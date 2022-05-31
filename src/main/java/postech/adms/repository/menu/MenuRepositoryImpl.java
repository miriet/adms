package postech.adms.repository.menu;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import postech.adms.domain.menu.AdminMenu;
import postech.adms.domain.menu.QAdminMenu;
import postech.adms.domain.user.QAdminGroup;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;

public class MenuRepositoryImpl implements CustomMenuRepository{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long countSubMenu(Predicate predicate) {
		JPAQuery query = new JPAQuery(entityManager);

		QAdminMenu menu = QAdminMenu.adminMenu;
		QAdminGroup permisson = QAdminGroup.adminGroup;
		
		return query.from(menu)
			.innerJoin(menu.menuPermissions,permisson)
			.where(predicate)
			.count();
	}

	@Override
	public List<AdminMenu> findSubMenu(Predicate predicate) {
		JPAQuery query = new JPAQuery(entityManager);
		
		QAdminMenu menu = QAdminMenu.adminMenu;
		QAdminGroup permisson = QAdminGroup.adminGroup;
		
		return query.from(menu)
				.innerJoin(menu.menuPermissions,permisson)
				.where(predicate)
				.distinct()
				.list(menu);
	}
}
