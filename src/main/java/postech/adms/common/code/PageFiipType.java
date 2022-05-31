package postech.adms.common.code;

import postech.adms.common.context.CmsRequestContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PageFiipType implements CommonCode,Serializable{
	private static final long serialVersionUID = 1L;
	
	protected static final Map<String, CommonCode> TYPES = new LinkedHashMap<String, CommonCode>();
	
	public static final PageFiipType FORWARD = new PageFiipType("FORWARD","cms.code.pageflip.type.forward","Forward");
	public static final PageFiipType REVERSE = new PageFiipType("REVERSE","cms.code.pageflip.type.reverse","Reverse");
	
	protected String code;
	protected String key;
	protected String name;
	
	public PageFiipType(final String code, final String key, final String defaultName){
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
