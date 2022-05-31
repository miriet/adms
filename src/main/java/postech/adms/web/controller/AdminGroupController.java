package postech.adms.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import postech.adms.domain.user.AdminGroup;
import postech.adms.domain.user.AdminUser;
import postech.adms.service.authority.AdminGroupService;

import javax.annotation.Resource;
import java.util.Map;

@Controller("admsAdminGroupController")
@RequestMapping("/group")
public class AdminGroupController extends BasicController {

	@Resource(name = "admsAdminGroupService")
	protected AdminGroupService adminGroupService;

	@PreAuthorize("isAuthenticated()")
	@RequestMapping("")
	public String init(ModelMap model) {
		return "group";
	}

	/**
	 * 그룹리스트
	 *
	 * @param param
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/find")
	public void find(@RequestParam Map<String, String> param, ModelMap model) {
		PageRequest page = new PageRequest(getCurrentPage(param.get("page")), Integer.parseInt(param.get("rows")),
				new Sort(Sort.Direction.DESC, "auditable.dateCreated"));
		Page<AdminGroup> result = adminGroupService.find(param, page);
		setModelWithkendoList(model, result);
	}

	/**
	 * 그룹입력 화면
	 *
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/insertForm")
	public String insertForm(ModelMap model) {
		return "popup/user/groupWriteForm";
	}

	/**
	 * 그룹입력 처리
	 *
	 * @param role
	 * @param bindingResult
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/insert")
	public void insert(@ModelAttribute AdminGroup role, BindingResult bindingResult, ModelMap model) {
		// validator.validate(contentProvider, bindingResult);
		// new ShaPasswordEncoder().encodePassword(arg0, arg1)
		if (bindingResult.hasErrors()) {

		}
		adminGroupService.insert(role);
	}

	/**
	 * 그룹 수정화면
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/{id}")
	public String findOne(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("result", adminGroupService.findOne(id));
		return "popup/user/groupUpdateForm";
	}

	/**
	 * 그룹 수정처리
	 *
	 * @param adminGroup
	 * @param bindingResult
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/update")
	public void update(@ModelAttribute AdminGroup adminGroup, BindingResult bindingResult, ModelMap model) {
		// validator.validate(contentProvider, bindingResult);
		// new ShaPasswordEncoder().encodePassword(arg0, arg1)
		if (bindingResult.hasErrors()) {

		}
		adminGroupService.update(adminGroup);
	}

	/**
	 * 그룹에서 사용자 삭제 처리
	 *
	 * @param userId
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/removeUser")
	public void removeUser(@RequestParam("userId") long userId, ModelMap model) {
		adminGroupService.removeUser(userId);
	}

	/**
	 * 사용자 그룹변경 화면
	 *
	 * @param groupId
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/addUserForm/{groupId}")
	public String addUserForm(@PathVariable("groupId") Long groupId, ModelMap model) {
		model.addAttribute("groupId", groupId);
		return "popup/user/addUserToGroupForm";
	}

	/**
	 * 사용자 그룹변경 처리
	 *
	 * @param roleId
	 * @param selectedUserId
	 * @param model
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/addUser")
	public void addUser(@RequestParam("groupId") long roleId, @RequestParam("selectedUserId") long[] selectedUserId,
			ModelMap model) {
		adminGroupService.addUserToGroup(roleId, selectedUserId);
	}

	/*
	 * @PreAuthorize("hasRole('PERMISSION_GROUP_UPDATE')")
	 *
	 * @RequestMapping("/setAuth") public void setAuth(@RequestParam long
	 * roleId, @RequestParam("selectedPermission") String[] selectedPermission,
	 * ModelMap model) { //model.addAttribute("result",
	 * adminGroupService.findOne(id)); //model.addAttribute("roleId",id);
	 * adminGroupService.setAuth(roleId,selectedPermission); }
	 */

	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/setAuthForm/{id}")
	public String setAuthForm(@PathVariable("id") Long id, ModelMap model) {
		// model.addAttribute("result", adminGroupService.findOne(id));
		model.addAttribute("groupId", id);
		return "popup/user/setAuthForm";
	}

}
