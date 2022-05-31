package postech.adms.service.upload;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import postech.adms.common.upload.AdmsFileUploader;
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
import postech.adms.repository.member.DegreeRepository;
import postech.adms.repository.member.MemberRepository;
import postech.adms.repository.upload.AlumniUploadDetailRepository;
import postech.adms.repository.upload.AlumniUploadRepository;
import postech.adms.service.upload.predicate.AlumniUploadPredicate;
import postech.adms.service.user.UserService;
import postech.adms.util.excel.ExcelData;
import postech.adms.util.excel.ExcelDataSet;
import postech.adms.util.excel.ExcelExtention;
import postech.adms.util.excel.ExcelReader;
import postech.adms.web.security.UserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by miriet on 2017. 5. 6..
 */
@Service("admsAlumniUploadService")
@Transactional("transactionManager")
public class AlumniUploadService {

    @Resource
    private AlumniUploadRepository alumniUploadRepository;

    @Resource
    private AlumniUploadDetailRepository alumniUploadDetailRepository;

    @Resource
    private AdmsFileUploader admsFileUploader;

    @Resource
    private CsvReader csvReader;

    @Resource
    protected MemberRepository memberRepository;

    @Resource
    protected DegreeRepository degreeRepository;

    @Resource
    private UserService userService;

    @Resource
    private DepartmentRepository departmentRepository;

    static final String[] uploadRowhsKey = {"name", "birthday", "birthdayReal", "birthdayRealType", "email", "phone", "mobile"
            , "nationality", "gender", "isAlive", "mailingAddress", "currentWork", "currentWorkDept", "currentJobTitle"
            , "bsId", "bsDept", "bsEntranceYear", "bsGraduationYear", "bsSupervisor", "bsExpectedPath", "bsExpectedWork"
            , "msId", "msDept", "msEntranceYear", "msGraduationYear", "msSupervisor", "msExpectedPath", "msExpectedWork"
            , "phdId", "phdDept", "phdEntranceYear", "phdGraduationYear", "phdSupervisor", "phdExpectedPath", "phdExpectedWork"
            , "unityId", "unityDept", "unityEntranceYear", "unityGraduationYear", "unitySupervisor", "unityExpectedPath", "unityExpectedWork"
            , "pamtipId", "pamtipDept", "pamtipEntranceYear", "pamtipGraduationYear"
            , "homeZipcode", "homeAddr1", "homeAddr2", "homeAddr3", "homeAddr4"
            , "workZipcode", "workAddr1", "workAddr2", "workAddr3", "workAddr4"};

