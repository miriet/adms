package postech.adms.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import postech.adms.common.persistence.FieldCopy;

public class EntityUtil {
	public static void transformDtoToModel(Object dto,Object model){
		Field[] fields = getFields(model);
		
		for(Field field : fields){
			FieldCopy fieldCopy = field.getAnnotation(FieldCopy.class);
			
			if(fieldCopy != null &&fieldCopy.isCopy()){
				if(field.getType() == int.class || field.getType() == Integer.class
						|| field.getType() == long.class || field.getType() == Long.class
						|| field.getType() == boolean.class || field.getType() == Boolean.class
						|| field.getType() == double.class || field.getType() == Double.class
						|| field.getType() == float.class || field.getType() == Float.class
						|| field.getType() == String.class || field.getType() == BigDecimal.class
						|| field.getType() == Date.class){
					if(!Modifier.isFinal(field.getModifiers())){
						String setMethodName = "set" + StringUtils.capitalize(field.getName());
						String getMethodName = "get" + StringUtils.capitalize(field.getName());
						
						Method setterMethod = ReflectionUtils.findMethod(model.getClass(), setMethodName,field.getType());
						Method getterMethod = ReflectionUtils.findMethod(dto.getClass(), getMethodName);
						
						if(setterMethod != null && getterMethod != null){
							Object parameter = ReflectionUtils.invokeMethod(getterMethod, dto);
							ReflectionUtils.invokeMethod(setterMethod, model,parameter);
						}
					}
				}
			}
		}
	}
	
	private static Field[] getFields(Object dto) {
		if (dto == null) return new Field[0];
		List<Class<?>> classes = new ArrayList<Class<?>>();
		Class<?> clazz = dto.getClass();
		Class<?> superclass = clazz.getSuperclass();
	    while (superclass != null)
	    {
	        classes.add(superclass);
	        superclass = superclass.getSuperclass();
	    }
	    classes.add(clazz);
		Set<Field> fields = new HashSet<Field>();
	    for (Class<?> clazz2 : classes)
	    {
	    	fields.addAll(Arrays.asList(clazz2.getDeclaredFields()));
	    }

	    return fields.toArray(new Field[fields.size()]);
	}
}
