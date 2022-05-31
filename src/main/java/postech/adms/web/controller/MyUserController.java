package postech.adms.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import postech.adms.domain.user.AdminUser;
import postech.adms.service.authority.AdminGroupService;
import postech.adms.service.department.DepartmentService;
import postech.adms.service.user.UserService;
import postech.adms.web.security.UserInfo;

import javax.annotation.Resource;

/*
*  일반회원이 로그인했을때 보는 자기정보 조회화면
* */

@Controller("myUserController")
@RequestMapping("/myinfo")
public class MyUserController extends BasicController {
	@Resource(name = "admsUserService")
	protected UserService userService;

	@Resource(name = "admsAdminGroupService")
	private AdminGroupService adminGroupService;

	@Resource(name = "departmentService")
	private DepartmentService departmentService;

	@PreAuthorize("isAuthenticated()")
	@RequestMapping("")
	public String init(ModelMap model) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AdminUser result = userService.findOne(userInfo.getId());

		model.addAttribute("result", result);
		model.addAttribute("deptList", departmentService.findByIsDel(false));
		return "alumnusMemberUpdateForm";
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
		if (user.getId() != null) {
			userService.mamberUpdate(user);
			model.addAttribute("code", "0");
			model.addAttribute("message", "저장 되었습니다.");
		} else {
			model.addAttribute("code", "1");
			model.addAttribute("message", "저장되지 않았습니다. 관리자에게 문의 하시기 바랍니다.");
		}
	}

	// --------------------------------------------------------
	// 삭제 처리
	// --------------------------------------------------------
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/delete/{id}/{info}")
	public void delete(@PathVariable("id") Long id, @PathVariable("info") String info, ModelMap model) {
		if (info.equalsIgnoreCase("degreeInfo")) { // 학위정보 삭제
			userService.deleteDegree(id);
		} else if (info.equalsIgnoreCase("addressInfo")) { // 주소정보 삭제
			userService.deleteAddress(id);
		}
	}
}
