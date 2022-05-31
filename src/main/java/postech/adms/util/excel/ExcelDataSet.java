package postech.adms.util.excel;

import java.util.ArrayList;
import java.util.List;

public class ExcelDataSet extends ArrayList<ExcelData>{
    /**
     * 
     */
    private static final long serialVersionUID = -7772150118684950381L;

    private List<String> headers = LazyUtil.lazyStringList();
    
    public ExcelData getRow(int idx ) {
        return super.get(idx);
    }

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
    
    
}
