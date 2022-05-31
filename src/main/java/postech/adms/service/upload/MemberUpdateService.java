package postech.adms.service.upload;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import postech.adms.common.upload.AdmsFileUploader;
import postech.adms.common.util.AlumniInfoCopy;
import postech.adms.common.util.CsvReader;
import postech.adms.domain.codes.CalType;
import postech.adms.domain.codes.DegreeType;
import postech.adms.domain.codes.Gender;
import postech.adms.domain.codes.MembershipFeeStatus;
import postech.adms.domain.department.Department;
import postech.adms.domain.department.QDepartment;
import postech.adms.domain.member.Address;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.member.Degree;
import postech.adms.domain.member.QAlumniMember;
import postech.adms.domain.upload.AlumniUploadDetail;
import postech.adms.domain.upload.AlumniUploadInfo;
import postech.adms.domain.upload.QAlumniUploadDetail;
import postech.adms.domain.user.AdminUser;
import postech.adms.dto.upload.UploadDetailListDto;
import postech.adms.dto.upload.UploadListDto;
import postech.adms.repository.department.DepartmentRepository;
import postech.adms.repository.member.MemberRepository;
import postech.adms.repository.upload.AlumniUploadDetailRepository;
import postech.adms.repository.upload.AlumniUploadRepository;
import postech.adms.service.member.MemberService;
import postech.adms.service.member.predicate.MemberPredicate;
import postech.adms.service.upload.predicate.AlumniUploadPredicate;
import postech.adms.service.upload.predicate.MemberUpdatePredicate;
import postech.adms.service.user.UserService;
import postech.adms.util.excel.ExcelData;
import postech.adms.util.excel.ExcelDataSet;
import postech.adms.util.excel.ExcelExtention;
import postech.adms.util.excel.ExcelReader;
import postech.adms.web.security.UserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by miriet on 2017. 5. 6..
 */
@Service("admsMemberUpdateService")
@Transactional("transactionManager")
public class MemberUpdateService {

    @Resource private AlumniUploadRepository alumniUploadRepository;

    @Resource private AlumniUploadDetailRepository alumniUploadDetailRepository;

    @Resource private AdmsFileUploader admsFileUploader;

    @Resource private CsvReader csvReader;

    @Resource protected MemberRepository memberRepository;

    @Resource private UserService userService;
    
    @Resource private DepartmentRepository departmentRepository;

	@Resource private MemberService memberService;

	static final String[] uploadRowhsKey = {"name","birthday","birthdayReal","birthdayRealType","email","phone","mobile"
		,"nationality","gender","isAlive","isMarried","membershipFeeStatus","bsId","bsDept","bsEntranceYear"
		,"bsGraduationYear","msId","msDept","msEntranceYear","msGraduationYear","phdId","phdDept"
		,"phdEntranceYear","phdGraduationYear","unityId","unityDept","unityEntranceYear","unityGraduationYear"
		,"pamtipId","pamtipDept","pamtipEntranceYear","pamtipGraduationYear","homeZipcode","homeAddr1"
		,"homeAddr2","workZipcode","workAddr1","workAddr2","mailingZipcode","mailingAddr1","mailingAddr2"};

	/**
     * 엑셀 업로드 리스트
     * @param userId
     * @param groupId
     * @param deptCode
     * @param pageable
     * @return
     */
    @Transactional(readOnly=true)
    public Page<UploadListDto> find(int userId, Long groupId, String deptCode, Pageable pageable){
    	AdminUser adminUser = userService.findOne((long) userId);
        Long adminGroupId =  adminUser.getAdminGroup().getGroupId();
        Page<UploadListDto> result = null;

        // 권한그룹별 조회권한
        if (adminGroupId == 1 || adminGroupId == 2) {	//관리자, 총동창회 :전체조회
        	result = alumniUploadRepository.readUploadList(null, pageable);
        } else if (adminGroupId == 3) {		//대학본부 : 소속그룹 조회
        	//result = alumniUploadRepository.readUploadList(AlumniUploadPredicate.uploaderEqual(userId), pageable);	//자기자신이 업로드한것만 조회
        	result = alumniUploadRepository.readUploadList(AlumniUploadPredicate.groupIdEqual(groupId), pageable);
        } else {		// 학과동창회, 학과사무실 : 소속학과조회
        	result = alumniUploadRepository.readUploadList(AlumniUploadPredicate.deptCodeEqual(deptCode), pageable);
        }
        return result;
    }
    
