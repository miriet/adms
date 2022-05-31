package postech.adms.service.menu.predicate;

import java.util.List;

import postech.adms.domain.menu.AdminMenu;
import postech.adms.domain.menu.QAdminMenu;
import postech.adms.domain.user.QAdminGroup;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

public class MenuPredicate {
	private static final QAdminMenu menu = QAdminMenu.adminMenu;
	
	public static Predicate menuAuthorityPredicate(AdminMenu parentAdminMenu, List<String> permissionNames){
		BooleanBuilder  booleanBuilder  = new BooleanBuilder();
		
		QAdminGroup permission = QAdminGroup.adminGroup;
		
		booleanBuilder.and(menu.isDel.eq(false));
		booleanBuilder.and(menu.parentMenu().eq(parentAdminMenu));
		booleanBuilder.and(permission.groupName.in(permissionNames));
		/*booleanBuilder.and(permission.permissionType.eq("VIEW"));*/
		
		return booleanBuilder;
	}
	
}
