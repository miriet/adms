package postech.adms.web.controller;

import postech.adms.service.menu.MenuService;
import postech.adms.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller("admsCommonController")
@RequestMapping("/common")
public class CommonController extends BasicController {
	@Resource(name = "admsMenuService")
	protected MenuService menuService;

	@Resource(name = "admsUserService")
	protected UserService userService;

	@RequestMapping("/rootMenuTree")
	public void readRootMenuTree(ModelMap model) {
		readMenuTree(1L, model);
	}

	@RequestMapping("/menuTree/{parentMenuId}")
	public void readMenuTree(@PathVariable("parentMenuId") Long parentMenuId, ModelMap model) {
		// System.out.println("parentMenuId : " + parentMenuId);
		model.addAttribute("result", menuService.readMenuTreeByParentMenuId(parentMenuId));
	}

	/*
	@RequestMapping("/menuPermissionTree/{parentMenuId}")
	public void menuPermissionTree(@PathVariable("parentMenuId") Long parentMenuId,@RequestParam long roleId,ModelMap model){
		System.out.println("parentMenuId : " + parentMenuId);
		model.addAttribute("result", menuService.readMenuPermissionTree(parentMenuId,roleId));
	}
	*/
}
