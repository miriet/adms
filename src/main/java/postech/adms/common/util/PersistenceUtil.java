package postech.adms.common.util;

import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.PathBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PersistenceUtil {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static OrderSpecifier[] toOrder(PathBuilder<?> builder,Pageable pageable) {
		List<OrderSpecifier> result = new ArrayList<OrderSpecifier>();
		
		for(Sort.Order order : pageable.getSort()){
			Expression<Object> property = builder.get(order.getProperty());
			
			result.add(new OrderSpecifier(order.isAscending() ? com.mysema.query.types.Order.ASC
					: com.mysema.query.types.Order.DESC, property));
		}
		
		return result.toArray(new OrderSpecifier[result.size()]);
	}
}
