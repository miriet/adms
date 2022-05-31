package postech.adms.common.code;

import postech.adms.common.context.CmsRequestContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApiType implements CommonCode,Serializable{
	private static final long serialVersionUID = 1L;
	
	protected static final Map<String, CommonCode> TYPES = new LinkedHashMap<String, CommonCode>();
	
	public static final ApiType ContentPrivderOne = new ApiType("ContentPrivderOne","cms.code.api.type.contentprivderone","ContentPrivder One");
	public static final ApiType ContentPrivderList = new ApiType("ContentPrivderList","cms.code.api.type.contentprivderlist","ContentPrivder List");
	
	public static final ApiType BookOne = new ApiType("BookOne","cms.code.api.type.bookone","BookOne");
	public static final ApiType BookList = new ApiType("BookList","cms.code.api.type.booklist","BookList");
	
	public static final ApiType WriterOne = new ApiType("WriterOne","cms.code.api.type.writerone","WriterOne");
	public static final ApiType WriterList = new ApiType("WriterList","cms.code.api.type.writerlist","WriterList");
	
	public static final ApiType ContractOne = new ApiType("ContractOne","cms.code.api.type.writerone","ContractOne");
	public static final ApiType ContractList = new ApiType("ContractList","cms.code.api.type.writerlist","ContractList");
	
	public static final ApiType SeasonOne = new ApiType("SeasonOne","cms.code.api.type.writerone","SeasonOne");
	public static final ApiType SeasonList = new ApiType("SeasonList","cms.code.api.type.writerlist","SeasonList");
	
	public static final ApiType EpisodeOne = new ApiType("EpisodeOne","cms.code.api.type.writerone","EpisodeOne");
	public static final ApiType EpisodeList = new ApiType("EpisodeList","cms.code.api.type.writerlist","EpisodeList");
	
	
	protected String code;
	protected String key;
	protected String name;
	
	public ApiType(final String code, final String key, final String defaultName){
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
