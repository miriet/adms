package postech.adms.service.member;

import com.mysema.query.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import postech.adms.domain.codes.DegreeType;
import postech.adms.domain.department.Department;
import postech.adms.domain.department.QDepartment;
import postech.adms.domain.member.Address;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.member.Degree;
import postech.adms.domain.user.AdminUser;
import postech.adms.dto.member.MemberListDto;
import postech.adms.repository.department.DepartmentRepository;
import postech.adms.repository.member.AddressRepository;
import postech.adms.repository.member.DegreeRepository;
import postech.adms.repository.member.MemberRepository;
import postech.adms.repository.user.UserRepository;
import postech.adms.service.member.predicate.MemberPredicate;
import postech.adms.web.security.UserInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("admsMemberService")
@Transactional("transactionManager")
public class MemberService {

	@Resource
	protected MemberRepository memberRepository;

	@Resource
	protected DegreeRepository degreeRepository;

	@Resource
	protected AddressRepository addressRepository;

	@Resource
	protected UserRepository userRepository;

    @Resource
    protected DepartmentRepository departmentRepository;

    // --------------------------------------------------------
	//
	// --------------------------------------------------------
	@Transactional(readOnly = true)
	public AlumniMember findOne(Long id) {
		return memberRepository.findOne(id);
	}

	// --------------------------------------------------------
	//
	// --------------------------------------------------------
	@Transactional(readOnly = true)
	public AlumniMember findByName(String name) {
		return memberRepository.findByName(name);
	}

	@Transactional(readOnly = true)
	public AlumniMember findByNameAndBirthdayOfficial(String name, String birthdayOfficial) {
		return memberRepository.findByNameAndBirthdayOfficial(name, birthdayOfficial);
	}

	@Transactional(readOnly = true)
	public AlumniMember findByNameAndBirthdayReal(String name, String birthdayReal) {
		return memberRepository.findByNameAndBirthdayReal(name, birthdayReal);
	}

	// --------------------------------------------------------
	//
	// --------------------------------------------------------
	@Transactional(readOnly = true)
	public AlumniMember findByMemberId(Long memberId) {
		return memberRepository.findOne(memberId);
	}

	// --------------------------------------------------------
	//
	// --------------------------------------------------------
	@Transactional(readOnly = true)
	public Page<MemberListDto> find(Map<String, String> param, Pageable pageable) {
		boolean isDgreeJoin = false;
		if (!StringUtils.isEmpty(param.get("searchDept")) || !StringUtils.isEmpty(param.get("searchDegreeType"))) {
			isDgreeJoin = true;
		}

		Page<MemberListDto> result = memberRepository.readMembers(MemberPredicate.searchPredicate(param), pageable,isDgreeJoin);
		return result;
	}

	/**
	 * 2018.03.23 박재현
	 * 동창 조회 사용
	 * @param param
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<MemberListDto> alumnusFind(Map<String, String> param, Pageable pageable) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AdminUser loginData = userRepository.findOne(userInfo.getId());

        List<Degree> degreeList = new ArrayList<Degree>();
        degreeList = degreeRepository.findByAlumniMember(loginData.getMember());

        for (Degree data : degreeList) {
            if (data.getDegreeType().toString().equals(param.get("searchDegreeType").toString()) || data.getDegreeType().toString() == param.get("searchDegreeType")) {
                param.put("DEPT_CODE", data.getDeptCode());
                param.put("ENTRANCE_YEAR", data.getEntranceYear());
				//param.put("MEMBER_ID", loginData.getMember().getId().toString());
				break;
			}
		}

        param.put("MEMBER_ID", loginData.getMember().getId().toString());
        Page<MemberListDto> result = memberRepository.readAlumMembers(MemberPredicate.searchAlumnusPredicate(param), pageable);

        return result;
    }

    // --------------------------------------------------------
    // 회원목록
    // --------------------------------------------------------
    @Transactional(readOnly = true)
    public List<AlumniMember> find(Map<String, String> param) {
        boolean isDgreeJoin = false;
        if (!StringUtils.isEmpty(param.get("searchDept")) || !StringUtils.isEmpty(param.get("searchDegreeType"))) {
            isDgreeJoin = true;
        }

        List<AlumniMember> result = memberRepository.readMemberList(MemberPredicate.searchPredicate(param), isDgreeJoin);
        return result;
    }

    // --------------------------------------------------------
    // 수정된 회원목록 (ADMS => SAP)
    // --------------------------------------------------------
    @Transactional(readOnly = true)
    public List<AlumniMember> getMember(Map<String, String> param) {
        boolean isDgreeJoin = true;
        List<AlumniMember> result = memberRepository.readMemberList(MemberPredicate.searchPredicate(param), isDgreeJoin);
        return result;
    }

	/*
	// --------------------------------------------------------
	//
	// --------------------------------------------------------
	public Page<MemberListDto> readUsersWithoutUserRoles(long siteId, Pageable pageable) {
		Page<MemberListDto> result = memberRepository
				.readMembersWithoutUserRoles(MemberPredicate.userWithoutUserRole(siteId), pageable);
		return result;
	}
    */

