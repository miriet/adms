package postech.adms.common.predicate;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import postech.adms.audit.QUserAuditable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class UserAuditablePredicate {
	
	public static BooleanExpression dateCreatedBetween(QUserAuditable userAuditable,String from,String to){
		Date fromDate = null;
		Date toDate = null;
		
		try{
			fromDate = DateUtils.parseDate(from, "yyyyMMdd");
			toDate = DateUtils.parseDate(to, "yyyyMMdd");
		}catch(ParseException e){
			fromDate = new Date();
			toDate = new Date();
		}
		System.out.println(fromDate + "  ~ " + toDate);
		return dateCreatedBetween(userAuditable,fromDate,DateUtils.addDays(toDate, 1));
	}
	
	public static BooleanExpression dateCreatedBetween(QUserAuditable userAuditable,Date from,Date to){
		return userAuditable.dateCreated.goe(from).and(userAuditable.dateCreated.lt(to));
	}
	
	
	public static BooleanExpression dateUpdatedBetween(QUserAuditable userAuditable,String from,String to){
		Date fromDate = null;
		Date toDate = null;
		
		try{
			fromDate = DateUtils.parseDate(from, "yyyyMMdd");
			toDate = DateUtils.parseDate(to, "yyyyMMdd");
		}catch(ParseException e){
			fromDate = new Date();
			toDate = new Date();
		}
		System.out.println(fromDate + "  ~ " + toDate);
		return dateUpdatedBetween(userAuditable,fromDate,DateUtils.addDays(toDate, 1));
	}
	
	public static BooleanExpression dateUpdatedBetween(QUserAuditable userAuditable,Date from,Date to){
		return userAuditable.dateUpdated.goe(from).and(userAuditable.dateUpdated.lt(to));
	}
	
	
	
	public static Predicate apiPredicate(QUserAuditable userAuditable,Map<String,String> param){
		BooleanBuilder  booleanBuilder  = new BooleanBuilder();
		/* ex 
		 * select * from cms_cp where date_created >= "20160530" and date_created < "20160630" or (date_updated >= "20160530" and date_created < "20160630")
		 * admin에서 새로 등록할 항목과 업데이트 항목리스트를 가져 온다
		 * 
		 */
		if(!StringUtils.isEmpty(param.get("from")) && !StringUtils.isEmpty(param.get("to"))){
			booleanBuilder.and(dateCreatedBetween(userAuditable,param.get("from"),param.get("to"))).or(dateUpdatedBetween(userAuditable,param.get("from"),param.get("to")));
		}
		return booleanBuilder;
	}
}
