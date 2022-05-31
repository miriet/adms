package postech.adms.web.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import postech.adms.domain.user.AdminUser;
import postech.adms.dto.user.UserListDto;
import postech.adms.service.authority.AdminGroupService;
import postech.adms.service.department.DepartmentService;
import postech.adms.service.user.UserService;
import postech.adms.web.form.ChangePwdForm;
import postech.adms.web.message.MessageHandler;
import postech.adms.web.security.UserInfo;
import postech.adms.web.validator.BasicValidator;

/**
 * Created by miriet on 2017. 3. 27..
 */
@Controller("admsUserController")
@RequestMapping("/user")
public class AdminUserController extends BasicController {

	@Resource(name = "admsUserService")
	protected UserService userService;

	@Resource(name = "admsAdminGroupService")
	private AdminGroupService adminGroupService;

	@Resource(name = "departmentService")
	private DepartmentService departMentService;

	protected @Resource(name = "messageHandler") MessageHandler msgHandler;

	@PreAuthorize("isAuthenticated()")
	@RequestMapping("")
	public String init(ModelMap model) {
		return "user";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/list")
	public void readUsersWithoutUserRoles(@RequestParam Map<String, String> param, ModelMap model) {
		PageRequest page = new PageRequest(getCurrentPage(param.get("page")), Integer.parseInt(param.get("rows")),
				new Sort(Sort.Direction.DESC, "auditable.dateCreated"));
		Page<UserListDto> result = userService.readUsersWithoutUserRoles(Long.parseLong(param.get("siteId")), page);
		setModelWithkendoList(model, result);
	}

	/**
	 * 회원리스트 가져오기
	 *
	 * @param param
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/find")
	public void find(@RequestParam Map<String, String> param, ModelMap model) {
		PageRequest page = new PageRequest(getCurrentPage(param.get("page")), Integer.parseInt(param.get("rows")),
				new Sort(Sort.Direction.ASC, "auditable.dateCreated"));
		Page<UserListDto> result = userService.find(param, page);
		setModelWithkendoList(model, result);
	}

	/**
	 * 그룹별 사용자 찾기 (그룹관리에 사용)
	 *
	 * @param param
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/userGroupFind")
	public void userFind(@RequestParam Map<String, String> param, ModelMap model) {
		PageRequest page = new PageRequest(getCurrentPage(param.get("page")), Integer.parseInt(param.get("rows")),
				new Sort(Sort.Direction.ASC, "auditable.dateCreated"));
		Page<UserListDto> result = userService.userGroupFind(param, page);
		setModelWithkendoList(model, result);
	}

	/**
	 * 회원정보 변경화면
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/{id}")
	public String findOne(@PathVariable("id") Long id, ModelMap model) {
		// 접속자 정보
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("deptList", departMentService.findByIsDel(false));
		model.addAttribute("groupList", adminGroupService.findByIsDel(false));
		model.addAttribute("result", userService.findOne(id));
		return "popup/user/userUpdateForm";
	}

	/**
	 * 회원정보 변경처리
	 *
	 * @param user
	 * @param bindingResult
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/update")
	public void update(@ModelAttribute AdminUser user, BindingResult bindingResult, ModelMap model) {
		// new ShaPasswordEncoder().encodePassword(arg0, arg1)
		if (bindingResult.hasErrors()) {

		}
		userService.update(user);
	}

	/**
	 * admin 패스워드 초기화 버튼 클릭시 팝업창 띄워주는 메소드
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/initPasswordForm/{id}")
	public String initPasswordForm(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("result", userService.findOne(id));
		return "popup/user/initPasswordForm";
	}

	/**
	 * 바말번호 변경처리
	 *
	 * @param user
	 * @param bindingResult
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/initPassword")
	public void initPassword(@ModelAttribute AdminUser user, BindingResult bindingResult, ModelMap model) {
		// validator.validate(contentProvider, bindingResult);
		if (bindingResult.hasErrors()) {

		}
		userService.changePassword(user);
	}

	/**
	 * 등록화면
	 *
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/insertForm")
	public String insertForm(ModelMap model) {
		model.addAttribute("groupList", adminGroupService.findByIsDel(false));
		model.addAttribute("deptList", departMentService.findByIsDel(false));
		return "popup/user/userWriteForm";
	}

	/**
	 * 등록 처리
	 *
	 * @param user
	 * @param bindingResult
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/insert")
	public void insert(@ModelAttribute AdminUser user, BindingResult bindingResult, ModelMap model) {
		// validator.validate(contentProvider, bindingResult);
		// new ShaPasswordEncoder().encodePassword(arg0, arg1)
		if (bindingResult.hasErrors()) {

		}

		userService.insert(user);
	}

	/**
	 * 사용자계정잠금 해제 처리
	 *
	 * @param user
	 * @param bindingResult
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/userUnlock")
	public void userUnlock(@ModelAttribute AdminUser user, BindingResult bindingResult, ModelMap model) {
		userService.userUnlock(user);
	}

	/**
	 * 기간경과에 따른 비밀번호 변경화면
	 *
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/semestralChagePasswd")
	public String semestralChagePasswd(ModelMap model) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("userInfo", userInfo);
		return "popup/user/semestralChagePasswd";
	}

	/**
	 * 기간경과에 따른 비밀번호 변경처리
	 *
	 * @param user
	 * @param bindingResult
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/changPwdProcess")
	public void changPwdProcess(ModelMap model, @ModelAttribute("inputForm") ChangePwdForm inputForm,
			BindingResult errors) {
		valditate(inputForm, errors);
		if (!errors.hasErrors()) {
			userService.changPwdProcess(inputForm);
			model.addAttribute("code", "0");
			model.addAttribute("message", "비밀번호가 변경 되었습니다.");
		} else {
			model.addAttribute("code", "-1");
			model.addAttribute("message", msgHandler.getMessageOne(errors.getAllErrors()));
		}
	}

	private void valditate(ChangePwdForm inputForm, BindingResult errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwd", "adms.empty", "비밀번호를 입력해주세요");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPasswd", "adms.empty", "새 비밀번호를 입력해주세요");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newconfirmPasswd", "adms.empty", "새 비밀번호 확인를 입력해주세요");

		final AdminUser user = userService.findOne(inputForm.getUserId());

		// 회원정보 확인
		if (!errors.hasErrors()) {
			if (user != null) {
				if (userService.matches(inputForm.getPasswd(), user.getPassword()) == false) {
					errors.rejectValue("passwd", "adms.msg.notmatch.password", "기존 비밀번호가 일치 하지 않습니다.");
				}
			} else {
				errors.rejectValue("passwd", "adms.msg.notfound.member", "사용자 정보가 존재 하지 않습니다.");
			}
		}

		if (!errors.hasErrors()) {
			validatePasswd(errors, "newPasswd", inputForm.getNewconfirmPasswd(), user.getLoginId());
			if (!inputForm.getNewPasswd().equals(inputForm.getNewconfirmPasswd())) {
				errors.rejectValue("newPasswd", "NotMatch.refer.hospital", "변경할 비밀번호가 일치하지 않습니다.");
			}
		}

		// 이전비밀번호 확인
		if (!errors.hasErrors()) {
			final String encPasswd = userService.encryptPasswd(inputForm.getNewPasswd());
			if (user.getPasswdHist().indexOf(encPasswd) > -1) {
				errors.rejectValue("passwd", "adms.oldPasswd.use", "이전 비밀번호와 동일하게 사용할 수 없습니다.");
			}
		}
	}

	private void validatePasswd(BindingResult errors, String name, String newconfirmPasswd, String loginId) {
		// 비밀번호 패턴, 8~30자 영문 대소문자,숫자,특수문자 사용
		final String passwdRegexS = ".*[a-z].*";
		final String passwdRegexC = ".*[A-Z].*";
		final String passwdRegexN = ".*[0-9].*";
		final String passwdRegexSC = ".*[!@#$%^*+=-?].*";

		// 비밀번호에 공백(스페이스)을 사용하실 수 없습니다.
		if (newconfirmPasswd != null && newconfirmPasswd.indexOf(" ") > -1) {
			errors.rejectValue("newconfirmPasswd", "adms.NotMatch", "비밀번호에 공백을 사용하실 수 없습니다.");
		}

		// 비밀번호는 8자 이상 30자 이하로 입력해 주세요.
		if (newconfirmPasswd.length() < 8 || newconfirmPasswd.length() > 30) {
			errors.rejectValue("newconfirmPasswd", "adms.NotMatch", "비밀번호는 최소 8자 이상 30자 이하로 입력해 주세요.");
		}

		// 계정명 제외
		if (newconfirmPasswd.indexOf(loginId) > -1) {
			errors.rejectValue(name, "adms.NotMatch", "새 비밀번호에 계정명이 포함되어 있습니다.");
		}

		// 대문자, 소문자, 숫자, 특수문자 중 3가지 이상 조합하여 등록해 주세요.
		int checkCount = checkPattern(passwdRegexC, newconfirmPasswd);
		checkCount += checkPattern(passwdRegexS, newconfirmPasswd);
		checkCount += checkPattern(passwdRegexN, newconfirmPasswd);
		checkCount += checkPattern(passwdRegexSC, newconfirmPasswd);

		if (checkCount < 3) {
			errors.rejectValue(name, "adms.NotMatch", "대문자, 소문자, 숫자, 특수문자 중 3가지 이상 조합하여 등록해 주세요.");
		}

		// 일련숫자 또는 알파벳 순서대로 3자이상 사용하는 비밀번호는 사용불가
		if (checkContinuous3Character(newconfirmPasswd)) {
			errors.rejectValue("newconfirmPasswd", "adms.NotMatch", "일려번호(연속된 숫자,영문자)를 입력하실 수 없습니다.");
		}

	}

	private static int checkPattern(String pattern, String newPasswd) {
		int result = 1;
		final Pattern p = Pattern.compile(pattern);
		final Matcher m = p.matcher(newPasswd);
		if (m.matches() == false) {
			result = 0;
		}
		return result;
	}

	/**
	 * 일련숫자 또는 알파벳 순서대로 3자이상 사용하는 비밀번호는 사용불가
	 *
	 * @param newconfirmPasswd
	 * @return
	 */
	public static boolean checkContinuous3Character(String newconfirmPasswd) {
		final byte[] b = newconfirmPasswd.getBytes();
		final int p = newconfirmPasswd.length();

		if (newconfirmPasswd.length() > 2) {
			// 연속된 3개의 문자 사용불가 (오름차순)
			for (int i = 0; i < ((p * 2) / 3); i++) {
				final int b1 = b[i] + 1;
				final int b2 = b[i + 1];
				final int b3 = b[i + 1] + 1;
				// int b4 = b[i + 2];

				if ((b1 == b2) && (b2 == b3)) {
					return true;
				} else {
					continue;
				}
			}
			// 연속된 3개의 문자 사용불가 (내림차순)
			for (int i = 0; i < ((p * 2) / 3); i++) {
				final int b1 = b[i + 1];
				final int b2 = b[i + 2];

				if ((b[i] == b1) && (b[i] == b2)) {
					return true;
				} else {
					continue;
				}
			}
		}
		return false;
	}

	/**
	 * 기간경과에 따른 비밀번화 변경화면(다음에 변경하기)
	 *
	 * @param user
	 * @param bindingResult
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/changPwdDelay/{id}")
	public void changPwdDelay(@PathVariable("id") Long id, ModelMap model) {
		userService.userChangPwdDelay(id);
	}

	/**
	 * 일반동창회원 로그인시, 최초 진입화면
	 *
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/alumnusmembers")
	public String alumnusmembers(ModelMap model) {
		return "member/alumnusmembersForm";
	}

}