	// --------------------------------------------------------
	// 신규 등록
	// --------------------------------------------------------
	public void insert(AlumniMember alumniMember){
		AlumniMember newMember = new AlumniMember();

		newMember.setName(alumniMember.getName());
		newMember.setEmail(alumniMember.getEmail());
		newMember.setMobile(alumniMember.getMobile());
		newMember.setBirthdayOfficial(alumniMember.getBirthdayOfficial());

		newMember.setBirthdayType(alumniMember.getBirthdayType());
		newMember.setBirthdayReal(alumniMember.getBirthdayReal());
		newMember.setGender(alumniMember.getGender());
		newMember.setPhone(alumniMember.getPhone());
		newMember.setNationality(alumniMember.getNationality());
		newMember.setMembershipFeeStatus(alumniMember.getMembershipFeeStatus());
		newMember.setMembershipFeeDate(alumniMember.getMembershipFeeDate());
		newMember.setMembershipFeeTotal(alumniMember.getMembershipFeeTotal());

		// 학위
		if (alumniMember.getDegrees() != null && !alumniMember.getDegrees().isEmpty()) {
    		for (Degree degree : alumniMember.getDegrees()) {
				degree.setAlumniMember(alumniMember);
				degree.setDegreeType(getDegreeType(degree.getDegreeType().name()));
				degree.setDeptCode(degree.getDeptCode());
				degree.setDeptName(getDeptName(degree.getDeptCode()));
				degree.setEntranceYear(degree.getEntranceYear());
				degree.setGraduationYear(degree.getGraduationYear());
				degree.setStudentId(degree.getStudentId());
			}
		}

		// 주소
		if (alumniMember.getAddresses() != null && !alumniMember.getAddresses().isEmpty()) {
			for (Address address : alumniMember.getAddresses()) {
				address.setAlumniMember(alumniMember);
			}
		}

		memberRepository.save(alumniMember);
	}

	// --------------------------------------------------------
	// 수정
	// --------------------------------------------------------
	public void update(AlumniMember memberDto) {
		AlumniMember existMember = findOne(memberDto.getId());

		existMember.setName(memberDto.getName());
		existMember.setEmail(memberDto.getEmail());
		existMember.setMobile(memberDto.getMobile());
		existMember.setBirthdayOfficial(memberDto.getBirthdayOfficial());
		existMember.getAuditable().setDateUpdated(new Date());

		existMember.setBirthdayType(memberDto.getBirthdayType());
		existMember.setBirthdayReal(memberDto.getBirthdayReal());
		existMember.setGender(memberDto.getGender());
		existMember.setPhone(memberDto.getPhone());
		existMember.setNationality(memberDto.getNationality());
		existMember.setMembershipFeeStatus(memberDto.getMembershipFeeStatus());
		existMember.setMembershipFeeDate(memberDto.getMembershipFeeDate());
		existMember.setMembershipFeeTotal(memberDto.getMembershipFeeTotal());
		existMember.setAlive(memberDto.isAlive());
		existMember.setMarried(memberDto.isMarried());
		existMember.setMailingAddress(memberDto.getMailingAddress());
		existMember.setCurrentWork(memberDto.getCurrentWork());
		existMember.setCurrentWorkDept(memberDto.getCurrentWorkDept());
		existMember.setCurrentJobTitle(memberDto.getCurrentJobTitle());

		// 학위
		if (memberDto.getDegrees() != null && !memberDto.getDegrees().isEmpty()) {
			for (Degree degree : memberDto.getDegrees()) {
				if (degree.getId() != null) {
					for (Degree existDegree : existMember.getDegrees()) {
						if (degree.getId() == existDegree.getId()) {
							existDegree.setDegreeType(degree.getDegreeType());
							existDegree.setDeptCode(degree.getDeptCode());
							// existDegree.setDeptName(degree.getDeptName());
							existDegree.setDeptName(getDeptName(degree.getDeptCode()));
							existDegree.setEntranceYear(degree.getEntranceYear());
							existDegree.setGraduationYear(degree.getGraduationYear());
							existDegree.setStudentId(degree.getStudentId());
							existDegree.setSupervisor(degree.getSupervisor());
							existDegree.setExpectedPath(degree.getExpectedPath());
							existDegree.setExpectedWork(degree.getExpectedWork());
						}
					}
				} else {
					 existMember.addDegree(degree);
				}
			}
		}

		// 주소
		if (memberDto.getAddresses() != null && !memberDto.getAddresses().isEmpty()) {
			for (Address address : memberDto.getAddresses()) {
				if (address.getId() != null) {
					for (Address existAddress : existMember.getAddresses()) {
						long address1 = address.getId().longValue();
						long address2 = existAddress.getId().longValue();
//						if (address.getId() == existAddress.getId()) {
						if (address1 == address2) {
							existAddress.setAddressType(address.getAddressType());
							existAddress.setAddressName(address.getAddressName());
							existAddress.setZipCode(address.getZipCode());
							existAddress.setAddress1(address.getAddress1());
							existAddress.setAddress2(address.getAddress2());
							existAddress.setAddress3(address.getAddress3());
							existAddress.setAddress4(address.getAddress4());
						}
					}
				} else {
					existMember.addAddress(address);
				}
			}
		}

		memberRepository.save(existMember);
	}

