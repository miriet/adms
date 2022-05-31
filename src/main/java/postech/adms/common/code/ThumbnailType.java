package postech.adms.common.code;

import postech.adms.common.context.CmsRequestContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ThumbnailType implements CommonCode,Serializable{
	private static final long serialVersionUID = 1L;
	
	protected static final Map<String, CommonCode> TYPES = new LinkedHashMap<String, CommonCode>();
	
	public static final ThumbnailType RECTANGLE = new ThumbnailType("RECTANGLE","cms.code.thumbnail.type.rectangle","Rectangle");
	public static final ThumbnailType SQUARE = new ThumbnailType("SQUARE","cms.code.thumbnail.type.square","Square");
	public static final ThumbnailType WIDE = new ThumbnailType("WIDE","cms.code.thumbnail.type.wide","Wide");
	
	protected String code;
	protected String key;
	protected String name;
	
	public ThumbnailType(final String code, final String key, final String defaultName){
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
