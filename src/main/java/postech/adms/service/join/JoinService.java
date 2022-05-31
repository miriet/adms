package postech.adms.service.join;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import postech.adms.common.util.StringUtil;
import postech.adms.domain.member.AlumniMember;
import postech.adms.domain.user.AdminGroup;
import postech.adms.domain.user.AdminUser;
import postech.adms.repository.join.JoinRepository;
import postech.adms.repository.user.AdminGroupRepository;
import postech.adms.web.controller.form.JoinForm;

import javax.annotation.Resource;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("admsJoinService")
@Transactional("transactionManager")
public class JoinService {
	
	@Resource protected JoinRepository joinRepository;
	@Resource protected AdminGroupRepository adminGroupRepository;
	
	// 회원 아이디 패턴, 영문대소문자,숫자,영대소문자+숫자  4~20자
	static final String useridregexOne = "^[_a-zA-Z0-9-]{4,20}$"; 
	static final String useridregexTwo = "^.*(?=.{4,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$";
	static final String useridregexSC = ".*[~!@#$%^*+=_-].*";
	
	/**
	 * 아이디 패턴 체크
	 */
	public boolean validUserId(String userId) {
		boolean result = false;
		
		if(userId != null){
			int checkOutSpecialChar =  checkPattern(useridregexSC, userId);
			if(checkOutSpecialChar == 1){
				return false;
			}
			int checkCount = checkPattern(useridregexOne, userId);
			checkCount += checkPattern(useridregexTwo, userId);
			if(checkCount > 0){
				result = true;				
			}			
		}
		return result;
	}
	private int checkPattern(String pattern, String newPasswd) {
		int result = 1;
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(newPasswd);
		if (m.matches() == false) {
			result = 0;
		}
		return result;
	}
	
	/**
	 * 아이디 정보 찾기
	 */
	public AdminUser getDataByUserId(String userId) {
		return joinRepository.findByLoginId(userId);
	}
	
	/**
	 * 중복회업가입 확인
	 */
	public AdminUser findByNameAndBirthday(String name, String birthday) {
		return joinRepository.findByNameAndBirthday(name, birthday);
	}
	public AdminUser findByDi(String di) {
		return joinRepository.findByDi(di);
	}
	
	/**
	 * 회원가입 처리
	 */
	public void insertJoin(JoinForm joinForm, AlumniMember alumniMember) {
		AdminUser adminUser = new AdminUser();
		AdminGroup adminGroup = adminGroupRepository.findOne(6L);
		Date newDate = new Date();
		
		adminUser.setName(joinForm.getName());
		adminUser.setBirthday(StringUtil.changeDateFormat(joinForm.getBirthday()));
		adminUser.setLoginId(joinForm.getLoginId());
		adminUser.setPassword(new ShaPasswordEncoder().encodePassword(joinForm.getPassword(), "POSTECH"));
		adminUser.setEmail(joinForm.getEmail());
		adminUser.setDeptCode("000000");
		
		adminUser.setAdminGroup(adminGroup);
		adminUser.setLastChangePwd(newDate);
		adminUser.setPasswdHist(adminUser.getPassword()+",");
		adminUser.setPwdCount(0L);
		adminUser.setMember(alumniMember);
		adminUser.setCi(joinForm.getCi());
		adminUser.setDi(joinForm.getDi());
		
		joinRepository.save(adminUser);
	}
	
}
