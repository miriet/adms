package postech.adms.service.user.predicate;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import postech.adms.domain.user.QAdminGroup;
import postech.adms.domain.user.QAdminUser;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

public class UserPredicate {
	private static final QAdminUser adminUser= QAdminUser.adminUser;
	private static final QAdminGroup adminGroup = QAdminGroup.adminGroup;
	
	public static BooleanExpression nameLike(String name){
		return nameLike(adminUser,name);
	}
	
	public static BooleanExpression nameLike(QAdminUser user,String name){
		return user.name.like("%" + name + "%");
	}
	
	
	public static BooleanExpression userNameLike(String userName){
		return userNameLike(adminUser,userName);
	}
	
	public static BooleanExpression userNameLike(QAdminUser user,String userName){
		return user.loginId.like("%" + userName + "%");
	}
	
//	public static BooleanExpression siteIdEqual(String siteId) {
//		return siteIdEqual(adminUser,Long.parseLong(siteId));
//	}
//
//	public static BooleanExpression siteIdEqual(long siteId) {
//		return siteIdEqual(adminUser,siteId);
//	}
//
//	public static BooleanExpression siteIdEqual(QAdminUser user,long siteId){
//		return user.siteId.eq(siteId);
//	}
	
	public static BooleanExpression isDelEqual(boolean isDel){
		return isDelEqual(adminUser,isDel);
	}
	
	public static BooleanExpression isDelEqual(String isDel){
		return isDelEqual(adminUser,BooleanUtils.toBoolean(isDel));
	}
	
	public static BooleanExpression isDelEqual(QAdminUser user,boolean isDel){
		return user.isDel.eq(isDel);
	}
	
	public static BooleanExpression adminGroupIdEqual(String id){
		
		return adminGroup.groupId.eq(Long.parseLong(id));
		//return adminUser.userRoles.any().id.eq(Long.parseLong(id));
	}
	
	public static Predicate searchPredicate(Map<String,String> param){
		BooleanBuilder  booleanBuilder  = new BooleanBuilder();
		
		if(!StringUtils.isEmpty(param.get("isDel"))){
			booleanBuilder.and(isDelEqual(param.get("isDel")));
		}else{
			booleanBuilder.and(isDelEqual(false));
		}
		
		if(!StringUtils.isEmpty(param.get("name"))){
			booleanBuilder.and(nameLike(param.get("name")));
		}
		
		if(!StringUtils.isEmpty(param.get("loginId"))){
			booleanBuilder.and(userNameLike(param.get("loginId")));
		}
//
//		if(!StringUtils.isEmpty(param.get("siteId"))){
//			booleanBuilder.and(siteIdEqual(param.get("siteId")));
//		}
		
		if(!StringUtils.isEmpty(param.get("groupId"))){
			booleanBuilder.and(adminGroupIdEqual(param.get("groupId")));
		}
	
		return booleanBuilder;
	}
	
	public static Predicate userWithoutUserRole(Long siteId){
		BooleanBuilder  booleanBuilder  = new BooleanBuilder();
		JPASubQuery subQuery = new JPASubQuery();
		booleanBuilder.and(isDelEqual(false));
//		booleanBuilder.and(siteIdEqual(siteId));
//		booleanBuilder
//				.and(adminUser.id.notIn(subQuery.from(adminUser.getAdminUser(), adminRole).distinct().list(adminUser.id)));
		return booleanBuilder;
	}
}
