package postech.adms.common.persistence;

import org.apache.commons.collections.MapUtils;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class IdOverrideTableGenerator extends TableGenerator{
	public static final String ENTITY_NAME_PARAM = "entity_name";
    @SuppressWarnings("unchecked")
	private static final Map<String, Field> FIELD_CACHE = MapUtils.synchronizedMap(new HashMap<String, Field>());

    private String entityName;

    private Field getIdField(Class<?> clazz) {
        Field response = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                response = field;
                break;
            }
        }
        if (response == null && clazz.getSuperclass() != null) {
            response = getIdField(clazz.getSuperclass());
        }

        return response;
    }

    @Override
    public Serializable generate(SessionImplementor session, Object obj) {
    	String objName = obj.getClass().getName();
        if (!FIELD_CACHE.containsKey(objName)) {
            Field field = getIdField(obj.getClass());
            if (field == null) {
                throw new IllegalArgumentException("Cannot specify IdOverrideTableGenerator for an entity (" + objName + ") that does not have an Id field declared using the @Id annotation.");
            }
            field.setAccessible(true);
            FIELD_CACHE.put(objName, field);
        }
        Field field = FIELD_CACHE.get(objName);
        final Serializable id;
        try {
            id = (Serializable) field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if ( id != null ) {
            return id;
        }
        return super.generate(session, obj);
    }
    
    
    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        if (params.get("table_name") == null) {
            params.put("table_name", "SEQUENCE_GENERATOR");
        }

        if (params.get("segment_column_name") == null) {
            params.put("segment_column_name", "ID_NAME");
        }

        if (params.get("value_column_name") == null) {
            params.put("value_column_name", "ID_VAL");
        }

        if (params.get("increment_size") == null) {
            params.put("increment_size", 1);
        }
        super.configure(type, params, serviceRegistry);
        entityName = (String) params.get(ENTITY_NAME_PARAM);
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
