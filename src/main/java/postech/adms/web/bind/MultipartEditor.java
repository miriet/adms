package postech.adms.web.bind;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyEditorSupport;

public class MultipartEditor extends PropertyEditorSupport{
	public void setValue(Object value) {
		MultipartFile multipartFile = (MultipartFile) value;
		if (multipartFile != null && multipartFile.getSize() > 0) {
			super.setValue(multipartFile);
		} else {
			super.setValue(null);
		}
	}
	
	@Override
	public void setAsText(String text){
		setValue(null);
	}
	
	@Override
	public String getAsText() {
		System.out.println("111");
		return (StringUtils.isEmpty(getValue()))
                ? getValue().toString()
                : null;
	}
}
