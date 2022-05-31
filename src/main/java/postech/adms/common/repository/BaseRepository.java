package postech.adms.common.repository;

import java.util.Map;

public interface BaseRepository {
	public void enableFilter(String filterName,Map<String,Object> param);
	public Map<String, Object> getIdMetadata(Class<?> entityClass);
}
