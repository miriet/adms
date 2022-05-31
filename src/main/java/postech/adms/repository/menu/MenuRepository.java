package postech.adms.repository.menu;

import postech.adms.common.repository.JpaQueryDslBaseRepository;
import postech.adms.domain.menu.AdminMenu;

import java.util.List;

public interface MenuRepository extends JpaQueryDslBaseRepository<AdminMenu,Long>,CustomMenuRepository{
	public List<AdminMenu> findByParentMenu_MenuId(Long id);
	public List<AdminMenu> findByParentMenu_MenuIdAndIsDel(Long id,boolean isDel);
	public AdminMenu findByMenuPath(String menuPath);
}
