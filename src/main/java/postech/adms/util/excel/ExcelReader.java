package postech.adms.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    private InputStream in;
    private ExcelExtention type;
    private int headerRows;
    private List<AbstractExcelReader> excels;

    public ExcelReader() {
    }

    public ExcelReader(InputStream in) {
        this(in, ExcelExtention.XLS);
    }

    public ExcelReader(InputStream in, ExcelExtention type) {
        this(in, type, 1);
    }

    /**
     * @param in
     * @param type
     * @param headerRows
     */
    public ExcelReader(InputStream in, ExcelExtention type, int headerRows) {
        super();
        this.in = in;
        this.type = type;
        this.headerRows = headerRows;
        excels = new ArrayList<AbstractExcelReader>();
        excels.add(new Excel2003Reader());
        excels.add(new Excel2007Reader());
    }

    public ExcelDataSet getReadExcel() throws IOException {

        ExcelDataSet dataset = null;
        boolean canExcute = false;
        AbstractExcelReader reader = null;

        if (type != null) {

            switch (type) {
                case XLS:
                    reader = new Excel2003Reader();
                    dataset = reader.reader(in, headerRows);
                    canExcute = true;
                    break;
                case XLSX:
                    reader = new Excel2007Reader();
                    dataset = reader.reader(in, headerRows);
                    canExcute = true;
                    break;
            }

        } else {

            for (AbstractExcelReader r : excels) {
                if (r.canReadExcel(in)) {
                    dataset = r.reader(in, headerRows);
                    canExcute = true;
                    break;
                }
            }
            ;
        }
        if (!canExcute) {
            throw new IOException("파일을 읽는중 애러가 발생하였습니다. 파일 확장자명을 체크하세요");
        }
        return dataset;

    }


}
