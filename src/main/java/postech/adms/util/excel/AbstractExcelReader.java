package postech.adms.util.excel;

import java.io.IOException;
import java.io.InputStream;


public abstract class AbstractExcelReader {
	abstract public ExcelDataSet reader(InputStream in, int headerRows) throws IOException;
	abstract public boolean canReadExcel(InputStream in);

}