    /**
     * 엑셀 업로드 리스트
     *
     * @param userId
     * @param groupId
     * @param deptCode
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<UploadListDto> find(int userId, Long groupId, String deptCode, Pageable pageable) {
        AdminUser adminUser = userService.findOne((long) userId);
        Long adminGroupId = adminUser.getAdminGroup().getGroupId();
        Page<UploadListDto> result = null;

        // 권한그룹별 조회권한
        if (adminGroupId == 1 || adminGroupId == 2) {    //관리자, 총동창회 :전체조회
            result = alumniUploadRepository.readUploadList(null, pageable);
        } else if (adminGroupId == 3) {        //대학본부 : 소속그룹 조회
            //result = alumniUploadRepository.readUploadList(AlumniUploadPredicate.uploaderEqual(userId), pageable);	//자기자신이 업로드한것만 조회
            result = alumniUploadRepository.readUploadList(AlumniUploadPredicate.groupIdEqual(groupId), pageable);
        } else {        // 학과동창회, 학과사무실 : 소속학과조회
            result = alumniUploadRepository.readUploadList(AlumniUploadPredicate.deptCodeEqual(deptCode), pageable);
        }
        return result;
    }

    /**
     * 엑셀 파일 회원 목록
     *
     * @param detailId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<UploadDetailListDto> detailFind(int detailId, Pageable pageable) {
        AlumniUploadInfo alumniUploadInfo = alumniUploadRepository.findOne((long) detailId);
        long infoId;
        infoId = (alumniUploadInfo == null) ? 0L : alumniUploadInfo.getInfoId();
//        Page<UploadDetailListDto> result = alumniUploadRepository.readUploadDetailList(AlumniUploadPredicate.infoIdEqual(infoId), pageable);
        Page<UploadDetailListDto> result = alumniUploadRepository.readUploadDetailList(AlumniUploadPredicate.infoIdEqual(alumniUploadInfo), pageable);

        return result;
    }

    /**
     * 엑셀 업로드 회원 승인처리
     *
     * @param detailIds
     */
    public void addUploadMember(long[] detailIds) {
        for (long detailId : detailIds) {
            AlumniUploadDetail alumniUploadDetail = alumniUploadDetailRepository.findOne(detailId);
            AlumniMember alumniMember = getMemberUpdateFor(alumniUploadDetail);
            boolean isUpdate = alumniMember.getId() == null ? false : true;

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

            if ("NO_LIFE_MEMBER".equals(alumniUploadDetail.getMembershipFeeStatus())) {
                alumniMember.setMembershipFeeStatus(MembershipFeeStatus.NO_LIFE_MEMBER);
            } else if ("LIFE_MEMBER".equals(alumniUploadDetail.getMembershipFeeStatus())) {
                alumniMember.setMembershipFeeStatus(MembershipFeeStatus.LIFE_MEMBER);
            }

            alumniMember.setMobile(alumniUploadDetail.getMobile());
            alumniMember.setName(alumniUploadDetail.getName());
            alumniMember.setNationality(alumniUploadDetail.getNationality());
            alumniMember.setPhone(alumniUploadDetail.getPhone());
            alumniMember.setMailingAddress(alumniUploadDetail.getMailingAddress());
            alumniMember.setCurrentWork(alumniUploadDetail.getCurrentWork());
            alumniMember.setCurrentWorkDept(alumniUploadDetail.getCurrentWorkDept());
            alumniMember.setCurrentJobTitle(alumniUploadDetail.getCurrentJobTitle());

            if (isUpdate == false) {
                memberRepository.save(alumniMember);
            }else{
                System.out.println("isUpdate is TRUE::uploadDetailId::" + alumniUploadDetail.getDetailId());
            }

            //학과 셋팅
            if (alumniUploadDetail.getBsId() != null && alumniUploadDetail.getBsId().length() > 1) {
                Degree degree = new Degree();
                BooleanBuilder booleanBuilder = new BooleanBuilder();
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
                degree.setSupervisor(alumniUploadDetail.getBsSupervisor());
                degree.setExpectedPath(alumniUploadDetail.getBsExpectedPath());
                degree.setExpectedWork(alumniUploadDetail.getBsExpectedWork());

                alumniMember.addDegree(degree);
            }
            if (alumniUploadDetail.getMsId() != null && alumniUploadDetail.getMsId().length() > 1) {
                Degree degree = new Degree();
                BooleanBuilder booleanBuilder = new BooleanBuilder();
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
                degree.setSupervisor(alumniUploadDetail.getMsSupervisor());
                degree.setExpectedPath(alumniUploadDetail.getMsExpectedPath());
                degree.setExpectedWork(alumniUploadDetail.getMsExpectedWork());

                alumniMember.addDegree(degree);
            }
            if (alumniUploadDetail.getPhdId() != null && alumniUploadDetail.getPhdId().length() > 1) {
                Degree degree = new Degree();
                BooleanBuilder booleanBuilder = new BooleanBuilder();
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
                degree.setSupervisor(alumniUploadDetail.getPhdSupervisor());
                degree.setExpectedPath(alumniUploadDetail.getPhdExpectedPath());
                degree.setExpectedWork(alumniUploadDetail.getPhdExpectedWork());

                alumniMember.addDegree(degree);
            }
            if (alumniUploadDetail.getUnityId() != null && alumniUploadDetail.getUnityId().length() > 1) {
                Degree degree = new Degree();
                BooleanBuilder booleanBuilder = new BooleanBuilder();
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
                degree.setSupervisor(alumniUploadDetail.getUnitySupervisor());
                degree.setExpectedPath(alumniUploadDetail.getUnityExpectedPath());
                degree.setExpectedWork(alumniUploadDetail.getUnityExpectedWork());

                alumniMember.addDegree(degree);
            }

            if (alumniUploadDetail.getPamtipId() != null && alumniUploadDetail.getPamtipId().length() > 1) {
                Degree degree = new Degree();
                BooleanBuilder booleanBuilder = new BooleanBuilder();
                booleanBuilder.or(QDepartment.department.deptName.eq(alumniUploadDetail.getPamtipDept()));
                booleanBuilder.or(QDepartment.department.deptCode.eq(alumniUploadDetail.getPamtipDept()));
                Department department = departmentRepository.findOne(booleanBuilder);
                degree.setAlumniMember(alumniMember);
                degree.setDegreeType(DegreeType.PAMTIP);
                degree.setDeptCode(department.getDeptCode());
                degree.setDeptName(alumniUploadDetail.getPamtipDept());
                degree.setEntranceYear(alumniUploadDetail.getPamtipEntranceYear());
                degree.setGraduationYear(alumniUploadDetail.getPamtipGraduationYear());
                degree.setStudentId(alumniUploadDetail.getPamtipId());

                alumniMember.addDegree(degree);
            }

            //주소셋팅
            if (alumniUploadDetail.getHomeZipcode() != null && alumniUploadDetail.getHomeZipcode().length() > 0) {
                Address address = new Address();
                address.setAlumniMember(alumniMember);
                address.setAddressType("HOME");
                address.setCountry("KOR");
                address.setZipCode(alumniUploadDetail.getHomeZipcode());
                address.setAddress1(alumniUploadDetail.getHomeAddr1());
                address.setAddress2(alumniUploadDetail.getHomeAddr2());
                address.setAddress3(alumniUploadDetail.getHomeAddr3());
                address.setAddress4(alumniUploadDetail.getHomeAddr4());

                alumniMember.addAddress(address);
            }
            if (alumniUploadDetail.getWorkZipcode() != null && alumniUploadDetail.getWorkZipcode().length() > 0) {
                Address address = new Address();
                address.setAlumniMember(alumniMember);
                address.setAddressType("WORK");
                address.setCountry("KOR");
                address.setZipCode(alumniUploadDetail.getWorkZipcode());
                address.setAddress1(alumniUploadDetail.getWorkAddr1());
                address.setAddress2(alumniUploadDetail.getWorkAddr2());
                address.setAddress3(alumniUploadDetail.getHomeAddr3());
                address.setAddress4(alumniUploadDetail.getHomeAddr4());

                alumniMember.addAddress(address);
            }
            System.out.println("DetailId :: " + detailId);
//            if (alumniUploadDetail.getMailingZipcode() != null && alumniUploadDetail.getMailingZipcode().length() > 0) {
//                Address address = new Address();
//                address.setAlumniMember(alumniMember);
//                address.setAddressType("MAILING");
//                address.setCountry("KOR");
//                address.setZipCode(alumniUploadDetail.getMailingZipcode());
//                address.setAddress1(alumniUploadDetail.getMailingAddr1());
//                address.setAddress2(alumniUploadDetail.getMailingAddr2());
//
//                alumniMember.addAddress(address);
//            }

        }
    }

