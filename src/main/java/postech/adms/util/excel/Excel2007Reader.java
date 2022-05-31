package postech.adms.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Excel2007Reader extends AbstractExcelReader {

	private boolean formulasNotResults = false;
	private XSSFWorkbook wb;
	/**
	 * @param in 인풋스트림
	 * @param headerRows 해더의 Row 의 수
	 * @return
	 * @throws IOException
	 */
	@Override
	public ExcelDataSet reader(InputStream in, int headerRows) throws IOException {

		// Workbook
		if(wb == null){
			wb = new XSSFWorkbook(in);
		}
		XSSFSheet sheet = wb.getSheetAt(0);
		List<String> headers = null;
		ExcelDataSet dataSet = new ExcelDataSet();
		int i = 0;
		int j = 0;
		for (Object rawR : sheet) {
			Row row = (Row)rawR;
			ExcelData rowData = new ExcelData();
//			if(rowData.size()==0)
//				continue;
	        if(i < headerRows){
	        	headers = setTitle(row);
	        	dataSet.setHeaders(headers);
	        	i++;
	        	continue;
	        }
	        int cellIdx = 0;
			for(Iterator<Cell> ri = row.cellIterator(); ri.hasNext();) {
				Cell cell = ri.next();
				cellIdx = cell.getColumnIndex();
				rowData = getCellValue(headers.get(cellIdx), cell, rowData);
				j++;
			}
	        dataSet.add(rowData);
		    j = 0;			
		};
		return dataSet;
	}
	
	private ExcelData getCellValue(String cloumn, Cell cell, ExcelData rowData) {
		if(cell.getCellType() == Cell.CELL_TYPE_FORMULA && formulasNotResults) {
			rowData.put(cloumn, cell.getCellFormula());
		} else if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
			rowData.put(cloumn, cell.getRichStringCellValue().getString());
		} else {
			XSSFCell xc = (XSSFCell)cell;
			rowData.put(cloumn, xc.getRawValue());
		}
		return rowData;
	}
	
	private List<String> setTitle(Row row) {
	    List<String> list = new ArrayList<String>();
	    for(Iterator<Cell> ri = row.cellIterator(); ri.hasNext();) {
	    	Cell cell = ri.next();
	    	
	    	if(cell.getCellType() == Cell.CELL_TYPE_FORMULA && formulasNotResults) {
	    		list.add(cell.getCellFormula());
	    	} else if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
	    		list.add(cell.getRichStringCellValue().getString());
	    	} else {
	    		XSSFCell xc = (XSSFCell)cell;
	    		list.add(xc.getRawValue());
	    	}	    
	    }
		return list;
	}	
	public static void main(String[] args) {
		try {
			// ExcelReader
			Excel2007Reader reader = new Excel2007Reader();
			
			 String excel2007 = "D:/tmp1/sample.xlsx";
			// check file
			File file = new File(excel2007);
			if (!file.exists() || !file.isFile() || !file.canRead()) {
				throw new IOException(excel2007);
			}
			ExcelDataSet set = reader.reader(new FileInputStream(file), 1);
			List<String> list = set.getHeaders();
			
			for(ExcelData data : set){
				for(String column : list){
//					System.out.println(data.getColumn(column));
				}
			}
			
		} catch (Exception e) {
//			System.out.println("Exception occurred! : " + e);
		}
		
	}

	@Override
	public boolean canReadExcel(InputStream in) {
		try {
			XSSFWorkbook wb = new XSSFWorkbook(in);
			this.wb = wb;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}	
}