	// --------------------------------------------------------
	// 학위정보 삭제
	// --------------------------------------------------------
	public void deleteDegree (Long id) {
		try {
			degreeRepository.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------
	// 주소정보 삭제
	// --------------------------------------------------------
	public void deleteAddress (Long id) {
		try {
			addressRepository.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------
	// 학위타입
	// --------------------------------------------------------
    private DegreeType getDegreeType(String degreeType) {
 	   DegreeType result = null;

		switch (degreeType) {
		case "BS":
            result = DegreeType.BS;
			break;

		case "MS":
            result = DegreeType.MS;
			break;

		case "PhD":
            result = DegreeType.PhD;
			break;

		case "UNITY":
            result = DegreeType.UNITY;
			break;

		case "PAMTIP":
            result = DegreeType.PAMTIP;
			break;

		}

	   return result;
   }

    public List<AlumniMember> alumnusFindExcel(Map<String, String> param) {
    	UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AdminUser loginData = userRepository.findOne(userInfo.getId());
		List<Degree> degreeList = new ArrayList<Degree>();
		degreeList = degreeRepository.findByAlumniMember(loginData.getMember());

        for (Degree data : degreeList) {
			if(data.getDegreeType().toString().equals(param.get("searchDegreeType").toString()) || data.getDegreeType().toString() == param.get("searchDegreeType")){
				param.put("DEPT_CODE", data.getDeptCode());
				param.put("ENTRANCE_YEAR", data.getEntranceYear());
				param.put("MEMBER_ID", loginData.getMember().getId().toString());
				break;
			}
		}

		List<AlumniMember> result = memberRepository.readAlumMembersList(MemberPredicate.searchAlumnusPredicate(param));
		return result;
    }

    // --------------------------------------------------------
    // SAP => ADMS 회원정보 저장처리
    // --------------------------------------------------------
	@Transactional
    public void saveMemberList(List<AlumniMember> alumniMemberList) {
        for (AlumniMember alumniMember : alumniMemberList) {
            // 중복회원 체크 (성명 + 생년월일)
            AlumniMember alumniMemberExist = findByNameAndBirthdayOfficial(alumniMember.getName(), alumniMember.getBirthdayOfficial());
//            AlumniMember alumniMemberExist = findByNameAndBirthdayOfficial(alumniMember.getName(), StringUtil.changeDateFormat(alumniMember.getBirthdayOfficial()));

            if (alumniMemberExist == null) { // 신규회원일때 Insert
                insert(alumniMember);
            } else { // 기존회원일때 Update
                alumniMember.setId(alumniMemberExist.getId());
                update(alumniMember);
            }
        }
    }

    // --------------------------------------------------------
    // 학과명 조회
    // --------------------------------------------------------
    public String getDeptName(String deptCode) {
        String deptName = "";
        if (!StringUtils.isEmpty(deptCode)) {
            Degree degree = new Degree();
            BooleanBuilder  booleanBuilder  = new BooleanBuilder();
            booleanBuilder.or(QDepartment.department.deptCode.eq(deptCode));
            Department department = departmentRepository.findOne(booleanBuilder);

            if (department != null) {
                deptName = department.getDeptName();
            }
        }

        return deptName;
    }
}
