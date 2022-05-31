package postech.adms.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BasicValidator implements Validator{
	protected Pattern pattern = null;
	protected Matcher matcher = null;

	protected String emailRegex 		= "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
	protected String phoneRegex 		= "^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})";
	protected String plusIntegerRegex	= "\\d+";
	protected String doubleRegex 		= "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";

	protected <T> void listValidate(Errors errors,List<T> value,String fieldName,String defaultMessage){

		if (value == null || value.size() == 0) {
			errors.reject("cms.validation.field.required", new Object[]{fieldName},defaultMessage);
		}
	}

	protected void emailValidate(Errors errors,String value){
		pattern = Pattern.compile(emailRegex);
		matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			errors.reject("dam.field.invalid.email", "이메일 형식이 올바르지 않습니다.");
		}
	}

	protected void plusIntegerValidate(Errors errors,String fieldName,String value){
		pattern = Pattern.compile(plusIntegerRegex);
		matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			errors.reject("dam.field.invalid.number", new Object[]{fieldName},"숫자만 가능 합니다.");
		}
	}

	protected void plusDoubleValidate(Errors errors,String fieldName,String value){
		pattern = Pattern.compile(doubleRegex);
		matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			errors.reject("dam.field.invalid.number", new Object[]{fieldName},"숫자만 가능 합니다.");
		}
	}

	public void dateValidate(Errors errors,String fieldName,String value,String format){
		 SimpleDateFormat sdf = new SimpleDateFormat(format);
		 sdf.setLenient(false);

		 try {
			 sdf.parse(value);
		 } catch (ParseException e) {
			 errors.reject("ecom.field.invalid.date", new Object[]{fieldName},"날짜포맷이 올바르지 않습니다.");
		 }
	}

	protected void arrayDuplicationValidator(Errors errors,String fieldName,String[] value){
		for (int i = 0; i < value.length; i++) {
	        String str1 = value[i];
	        for (int j = 0; j < value.length; j++) {
	            if (i == j) continue;
	            String str2 = value[j];
	            if (str1.equals(str2)) {
	            	errors.reject("dam.field.array.duplicate", new Object[]{fieldName},"중복되었습니다");
	                break;
	            }
	        }
	    }
	}
}
