package postech.adms.service.user;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.adms.common.util.EntityUtil;
import postech.adms.domain.codes.DegreeType;
import postech.adms.domain.department.Department;
import postech.adms.domain.member.Address;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.member.Degree;
import postech.adms.domain.user.AdminUser;
import postech.adms.dto.user.UserListDto;
import postech.adms.repository.department.DepartmentRepository;
import postech.adms.repository.member.AddressRepository;
import postech.adms.repository.member.DegreeRepository;
import postech.adms.repository.member.MemberRepository;
import postech.adms.repository.user.UserRepository;
import postech.adms.service.authority.AdminGroupService;
import postech.adms.service.user.predicate.UserPredicate;
import postech.adms.web.form.ChangePwdForm;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service("admsUserService")
@Transactional("transactionManager")
public class UserService {

	@Resource protected UserRepository userRepository;
	@Resource protected AdminGroupService groupSVC;
	@Resource protected DepartmentRepository departmentRepository;
	
	@Resource protected MemberRepository memberRepository;
	@Resource protected DegreeRepository degreeRepository;
	@Resource protected AddressRepository addressRepository;
	/**
	 * 해당 사용자 찾기
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public AdminUser findOne(Long id){
		return userRepository.findOne(id);
	}

	@Transactional(readOnly=true)
	public AdminUser findByName(String name){
		return userRepository.findByName(name);
	}
	
	// --------------------------------------------------------
	//
	// --------------------------------------------------------
	@Transactional(readOnly = true)
	public AlumniMember memberFindOne(Long id) {
		return memberRepository.findOne(id);
	}
	
	/**
	 * 로그인 처리에 사용
	 * @param loginId
	 * @return
	 */
	@Transactional(readOnly=true)
	public AdminUser findByLoginId(String loginId){
		return userRepository.findByLoginId(loginId);
	}
	/**
	 * 사용자 전체 리스트
	 * @param param
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<UserListDto> find(Map<String,String> param, Pageable pageable){

		Page<UserListDto> result = userRepository.readUsers(UserPredicate.searchPredicate(param), pageable);
		return result;
	}
	
	/**
	 * 그룹별 사용자 찾기(그룹 관리에서 사용)
	 * @param param
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<UserListDto> userGroupFind(Map<String,String> param, Pageable pageable){

		Page<UserListDto> result = userRepository.readGroupsUsers(UserPredicate.searchPredicate(param), pageable);
		return result;
	}
	
	/**
	 * 정보 변경
	 * @param adminUserDto
	 */
	public void update(AdminUser adminUserDto){
		AdminUser adminUser = findOne(adminUserDto.getId());

		if(adminUserDto.getDepartment().getId() == null || adminUserDto.getDepartment().getId().equals("")){
			Department dept = departmentRepository.findOne((long)1);
			adminUserDto.setDepartment(dept);
		}else{
			Department dept = departmentRepository.findOne(adminUserDto.getDepartment().getId());
			adminUserDto.setDepartment(dept);
		}
		
		adminUserDto.setAdminGroup(groupSVC.findOne(adminUserDto.getAdminGroup().getGroupId()));
 		EntityUtil.transformDtoToModel(adminUserDto, adminUser);

 		adminUser.setDepartment(adminUserDto.getDepartment());
 		adminUser.setDeptCode(adminUserDto.getDepartment().getDeptCode());
		adminUser.setAdminGroup(adminUserDto.getAdminGroup());
		
	}
	
