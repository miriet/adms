package postech.adms.common.code;

import postech.adms.common.context.CmsRequestContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ThumbnailSizeType implements CommonCode,Serializable{
private static final long serialVersionUID = 1L;
	
	protected static final Map<String, CommonCode> TYPES = new LinkedHashMap<String, CommonCode>();
	
	public static final ThumbnailSizeType SMALL 	= new ThumbnailSizeType("S","cms.code.thumbnail.size.rectangle","Small");
	public static final ThumbnailSizeType MEDIUM 	= new ThumbnailSizeType("M","cms.code.thumbnail.size.square","Medium");
	public static final ThumbnailSizeType LARGE 	= new ThumbnailSizeType("L","cms.code.thumbnail.size.wide","Large");
	public static final ThumbnailSizeType ORGINAL 	= new ThumbnailSizeType("O","cms.code.thumbnail.size.wide","Original");
	
	protected String code;
	protected String key;
	protected String name;
	
	public ThumbnailSizeType(final String code, final String key, final String defaultName){
		this.key = key;
		
		if(CmsRequestContext.getCmsRequestContext().getMessageSourceAccessor() != null){
			this.name = CmsRequestContext.getCmsRequestContext().getMessageSourceAccessor().getMessage(key,defaultName);
		}else{
			this.name = defaultName;
		}
		
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
