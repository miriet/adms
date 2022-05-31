package postech.adms.service.menu;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import postech.adms.common.context.CmsRequestContext;
import postech.adms.domain.menu.AdminMenu;
import postech.adms.dto.menu.MenuTreeDto;
import postech.adms.repository.menu.MenuRepository;
import postech.adms.service.authority.AdminGroupService;
import postech.adms.service.menu.predicate.MenuPredicate;

import com.google.common.collect.Lists;

@Service("admsMenuService")
@Transactional("transactionManager")
public class MenuService {
	@Resource
	protected MenuRepository menuRepository;
	
	@Resource(name="admsAdminGroupService")
	protected AdminGroupService adminRoleService;
	
	@Transactional(readOnly=true)
	public List<AdminMenu> findRootMenu(Long parentMenuId){
		List<AdminMenu> result = menuRepository.findByParentMenu_MenuId(parentMenuId);
		
		for(Iterator<AdminMenu> it = result.iterator(); it.hasNext() ;){
			AdminMenu menu = it.next();
			Long count = menuRepository.countSubMenu(MenuPredicate.menuAuthorityPredicate(menu, CmsRequestContext.getCmsRequestContext().getUser().getPermissionNames()));
			
			if(count <= 0L){
				it.remove();
			}
		}
		
		return result;
	}
	
	@Transactional(readOnly=true)
	public List<AdminMenu> findLeafMenu(Long parentMenuId){
		AdminMenu parentMenu = menuRepository.findOne(parentMenuId);
		return menuRepository.findSubMenu(MenuPredicate.menuAuthorityPredicate(parentMenu, CmsRequestContext.getCmsRequestContext().getUser().getPermissionNames()));
	}
	
	@Transactional(readOnly=true)
	public AdminMenu findByMenuPath(String menuPath){
		return menuRepository.findByMenuPath(menuPath);
	}
	
	@Transactional(readOnly=true)
	public List<MenuTreeDto> readMenuTreeByParentMenuId(Long parentMenuId){
		//List<AdminMenu> menuList = (List<AdminMenu>) menuRepository.findAll(MenuPredicate.menuTreePredicate(parentMenuId));
		List<AdminMenu> menuList = menuRepository.findByParentMenu_MenuIdAndIsDel(parentMenuId,Boolean.FALSE);
		List<MenuTreeDto> result = Lists.newArrayList();
		
		for (AdminMenu adminMenu : menuList) {
			MenuTreeDto dto = new MenuTreeDto();
			dto.setMenuId(adminMenu.getMenuId());
			dto.setMenuName(adminMenu.getName());
			//dto.setHasChildren(!adminMenu.getIsLeaf());
			dto.setHasChildren(true);
			result.add(dto);
		}
		return result;
	}
	
	/*
	@Transactional(readOnly=true)
	public List<AdminPermissionTreeDto> readPermissionTreeByMenuId(Long menuId,Long roleId){
		AdminMenu menu = menuRepository.findOne(menuId);
		AdminGroup adminGroup = adminRoleService.findOne(roleId);
		Set<AdminPermission> groupPermissions = adminGroup.getGroupPermissions();
		
		Set<AdminPermission> menuPermissions = menu.getMenuPermissions();
		
		
		List<AdminPermissionTreeDto> result = Lists.newArrayList();
		
		for (AdminPermission menuPermission : menuPermissions) {
			AdminPermissionTreeDto dto = new AdminPermissionTreeDto();
			dto.setPermissionId(menuPermission.getPermissionCode());
			dto.setPermissionName(menuPermission.getPermissionName());
			dto.setHasChildren(false);
			dto.setChecked(groupPermissions.contains(menuPermission));
			result.add(dto);
		}
		
		return result;
	}
*/
	
	/* 테스트 */
	/*
	@Transactional(readOnly=true)
	public List<MenuPermissionTreeDto> readMenuPermissionTree(Long parentMenuId, long roleId){
		//List<AdminMenu> menuList = (List<AdminMenu>) menuRepository.findAll(MenuPredicate.menuTreePredicate(parentMenuId));
		List<AdminMenu> menuList = menuRepository.findByParentMenu_MenuIdAndIsDel(parentMenuId,Boolean.FALSE);
		List<MenuPermissionTreeDto> result = Lists.newArrayList();
		
		for (AdminMenu adminMenu : menuList) {
			MenuPermissionTreeDto dto = new MenuPermissionTreeDto();
			dto.setMenuId(adminMenu.getMenuId());
			dto.setMenuName(adminMenu.getName());
			List<AdminPermissionTreeDto> readMenuPermissionTree = readPermissionTreeByMenuId(adminMenu.getMenuId(),roleId);
			dto.setMenuPermissionTree(readMenuPermissionTree);
			//dto.setHasChildren(!adminMenu.getIsLeaf());
			dto.setHasChildren(true);
			result.add(dto);
		}
		return result;
	}
	*/
}