	/**
	 * 개인 정보 변경
	 * @param adminUserDto
	 */
	public void mamberUpdate(AdminUser adminUserDto){
		AdminUser adminUser = findOne(adminUserDto.getId());
				
		adminUser.setEmail(adminUserDto.getMember().getEmail());
		
		
		AlumniMember existMember = memberFindOne(adminUserDto.getMember().getId());
		
		existMember.setName(adminUserDto.getMember().getName());
		existMember.setEmail(adminUserDto.getMember().getEmail());
		existMember.setMobile(adminUserDto.getMember().getMobile());
		existMember.setBirthdayOfficial(adminUserDto.getMember().getBirthdayOfficial());
		existMember.getAuditable().setDateUpdated(new Date());

		existMember.setBirthdayType(adminUserDto.getMember().getBirthdayType());
		existMember.setBirthdayReal(adminUserDto.getMember().getBirthdayReal());
		existMember.setGender(adminUserDto.getMember().getGender());
		existMember.setPhone(adminUserDto.getMember().getPhone());
		existMember.setNationality(adminUserDto.getMember().getNationality());
		existMember.setMembershipFeeStatus(adminUserDto.getMember().getMembershipFeeStatus());
		existMember.setMembershipFeeDate(adminUserDto.getMember().getMembershipFeeDate());
		existMember.setMembershipFeeTotal(adminUserDto.getMember().getMembershipFeeTotal());
		existMember.setAlive(adminUserDto.getMember().isAlive());
		existMember.setMarried(adminUserDto.getMember().isMarried());
		existMember.setMailingAddress(adminUserDto.getMember().getMailingAddress());
		existMember.setCurrentWork(adminUserDto.getMember().getCurrentWork());
		existMember.setCurrentWorkDept(adminUserDto.getMember().getCurrentWorkDept());
		existMember.setCurrentJobTitle(adminUserDto.getMember().getCurrentJobTitle());
		
		// 학위
		if (adminUserDto.getMember().getDegrees() != null && !adminUserDto.getMember().getDegrees().isEmpty()) {
			for (Degree degree : adminUserDto.getMember().getDegrees()) {
				if (degree.getId() != null) {
					for (Degree existDegree : existMember.getDegrees()) {
						if (degree.getId() == existDegree.getId()) {
							existDegree.setDegreeType(degree.getDegreeType());
							existDegree.setDeptCode(degree.getDeptCode());
							existDegree.setDeptName(degree.getDeptName());
							existDegree.setEntranceYear(degree.getEntranceYear());
							existDegree.setGraduationYear(degree.getGraduationYear());
							existDegree.setStudentId(degree.getStudentId());
						}
					} 
				} else {
					 existMember.addDegree(degree);
				}
			}
		}
	
		
		// 주소
		if (adminUserDto.getMember().getAddresses() != null && !adminUserDto.getMember().getAddresses().isEmpty()) {
			for (Address address : adminUserDto.getMember().getAddresses()) {
				if (address.getId() != null) {
					for (Address existAddress : existMember.getAddresses()) {
						if (address.getId() == existAddress.getId()) {
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
	
	/**
	 * 비밀번호 변경
	 * @param adminUserDto
	 */
	public void changePassword(AdminUser adminUserDto){
		AdminUser adminUser = findOne(adminUserDto.getId());
		adminUser.setPassword(new ShaPasswordEncoder().encodePassword(adminUserDto.getPassword(), "POSTECH"));
		adminUser.setLastChangePwd(new Date());
		adminUser.setPasswdHist(makePasswdHistory(adminUser));
		
	}
	
	/**
	 * 등록
	 * @param adminUser
	 */
	public void insert(AdminUser adminUser){
		if(adminUser.getDepartment().getId() == null || adminUser.getDepartment().getId().equals("")){
			//그룹이 DEPARTMENT_OFFICE, DEPARTMNET_ALUMNI이 아닌 경우 학과코드에 기본값 셋팅
			Department dept = departmentRepository.findOne((long)1);
			adminUser.setDepartment(dept);
		}else{
			Department dept = departmentRepository.findOne(adminUser.getDepartment().getId());
			adminUser.setDepartment(dept);
		}
		adminUser.setDeptCode(adminUser.getDepartment().getDeptCode());
		adminUser.setPassword(new ShaPasswordEncoder().encodePassword(adminUser.getPassword(), "POSTECH"));
		adminUser.setPasswdHist(adminUser.getPassword());
		userRepository.save(adminUser);
	}
	
	public Page<UserListDto> readUsersWithoutUserRoles(long siteId,Pageable pageable){
		Page<UserListDto> result = userRepository.readUsersWithoutUserRoles(UserPredicate.userWithoutUserRole(siteId), pageable);
		return result;
	}
	/**
	 * 비밀번호 잘못 입력시 카운트(5회)
	 */
	public void updatePwdCount(String id,Long count){
		AdminUser adminUser = userRepository.findByLoginId(id);
		adminUser.setPwdCount(count);
		
	}
	/**
	 * 사용자계정장금 해제
	 * @param user
	 */
	public void userUnlock(AdminUser user) {
		AdminUser adminUser = findOne(user.getId());
		adminUser.setPwdCount(0L);
	}
	
	
	
	/**
	 * 기간에 따른 비밀번화 변경(다음에 변경)
	 * @param id
	 */
	public void userChangPwdDelay(Long id) {
		AdminUser adminUser = findOne(id);
		adminUser.setLastChangePwd(new Date());
		
	}
	/**
	 * 현재 비밀번호 / 기존 비밀번호 검증
	 * @param rawPassword
	 * @param encodedPassword
	 * @return
	 */
	public boolean matches(String rawPassword, String encodedPassword) {
		String passwd = new ShaPasswordEncoder().encodePassword(rawPassword, "POSTECH");
		return encodedPassword.equals(passwd);
	}
	/**
	 * 변경할 비밀번호 암호화
	 * @param newPasswd
	 * @return
	 */
	public String encryptPasswd(String newPasswd) {		
		return new ShaPasswordEncoder().encodePassword(newPasswd, "POSTECH");
	}

	public void changPwdProcess(ChangePwdForm inputForm) {
		AdminUser adminUser = findOne(inputForm.getUserId());
		adminUser.setPassword(new ShaPasswordEncoder().encodePassword(inputForm.getNewPasswd(), "POSTECH"));
		adminUser.setLastChangePwd(new Date());
		adminUser.setPasswdHist(makePasswdHistory(adminUser));
		
	}
	
	/**
	 * 6개월마다 비밀번호 변경시 사용
	 * 동일 비밀번호 사용제한(2개까지 저장)
	 * @param data
	 * @return
	 */
	private String makePasswdHistory(AdminUser data) {
		String result = "";
		String dataArr[] = data.getPasswdHist().split(",");
		int idx=(dataArr.length > 1?1:0);
		for (; idx < dataArr.length;idx++) {
			String pwdHistory = dataArr[idx];
			if (result.length() > 0 ) result += ",";
			result += pwdHistory;
		}
		if (result.length() > 0 ) result += ",";
		result += data.getPassword();
		return result;
	}
}
