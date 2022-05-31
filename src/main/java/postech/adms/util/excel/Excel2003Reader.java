package postech.adms.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Excel2003Reader extends AbstractExcelReader{
	
	private POIFSFileSystem fs;
	/**
	 * @param in 인풋스트림
	 * @param headerRows 해더의 Row 수 
	 * @return
	 * @throws IOException
	 */
	@Override
	public ExcelDataSet reader(InputStream in, int headerRows) throws IOException, OfficeXmlFileException{
		
		ExcelDataSet dataSet = new ExcelDataSet();
		if(fs == null){
			fs = new POIFSFileSystem( in );
		}
		HSSFWorkbook work = new HSSFWorkbook(fs);
		
		//TODO 우선 첫번째 시트에서 읽어드린다. 추후 파라미터를 받아 몇번째 시트에서 읽을것인가에 대한 
		//확장이 가능하다.
		HSSFSheet sheet = work.getSheetAt(0);
		
		Iterator<Row> rows = sheet.rowIterator(); 
		List<String> headers = new ArrayList<String>();
		
		int i = 0;
		int j = 0;
		
		while( rows.hasNext() ) {
			
			HSSFRow row = (HSSFRow) rows.next();
	        if(i < headerRows){
	        	headers = setTitle(row);
	        	dataSet.setHeaders(headers);
	        	i++;
	        	continue;
	        }	
		    Iterator<Cell> cells = row.cellIterator();
		    ExcelData rowData = new ExcelData();
		    
		    while( cells.hasNext() ) {
		        HSSFCell cell = (HSSFCell) cells.next();
		        rowData = getCellValue(headers.get(j), cell, rowData);
		        j++;
		    }
	        dataSet.add(rowData);
		    j = 0;
		}
		return dataSet;
	}
	
	private ExcelData getCellValue(String cloumn, HSSFCell cell, ExcelData rowData) {
		
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				rowData.put(cloumn, cell.getRichStringCellValue().getString());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					rowData.put(cloumn,cell.getDateCellValue());
				}
				else {
					Double doulbe = cell.getNumericCellValue();
					rowData.put(cloumn, doulbe.intValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				rowData.put(cloumn, cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				rowData.put(cloumn, cell.getCellFormula());
				break;
			default:
//				System.out.println(cloumn + "---> cell의 값이 없습니다.");
				rowData.put(cloumn, null);
		}
		return rowData;
	}

	private List<String> setTitle(HSSFRow row) {
	    Iterator<Cell> cells = row.cellIterator();
	    List<String> list = new ArrayList<String>();
	    while( cells.hasNext() ) {
	    	HSSFCell cell = (HSSFCell) cells.next();
	    	list.add(cell.getRichStringCellValue().getString());
	    }
		return list;
	}
	public static void main(String[] args) {
		try {
			// ExcelReader
			Excel2003Reader reader = new Excel2003Reader();
			
			String excel = "D:/tmp1/test1.xls";
			// check file
			File file = new File(excel);
			if (!file.exists() || !file.isFile() || !file.canRead()) {
				throw new IOException(excel);
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
			POIFSFileSystem fileSystem = new POIFSFileSystem( in );
			this.fs = fileSystem;
		} catch (OfficeXmlFileException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
