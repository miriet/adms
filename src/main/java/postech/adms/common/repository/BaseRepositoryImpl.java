package postech.adms.common.repository;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

public class BaseRepositoryImpl implements BaseRepository{
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public void enableFilter(String filterName, Map<String,Object> param) {
		Session session = (Session)entityManager.getDelegate();
		Filter filter = session.enableFilter(filterName);
		
		if(param != null){
			for(String key : param.keySet()){
				filter.setParameter(key, param.get(key));
			}
		}
	}
	
	@Override
	public Map<String, Object> getIdMetadata(Class<?> entityClass) {
        Map<String, Object> response = new HashMap<String, Object>();
        SessionFactory sessionFactory = ((Session)entityManager.getDelegate()).getSessionFactory();
        
        ClassMetadata metadata = sessionFactory.getClassMetadata(entityClass);
        if (metadata == null) {
            return null;
        }
        
        String idProperty = metadata.getIdentifierPropertyName();
        response.put("name", idProperty);
        Type idType = metadata.getIdentifierType();
        response.put("type", idType);

        return response;
    }
}
