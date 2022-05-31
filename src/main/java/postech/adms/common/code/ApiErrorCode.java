package postech.adms.common.code;

import postech.adms.common.context.CmsRequestContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApiErrorCode implements CommonCode,Serializable{
private static final long serialVersionUID = 1L;
	
	protected static final Map<String, CommonCode> TYPES = new LinkedHashMap<String, CommonCode>();
	
	public static final ApiErrorCode E001 = new ApiErrorCode("E001","cms.code.api.error.e001","No authority");
	public static final ApiErrorCode E002 = new ApiErrorCode("E002","cms.code.api.error.e002","No such Id");
	public static final ApiErrorCode E003 = new ApiErrorCode("E003","cms.code.api.error.e003","Wrong parameter");
	public static final ApiErrorCode E004 = new ApiErrorCode("E004","cms.code.api.error.e004","temporary server error");	
	
	protected String code;
	protected String key;
	protected String name;
	
	public ApiErrorCode(final String code, final String key, final String defaultName){
		this.key = key;
		this.name = CmsRequestContext.getCmsRequestContext().getMessageSourceAccessor().getMessage(key,defaultName);
		setCode(code);
	}
	
	public static List<CommonCode> getCodes(){
		return new ArrayList<CommonCode>(TYPES.values());
	}
	
	public static CommonCode getInstance(final String code){
		return TYPES.get(code);
	}
	
	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		
		if (!TYPES.containsKey(code)) {
            TYPES.put(code, this);
        }
	}
	
	@Override
	public String getName(){
		return name;
	}
}