    public void addUploadMembers(long infoId) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(QAlumniUploadDetail.alumniUploadDetail.alumniUploadInfo().infoId.eq(infoId));

        List<AlumniUploadDetail> uploadDetails = new ArrayList<AlumniUploadDetail>();
        try {
            uploadDetails = Lists.newArrayList(alumniUploadDetailRepository.findAll(booleanBuilder));
        } catch (Exception e) {
            e.printStackTrace();
        }

        int detailSize = uploadDetails.size();
        long[] uploadInfoIds = new long[detailSize];
        for (int i = 0; i < uploadDetails.size(); i++) {
            uploadInfoIds[i] = uploadDetails.get(i).getDetailId();
        }
        System.out.println(uploadInfoIds.length);
        addUploadMember(uploadInfoIds);
    }

    /**
     * 엑셀 업로드 회원 중복확인 name, (생년월일 또는 email)로
     *
     * @param alumniUploadDetail
     * @return
     */
    private AlumniMember getMemberUpdateFor(AlumniUploadDetail alumniUploadDetail) {
        BooleanExpression expression = QAlumniMember.alumniMember.birthdayOfficial
                .eq(alumniUploadDetail.getBirthdayOfficial());
//                .or(QAlumniMember.alumniMember.email.eq(alumniUploadDetail.getEmail()));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(QAlumniMember.alumniMember.name.eq(alumniUploadDetail.getName()));
        booleanBuilder.and(expression);
//		booleanBuilder.and(QAlumniMember.alumniMember.email.eq(alumniUploadDetail.getEmail()));

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

    public void insert(int uploader, String title, HttpServletRequest request) {

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
     *
     * @param title
     * @param excelFile
     */
    public void uploadExcel(String title, MultipartFile excelFile) throws IOException {
        ExcelExtention excelExtention = ExcelExtention.XLS;
        if (excelFile.getOriginalFilename().endsWith("xlsx")) {
            excelExtention = ExcelExtention.XLSX;
        }
        ExcelReader excelReader = new ExcelReader(excelFile.getInputStream(), excelExtention, 3);
        ExcelDataSet excelDataSet = excelReader.getReadExcel();


        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AlumniUploadInfo uploadInfo = new AlumniUploadInfo();
        Date today = new Date();

        uploadInfo.setUploader((int) (long) userInfo.getId());
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
     *
     * @param excelData
     * @param uploadDetail
     */
    private void getRowData(ExcelData excelData, AlumniUploadDetail uploadDetail) {

        for (int idx = 0; idx < uploadRowhsKey.length; idx++) {
            String data = excelData.getColumn(uploadRowhsKey[idx]);
            switch (idx) {
                case 0: uploadDetail.setName(data);              break;
                case 1: uploadDetail.setBirthdayOfficial(data);  break;
                case 2: uploadDetail.setBirthdayReal(data);      break;
                case 3: uploadDetail.setBirthdayRealType(data);  break;
                case 4: uploadDetail.setEmail(data);             break;
                case 5: uploadDetail.setPhone(data);             break;
                case 6: uploadDetail.setMobile(data);              break;
                case 7: uploadDetail.setNationality(data);         break;
                case 8: uploadDetail.setGender(data);              break;
                case 9: if ("Y".equals(data)) {
                            uploadDetail.setAlive(true);
                        } else if ("N".equals(data)) {
                            uploadDetail.setAlive(false);
                        }
                        break;
                case 10: uploadDetail.setMailingAddress(data);     break;
                case 11: uploadDetail.setCurrentWork(data);          break;
                case 12: uploadDetail.setCurrentWorkDept(data);      break;
                case 13: uploadDetail.setCurrentJobTitle(data);      break;
                case 14: uploadDetail.setBsId(data);                 break;
                case 15: uploadDetail.setBsDept(data);               break;
                case 16: uploadDetail.setBsEntranceYear(data);       break;
                case 17: uploadDetail.setBsGraduationYear(data);     break;
                case 18: uploadDetail.setBsSupervisor(data);         break;
                case 19: uploadDetail.setBsExpectedPath(data);       break;
                case 20: uploadDetail.setBsExpectedWork(data);       break;
                case 21: uploadDetail.setMsId(data);                 break;
                case 22: uploadDetail.setMsDept(data);               break;
                case 23: uploadDetail.setMsEntranceYear(data);       break;
                case 24: uploadDetail.setMsGraduationYear(data);     break;
                case 25: uploadDetail.setMsSupervisor(data);         break;
                case 26: uploadDetail.setMsExpectedPath(data);       break;
                case 27: uploadDetail.setMsExpectedWork(data);       break;
                case 28: uploadDetail.setPhdId(data);                break;
                case 29: uploadDetail.setPhdDept(data);              break;
                case 30: uploadDetail.setPhdEntranceYear(data);      break;
                case 31: uploadDetail.setPhdGraduationYear(data);    break;
                case 32: uploadDetail.setPhdSupervisor(data);        break;
                case 33: uploadDetail.setPhdExpectedPath(data);      break;
                case 34: uploadDetail.setPhdExpectedWork(data);      break;
                case 35: uploadDetail.setUnityId(data);              break;
                case 36: uploadDetail.setUnityDept(data);            break;
                case 37: uploadDetail.setUnityEntranceYear(data);    break;
                case 38: uploadDetail.setUnityGraduationYear(data);  break;
                case 39: uploadDetail.setUnitySupervisor(data);      break;
                case 40: uploadDetail.setUnityExpectedPath(data);    break;
                case 41: uploadDetail.setUnityExpectedWork(data);    break;
                case 42: uploadDetail.setPamtipId(data);             break;
                case 43: uploadDetail.setPamtipDept(data);           break;
                case 44: uploadDetail.setPamtipEntranceYear(data);   break;
                case 45: uploadDetail.setPamtipGraduationYear(data); break;
                case 46: uploadDetail.setHomeZipcode(data);          break;
                case 47: uploadDetail.setHomeAddr1(data);            break;
                case 48: uploadDetail.setHomeAddr2(data);            break;
                case 49: uploadDetail.setHomeAddr3(data);            break;
                case 50: uploadDetail.setHomeAddr4(data);            break;
                case 51: uploadDetail.setWorkZipcode(data);          break;
                case 52: uploadDetail.setWorkAddr1(data);            break;
                case 53: uploadDetail.setWorkAddr2(data);            break;
                case 54: uploadDetail.setWorkAddr3(data);            break;
                case 55: uploadDetail.setWorkAddr4(data);            break;

            }
//			if(idx == 9) {
//				if("Y".equals(data)) {
//					uploadDetail.setMarried(true);
//				} else if("N".equals(data)) {
//					uploadDetail.setMarried(false);
//				}
//			}
//			if(idx == 9) uploadDetail.setMembershipFeeStatus(data);
//			if(idx == 56) uploadDetail.setMailingZipcode(data);
//			if(idx == 57) uploadDetail.setMailingAddr1(data);
//			if(idx == 58) uploadDetail.setMailingAddr2(data);
//			if(idx == 59) uploadDetail.setMailingAddr3(data);
//			if(idx == 60) uploadDetail.setMailingAddr4(data);
        }
    }


}
