package postech.adms.dto.menu;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;



public class MenuPermissionTreeDto implements Serializable{

	private static final long serialVersionUID = -5150606488839517771L;
	
	private Long menuId;
	private String menuName;
	private boolean hasChildren;
	private List<AdminPermissionTreeDto> menuPermissionTree = Lists.newArrayList();
	
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public boolean isHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public List<AdminPermissionTreeDto> getMenuPermissionTree() {
		return menuPermissionTree;
	}
	public void setMenuPermissionTree(List<AdminPermissionTreeDto> menuPermissionTree) {
		this.menuPermissionTree = menuPermissionTree;
	}
	
}