    /**
     * UploadDetail 회원 목록 검색
     * @param param
     * @param pageable
     * @return
     */
    @Transactional(readOnly=true)
    public Page<UploadDetailListDto> detailFind( Map<String,String> param, Pageable pageable){
//    	AlumniUploadInfo alumniUploadInfo = alumniUploadRepository.findOne((long) detailId);
//		long infoId ;
//		infoId = (alumniUploadInfo==null)? 0L :alumniUploadInfo.getInfoId();

//        Page<UploadDetailListDto> result = alumniUploadRepository.readUploadDetailList(AlumniUploadPredicate.infoIdEqual(infoId), pageable);
//		Page<UploadDetailListDto> result = alumniUploadRepository.readUploadDetailList(AlumniUploadPredicate.infoIdEqual(alumniUploadInfo), pageable);
		Page<UploadDetailListDto> result = alumniUploadRepository.readUploadDetailList(MemberUpdatePredicate.searchUploadDetailPredicate(param), pageable);

    	return result;
    }

	/**
	 * UploadDetail 회원 목록 검색 (업로드 테이블 기준으로 회원테이블 데이터를 병합해서 표시)
	 * @param param
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<String, Object> findUpdateListByDetail( Map<String,String> param, Pageable pageable){
		Page<UploadDetailListDto> dtos = alumniUploadRepository.readUploadDetailList(MemberUpdatePredicate.searchUploadDetailPredicate(param), pageable);
		List<UploadDetailListDto> result = new ArrayList<UploadDetailListDto>();
		for(UploadDetailListDto dto:dtos){
			AlumniMember alumniMember = memberService.findByNameAndBirthdayOfficial(dto.getName(), dto.getBirthdayOfficial());
			result.add(dto);
			if (alumniMember == null) {
				result.add(new UploadDetailListDto());
			} else{
				result.add(AlumniInfoCopy.copyAlumniToUploadDetailListDto(alumniMember));
			}
		}
		Map<String,Object> returnValue = new HashMap<String, Object>();
		returnValue.put("dto", dtos);
		returnValue.put("result", result);

		return returnValue;
	}

	/**
	 * UploadDetail 회원 목록 검색 (회원 테이블 기준으로 업로드테이블 데이터를 병합해서 표시)
	 * @param param
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<String, Object> findUpdateListByMember( Map<String,String> param, Pageable pageable){
		boolean isDgreeJoin = false;
		if (!StringUtils.isEmpty(param.get("searchDept")) || !StringUtils.isEmpty(param.get("searchDegreeType"))) {
			isDgreeJoin = true;
		}
		Page<AlumniMember> alumniMembers = memberRepository.readAlumniMembers(MemberPredicate.searchPredicate(param),pageable, isDgreeJoin);

		List<UploadDetailListDto> result = new ArrayList<UploadDetailListDto>();
		for (AlumniMember alumniMember:alumniMembers){
			UploadDetailListDto uploadDetailListDto = alumniUploadDetailRepository.findByNameAndBirthdayOfficialAndAlumniUploadInfo_InfoId(
					alumniMember.getName(),alumniMember.getBirthdayOfficial(), Long.parseLong(param.get("infoId")));
			if (uploadDetailListDto == null) {
				result.add(new UploadDetailListDto());
			} else{
				result.add(uploadDetailListDto);
			}
			result.add(AlumniInfoCopy.copyAlumniToUploadDetailListDto(alumniMember));
		}
		Map<String,Object> returnValue = new HashMap<String, Object>();
		returnValue.put("dto", alumniMembers);
		returnValue.put("result", result);

		return returnValue;
	}

	/**
	 * UploadDetail 회원 목록 검색 (회원 테이블과 업로드테이블 공통 데이터를 표시)
	 * @param param
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<String, Object> findUpdateListByMemberAndUpload( Map<String,String> param, Pageable pageable){
		Page<UploadDetailListDto> dtos = alumniUploadDetailRepository.readUpdateDetailListByMemberAndUpload(MemberUpdatePredicate.searchUploadDetailPredicate(param), pageable);
		List<UploadDetailListDto> result = new ArrayList<UploadDetailListDto>();
		for(UploadDetailListDto dto:dtos){
			AlumniMember alumniMember = memberService.findByNameAndBirthdayOfficial(dto.getName(), dto.getBirthdayOfficial());
			result.add(dto);
			if (alumniMember == null) {
				result.add(new UploadDetailListDto());
			} else{
				result.add(AlumniInfoCopy.copyAlumniToUploadDetailListDto(alumniMember));
			}
		}
		Map<String,Object> returnValue = new HashMap<String, Object>();
		returnValue.put("dto", dtos);
		returnValue.put("result", result);

		return returnValue;
	}

	/**
	 * UploadDetail 회원 목록 검색 (회원 테이블과 업로드테이블 공통 데이터를 표시)
	 * @param param
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<String, Object> findUpdateListByMemberMinusUpload( Map<String,String> param, Pageable pageable){
		Page<AlumniMember> dtos = alumniUploadDetailRepository.readUpdateDetailListByMemberMinusUpload(Long.parseLong(param.get("infoId"))
																		, MemberPredicate.searchUploadDetailMinusConditionPredicate(param), pageable);
		List<UploadDetailListDto> result = new ArrayList<UploadDetailListDto>();

		for(AlumniMember dto:dtos){
			AlumniMember alumniMember = memberService.findByNameAndBirthdayOfficial(dto.getName(), dto.getBirthdayOfficial());
			result.add(new UploadDetailListDto());
			result.add(AlumniInfoCopy.copyAlumniToUploadDetailListDto(alumniMember));
		}

		Map<String,Object> returnValue = new HashMap<String, Object>();
		returnValue.put("dto", dtos);
		returnValue.put("result", result);

		return returnValue;
	}

	/**
	 * UploadDetail 회원 목록 검색 (회원 테이블과 업로드테이블 공통 데이터를 표시)
	 * @param param
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<String, Object> findUpdateListByUploadMinusMember( Map<String,String> param, Pageable pageable){
		Page<UploadDetailListDto> dtos = alumniUploadDetailRepository.readUpdateDetailListByUploadMinusMember(Long.parseLong(param.get("infoId"))
																		, MemberUpdatePredicate.searchUploadDetailMinusConditionPredicate(param), pageable);
		List<UploadDetailListDto> result = new ArrayList<UploadDetailListDto>();
		UploadDetailListDto uploadDetailListDto = new UploadDetailListDto();
		uploadDetailListDto.setSourceTable("M");
		for(UploadDetailListDto dto:dtos){
			AlumniMember alumniMember = memberService.findByNameAndBirthdayOfficial(dto.getName(), dto.getBirthdayOfficial());
			result.add(dto);
			result.add(uploadDetailListDto);
//			if (alumniMember == null) {
//				result.add(new UploadDetailListDto());
//			} else{
//				result.add(AlumniInfoCopy.copyAlumniToUploadDetailListDto(alumniMember));
//			}
		}
		Map<String,Object> returnValue = new HashMap<String, Object>();
		returnValue.put("dto", dtos);
		returnValue.put("result", result);

		return returnValue;
	}

	/**
     * 엑셀 업로드 회원 승인처리
     * @param detailIds
     */
    public void addUploadMember(long[] detailIds) {
    	for (long detailId : detailIds) {
    		AlumniUploadDetail alumniUploadDetail = alumniUploadDetailRepository.findOne(detailId);
    		AlumniMember alumniMember = getMemberUpdateFor(alumniUploadDetail);
    		boolean isUpdate = alumniMember.getId() == null?false:true;

			alumniMember.setBirthdayOfficial(alumniUploadDetail.getBirthdayOfficial());
			alumniMember.setBirthdayReal(alumniUploadDetail.getBirthdayReal());

			if ("SOLAR".equals(alumniUploadDetail.getBirthdayRealType())) {
				alumniMember.setBirthdayType(CalType.SOLAR);
			} else if ("LUNAR".equals(alumniUploadDetail.getBirthdayRealType())) {
				alumniMember.setBirthdayType(CalType.LUNAR);
			}

			alumniMember.setEmail(alumniUploadDetail.getEmail());

			if ("MALE".equals(alumniUploadDetail.getGender())) {
				alumniMember.setGender(Gender.MALE);
			} else if ("FEMALE".equals(alumniUploadDetail.getGender())) {
				alumniMember.setGender(Gender.FEMALE);
			} else if ("OTHER".equals(alumniUploadDetail.getGender())) {
				alumniMember.setGender(Gender.OTHER);
			} else if ("NO_COMMENT".equals(alumniUploadDetail.getGender())) {
				alumniMember.setGender(Gender.NO_COMMENT);
			}

			alumniMember.setAlive(alumniUploadDetail.getIsAlive());
			alumniMember.setMarried(alumniUploadDetail.getIsMarried());

			if("NO_LIFE_MEMBER".equals(alumniUploadDetail.getMembershipFeeStatus())) {
				alumniMember.setMembershipFeeStatus(MembershipFeeStatus.NO_LIFE_MEMBER);
			} else if ("LIFE_MEMBER".equals(alumniUploadDetail.getMembershipFeeStatus())) {
				alumniMember.setMembershipFeeStatus(MembershipFeeStatus.LIFE_MEMBER);
			}

			alumniMember.setMobile(alumniUploadDetail.getMobile());
			alumniMember.setName(alumniUploadDetail.getName());
			alumniMember.setNationality(alumniUploadDetail.getNationality());
			alumniMember.setPhone(alumniUploadDetail.getPhone());

			if (isUpdate == false) memberRepository.save(alumniMember);

			//학과 셋팅
			if(alumniUploadDetail.getBsId() != null && alumniUploadDetail.getBsId().length() > 1){
	    		Degree degree = new Degree();
	    		BooleanBuilder  booleanBuilder  = new BooleanBuilder();
	    		booleanBuilder.or(QDepartment.department.deptName.eq(alumniUploadDetail.getBsDept()));
	    		booleanBuilder.or(QDepartment.department.deptCode.eq(alumniUploadDetail.getBsDept()));
	    		Department department = departmentRepository.findOne(booleanBuilder);
				degree.setAlumniMember(alumniMember);
				degree.setDegreeType(DegreeType.BS);
				degree.setDeptCode(department.getDeptCode());
				degree.setDeptName(alumniUploadDetail.getBsDept());
				degree.setEntranceYear(alumniUploadDetail.getBsEntranceYear());
				degree.setGraduationYear(alumniUploadDetail.getBsGraduationYear());
				degree.setStudentId(alumniUploadDetail.getBsId());

				alumniMember.addDegree(degree);
			}
			if(alumniUploadDetail.getMsId() != null && alumniUploadDetail.getMsId().length() > 1){
	    		Degree degree = new Degree();
	    		BooleanBuilder  booleanBuilder  = new BooleanBuilder();
	    		booleanBuilder.or(QDepartment.department.deptName.eq(alumniUploadDetail.getMsDept()));
	    		booleanBuilder.or(QDepartment.department.deptCode.eq(alumniUploadDetail.getMsDept()));
	    		Department department = departmentRepository.findOne(booleanBuilder);
				degree.setAlumniMember(alumniMember);
				degree.setDegreeType(DegreeType.MS);
				degree.setDeptCode(department.getDeptCode());
				degree.setDeptName(alumniUploadDetail.getMsDept());
				degree.setEntranceYear(alumniUploadDetail.getMsEntranceYear());
				degree.setGraduationYear(alumniUploadDetail.getMsGraduationYear());
				degree.setStudentId(alumniUploadDetail.getMsId());

				alumniMember.addDegree(degree);
			}
			if(alumniUploadDetail.getPhdId() != null && alumniUploadDetail.getPhdId().length() > 1){
	    		Degree degree = new Degree();
	    		BooleanBuilder  booleanBuilder  = new BooleanBuilder();
	    		booleanBuilder.or(QDepartment.department.deptName.eq(alumniUploadDetail.getPhdDept()));
	    		booleanBuilder.or(QDepartment.department.deptCode.eq(alumniUploadDetail.getPhdDept()));
	    		Department department = departmentRepository.findOne(booleanBuilder);
				degree.setAlumniMember(alumniMember);
				degree.setDegreeType(DegreeType.PhD);
				degree.setDeptCode(department.getDeptCode());
				degree.setDeptName(alumniUploadDetail.getPhdDept());
				degree.setEntranceYear(alumniUploadDetail.getPhdEntranceYear());
				degree.setGraduationYear(alumniUploadDetail.getPhdGraduationYear());
				degree.setStudentId(alumniUploadDetail.getPhdId());

				alumniMember.addDegree(degree);
			}
			if(alumniUploadDetail.getUnityId() != null && alumniUploadDetail.getUnityId().length() > 1){
	    		Degree degree = new Degree();
	    		BooleanBuilder  booleanBuilder  = new BooleanBuilder();
	    		booleanBuilder.or(QDepartment.department.deptName.eq(alumniUploadDetail.getUnityDept()));
	    		booleanBuilder.or(QDepartment.department.deptCode.eq(alumniUploadDetail.getUnityDept()));
	    		Department department = departmentRepository.findOne(booleanBuilder);
				degree.setAlumniMember(alumniMember);
				degree.setDegreeType(DegreeType.UNITY);
				degree.setDeptCode(department.getDeptCode());
				degree.setDeptName(alumniUploadDetail.getUnityDept());
				degree.setEntranceYear(alumniUploadDetail.getUnityEntranceYear());
				degree.setGraduationYear(alumniUploadDetail.getUnityGraduationYear());
				degree.setStudentId(alumniUploadDetail.getUnityId());

				alumniMember.addDegree(degree);
			}

			//주소셋팅
			if(alumniUploadDetail.getHomeZipcode() != null && alumniUploadDetail.getHomeZipcode().length() > 0) {
				Address address = new Address();
				address.setAlumniMember(alumniMember);
				address.setAddressType("HOME");
				address.setCountry("KOR");
				address.setZipCode(alumniUploadDetail.getHomeZipcode());
				address.setAddress1(alumniUploadDetail.getHomeAddr1());
				address.setAddress2(alumniUploadDetail.getHomeAddr2());

				alumniMember.addAddress(address);
			}
			if(alumniUploadDetail.getWorkZipcode() != null && alumniUploadDetail.getWorkZipcode().length() > 0) {
				Address address = new Address();
				address.setAlumniMember(alumniMember);
				address.setAddressType("WORK");
				address.setCountry("KOR");
				address.setZipCode(alumniUploadDetail.getWorkZipcode());
				address.setAddress1(alumniUploadDetail.getWorkAddr1());
				address.setAddress2(alumniUploadDetail.getWorkAddr2());

				alumniMember.addAddress(address);
			}
			System.out.println("DetailId :: "+detailId);
			if(alumniUploadDetail.getMailingZipcode() != null && alumniUploadDetail.getMailingZipcode().length() > 0) {
				Address address = new Address();
				address.setAlumniMember(alumniMember);
				address.setAddressType("MAILING");
				address.setCountry("KOR");
				address.setZipCode(alumniUploadDetail.getMailingZipcode());
				address.setAddress1(alumniUploadDetail.getMailingAddr1());
				address.setAddress2(alumniUploadDetail.getMailingAddr2());

				alumniMember.addAddress(address);
			}

    	}
    }

