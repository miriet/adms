package postech.adms.audit;

import postech.adms.common.time.SystemTime;
import postech.adms.web.security.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.lang.reflect.Field;
import java.util.Calendar;

public class AuditableListener {
	@PrePersist
    public void setAuditCreatedBy(Object entity) throws Exception {
        if (entity.getClass().isAnnotationPresent(Entity.class)) {
            Field field = getSingleField(entity.getClass(), "auditable");
            field.setAccessible(true);
            if (field.isAnnotationPresent(Embedded.class)) {
                Object auditable = field.get(entity);
                if (auditable == null) {
                    System.out.println("setAuditCreatedBy::UserAuditable::"+auditable.getClass().getDeclaredField("updatedBy"));
//                    field.set(entity, new UserAuditable());
                    field.set(entity, new UserAuditable(2L,2L));
                    auditable = field.get(entity);
                }
                Field temporalCreatedField = auditable.getClass().getDeclaredField("dateCreated");
                Field temporalUpdatedField = auditable.getClass().getDeclaredField("dateUpdated");
                Field agentCreateField = auditable.getClass().getDeclaredField("createdBy");
                Field agentUpdateField = auditable.getClass().getDeclaredField("updatedBy");
                setAuditValueTemporal(temporalCreatedField, auditable);
                setAuditValueTemporal(temporalUpdatedField, auditable);
                setAuditValueAgent(agentCreateField, auditable);
                setAuditValueAgent(agentUpdateField, auditable);
            }
        }
    }

    @PreUpdate
    public void setAuditUpdatedBy(Object entity) throws Exception {
        if (entity.getClass().isAnnotationPresent(Entity.class)) {
            Field field = getSingleField(entity.getClass(), "auditable");
            field.setAccessible(true);
            if (field.isAnnotationPresent(Embedded.class)) {
                Object auditable = field.get(entity);
                if (auditable == null) {
                    System.out.println("setAuditUpdatedBy::UserAuditable::"+auditable.getClass().getDeclaredField("updatedBy"));
//                    field.set(entity, new UserAuditable());
                    field.set(entity, new UserAuditable(2L,2L));
                    auditable = field.get(entity);
                }
                Field temporalField = auditable.getClass().getDeclaredField("dateUpdated");
                Field agentField = auditable.getClass().getDeclaredField("updatedBy");
                setAuditValueTemporal(temporalField, auditable);
                setAuditValueAgent(agentField, auditable);
            }
        }
    }

    protected void setAuditValueTemporal(Field field, Object entity) throws IllegalArgumentException, IllegalAccessException {
        Calendar cal = SystemTime.asCalendar();
        field.setAccessible(true);
        field.set(entity, cal.getTime());
    }

    protected void setAuditValueAgent(Field field, Object entity) throws IllegalArgumentException, IllegalAccessException {
        try {
        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        	Calendar cal = SystemTime.asCalendar();
        	if (authentication != null && authentication.isAuthenticated()) {
        		Object principal = authentication.getPrincipal();
    			if ( principal instanceof String) {
    				field.setAccessible(true);
    		        field.set(entity, 1L);
    			} else {
	        		field.setAccessible(true);
	                field.set(entity, ((UserInfo)authentication.getPrincipal()).getId());
    			}
        	}
        } catch (IllegalStateException e) {
            //do nothing
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Field getSingleField(Class<?> clazz, String fieldName) throws IllegalStateException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException nsf) {
            // Try superclass
            if (clazz.getSuperclass() != null) {
                return getSingleField(clazz.getSuperclass(), fieldName);
            }

            return null;
        }
    }
}
