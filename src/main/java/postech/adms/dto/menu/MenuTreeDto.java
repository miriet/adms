package postech.adms.dto.menu;

import java.io.Serializable;

public class MenuTreeDto implements Serializable{

	private static final long serialVersionUID = -5150606488839517771L;
	
	private Long menuId;
	private String menuName;
	private boolean hasChildren;
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
}
