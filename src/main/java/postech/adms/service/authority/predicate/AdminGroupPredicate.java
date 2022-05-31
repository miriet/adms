package postech.adms.service.authority.predicate;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.StringUtils;
import postech.adms.domain.user.QAdminGroup;

import java.util.Map;

public class AdminGroupPredicate {
	
	private static final QAdminGroup adminGroup = QAdminGroup.adminGroup;
	
	public static BooleanExpression nameLike(String name){
		return nameLike(adminGroup,name);
	}
	
	public static BooleanExpression nameLike(QAdminGroup adminGroup,String name){
		return adminGroup.groupName.like("%" + name + "%");
	}
	
	
//	public static BooleanExpression siteIdEqual(String siteId) {
//		return siteIdEqual(adminGroup,Long.parseLong(siteId));
//	}
//
//	public static BooleanExpression siteIdEqual(long siteId) {
//		return siteIdEqual(adminGroup,siteId);
//	}
//
//	public static BooleanExpression siteIdEqual(QAdminGroup adminGroup,long siteId){
//		return adminRole.siteId.eq(siteId);
//	}
	
	public static BooleanExpression isDelEqual(boolean isDel){
		return isDelEqual(adminGroup,isDel);
	}
	
	public static BooleanExpression isDelEqual(String isDel){
		return isDelEqual(adminGroup,BooleanUtils.toBoolean(isDel));
	}
	
	public static BooleanExpression isDelEqual(QAdminGroup adminGroup,boolean isDel){
		return adminGroup.isDel.eq(isDel);
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
		
		if(!StringUtils.isEmpty(param.get("groupName"))){
			booleanBuilder.and(nameLike(param.get("groupName")));
		}
		
//		if(!StringUtils.isEmpty(param.get("siteId"))){
//			booleanBuilder.and(siteIdEqual(param.get("siteId")));
//		}
	
		return booleanBuilder;
	}
	
}
