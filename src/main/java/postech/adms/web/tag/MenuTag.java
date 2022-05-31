package postech.adms.web.tag;

import postech.adms.domain.menu.AdminMenu;
import postech.adms.service.menu.MenuService;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import java.util.List;

public class MenuTag extends RequestContextAwareTag {

	private static final long serialVersionUID = -312867896188083942L;

	private static final String RETURN_PARAM = "result";

	private Long menuId;
	private String result;
	private Boolean isLeaf;

	@Override
	protected int doStartTagInternal() throws Exception {
		if (this.getIsLeaf()) {
			pageContext.setAttribute(getResult(), getLeafMenu());
		} else {
			pageContext.setAttribute(getResult(), getRootMenu());
		}

		return 0;
	}

	private List<AdminMenu> getRootMenu() {
		WebApplicationContext ctx = getRequestContext().getWebApplicationContext();
		MenuService menuService = (MenuService) ctx.getBean("admsMenuService");

		return menuService.findRootMenu(menuId);
	}

	private List<AdminMenu> getLeafMenu() {
		WebApplicationContext ctx = getRequestContext().getWebApplicationContext();
		MenuService menuService = (MenuService) ctx.getBean("admsMenuService");

		return menuService.findLeafMenu(menuId);
	}

	public String getResult() {
		if (StringUtils.isEmpty(this.result)) {
			result = RETURN_PARAM;
		}
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
}
