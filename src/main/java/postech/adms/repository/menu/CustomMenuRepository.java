package postech.adms.repository.menu;

import com.mysema.query.types.Predicate;
import postech.adms.domain.menu.AdminMenu;

import java.util.List;

public interface CustomMenuRepository {
	public Long countSubMenu(Predicate predicate);
	public List<AdminMenu> findSubMenu(Predicate predicate);
}