    public void addUploadMembers(){
		BooleanBuilder  booleanBuilder  = new BooleanBuilder();
		booleanBuilder.and(QAlumniUploadDetail.alumniUploadDetail.alumniUploadInfo().infoId.eq(4L));

		List<AlumniUploadDetail> uploadDetails = new ArrayList<AlumniUploadDetail>();
		try {
			uploadDetails = Lists.newArrayList(alumniUploadDetailRepository.findAll(booleanBuilder));
		} catch (Exception e) {
			e.printStackTrace();
		}

    	long[] uploadInfoIds = new long[2621];
		for (int i = 0; i < uploadDetails.size();i++){
			uploadInfoIds[i] = uploadDetails.get(i).getDetailId();
		}
		System.out.println(uploadInfoIds.length);
		addUploadMember(uploadInfoIds);
	}
    
    /**
     * 엑셀 업로드 회원 중복확인 name, email로
     * @param alumniUploadDetail
     * @return
     */
    private AlumniMember getMemberUpdateFor(AlumniUploadDetail alumniUploadDetail) {
    	BooleanBuilder  booleanBuilder  = new BooleanBuilder();
		booleanBuilder.and(QAlumniMember.alumniMember.name.eq(alumniUploadDetail.getName()));
		booleanBuilder.and(QAlumniMember.alumniMember.email.eq(alumniUploadDetail.getEmail()));
		
		List<AlumniMember> memberList = new ArrayList<AlumniMember>();
		try {
			memberList = Lists.newArrayList(memberRepository.findAll(booleanBuilder));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		List<AlumniMember> memberList = Lists.newArrayList(memberRepository.findAll(booleanBuilder));
		AlumniMember result = null;
		if (memberList.isEmpty()) result = new AlumniMember();
		else result = memberList.get(0);
		return result;
	}
    
    public void deleteUploadFile(Long infoId) {
    	alumniUploadRepository.delete(infoId);
    }

	public void insert(int uploader, String title , HttpServletRequest request){

        AlumniUploadInfo alumniUploadInfo = admsFileUploader.parseUploadFileInfo(request);
        alumniUploadInfo.setUploader(uploader);
        alumniUploadInfo.setTitle(title);

        List<AlumniUploadDetail> alumniUploadDetails = csvReader.readAlumniUploadDetail(alumniUploadInfo.getStoredFileName());

//        alumniUploadRepository.flush();
//        for (AlumniUploadDetail alumniUploadDetail:alumniUploadDetails){
////            alumniUploadDetail.s
//            alumniUploadDetail.setAlumniUploadInfo(alumniUploadInfo);
//        }
        alumniUploadInfo.setAlumniUploadDetails(alumniUploadDetails);
        alumniUploadRepository.save(alumniUploadInfo);

    }
    
    /**
     * 엑셀 업로드 하여 UploadInfo , UploadDetail
     * @param title
     * @param excelFile
     */
	public void uploadExcel(String title, MultipartFile excelFile) throws IOException {
		ExcelExtention excelExtention = ExcelExtention.XLS;
    	if (excelFile.getOriginalFilename().endsWith("xlsx")) {
    		excelExtention = ExcelExtention.XLSX;
    	}
    	ExcelReader excelReader = new ExcelReader(excelFile.getInputStream(), excelExtention, 1);
    	ExcelDataSet excelDataSet = excelReader.getReadExcel();
    	
    	
    	UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	AlumniUploadInfo uploadInfo = new AlumniUploadInfo();
    	Date today = new Date();
    	
    	uploadInfo.setUploader((int)(long)userInfo.getId());
    	uploadInfo.setTitle(title);
    	uploadInfo.setUploadDate(today); 
    	uploadInfo.setFilePath("/data/adms/upload/");
    	uploadInfo.setFileName(excelFile.getOriginalFilename());
    	uploadInfo.setStoredFileName(System.currentTimeMillis() + "_" + excelFile.getOriginalFilename());
    	uploadInfo.setGroupId(userInfo.getGroupId());
    	uploadInfo.setDeptCode(userInfo.getDeptCode());
        
    	for (int i = 0; i < excelDataSet.size(); i++) {
			ExcelData excelData = excelDataSet.getRow(i);
			AlumniUploadDetail uploadDetail = new AlumniUploadDetail();
			
	    	getRowData(excelData, uploadDetail); // Row Data 읽기
	    	
			uploadInfo.addAlumniUploadDetails(uploadDetail);
			
		}
    	alumniUploadRepository.save(uploadInfo);
	}

	/**
	 * 업로드된 엑셀의 Row Data --> 객체 만들기(AlumniUploadDetail,AlumniMember)
	 * @param excelData
	 * @param uploadDetail
	 */
	private void getRowData(ExcelData excelData, AlumniUploadDetail uploadDetail) {
		
		for (int idx = 0; idx < uploadRowhsKey.length; idx++) {
			String data = excelData.getColumn(uploadRowhsKey[idx]);
			if(idx == 0) uploadDetail.setName(data);
			if(idx == 1) uploadDetail.setBirthdayOfficial(data);
			if(idx == 2) uploadDetail.setBirthdayReal(data);
			if(idx == 3) uploadDetail.setBirthdayRealType(data);
			if(idx == 4) uploadDetail.setEmail(data);
			if(idx == 5) uploadDetail.setPhone(data);
			if(idx == 6) uploadDetail.setMobile(data);
			if(idx == 7) uploadDetail.setNationality(data);
			if(idx == 8) uploadDetail.setGender(data);
			if(idx == 9) {
				if("Y".equals(data)) {
					uploadDetail.setAlive(true);
				} else if("N".equals(data)) {
					uploadDetail.setAlive(false);
				}
			}
			if(idx == 10) {
				if("Y".equals(data)) {
					uploadDetail.setMarried(true);
				} else if("N".equals(data)) {
					uploadDetail.setMarried(false);
				}
			}
			if(idx == 11) uploadDetail.setMembershipFeeStatus(data);
			if(idx == 12) uploadDetail.setBsId(data);
			if(idx == 13) uploadDetail.setBsDept(data);
			if(idx == 14) uploadDetail.setBsEntranceYear(data);
			if(idx == 15) uploadDetail.setBsGraduationYear(data);
			if(idx == 16) uploadDetail.setMsId(data);
			if(idx == 17) uploadDetail.setMsDept(data);
			if(idx == 18) uploadDetail.setMsEntranceYear(data);
			if(idx == 19) uploadDetail.setMsGraduationYear(data);
			if(idx == 20) uploadDetail.setPhdId(data);
			if(idx == 21) uploadDetail.setPhdDept(data);
			if(idx == 22) uploadDetail.setPhdEntranceYear(data);
			if(idx == 23) uploadDetail.setPhdGraduationYear(data);
			if(idx == 24) uploadDetail.setUnityId(data);
			if(idx == 25) uploadDetail.setUnityDept(data);
			if(idx == 26) uploadDetail.setUnityEntranceYear(data);
			if(idx == 27) uploadDetail.setUnityGraduationYear(data);
			if(idx == 28) uploadDetail.setPamtipId(data);
			if(idx == 29) uploadDetail.setPamtipDept(data);
			if(idx == 30) uploadDetail.setPamtipEntranceYear(data);
			if(idx == 31) uploadDetail.setPamtipGraduationYear(data);
			if(idx == 32) uploadDetail.setHomeZipcode(data);
			if(idx == 33) uploadDetail.setHomeAddr1(data);
			if(idx == 34) uploadDetail.setHomeAddr2(data);
			if(idx == 35) uploadDetail.setWorkZipcode(data);
			if(idx == 36) uploadDetail.setWorkAddr1(data);
			if(idx == 37) uploadDetail.setWorkAddr2(data);
			if(idx == 38) uploadDetail.setMailingZipcode(data);
			if(idx == 39) uploadDetail.setMailingAddr1(data);
			if(idx == 40) uploadDetail.setMailingAddr2(data);
		}
	}



}
