package postech.adms.dto.menu;

import java.io.Serializable;

public class AdminPermissionTreeDto implements Serializable{

	private static final long serialVersionUID = -5150606488839517771L;
	
	private String permissionId;
	private String permissionName;
	private boolean hasChildren;
	private boolean checked;
	
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public boolean isHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
