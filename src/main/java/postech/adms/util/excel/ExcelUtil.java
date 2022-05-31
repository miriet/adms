package postech.adms.util.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelUtil {

	private XSSFWorkbook workbook;
	private SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(1000);
	
	public ExcelUtil(XSSFWorkbook workbook){
		this.workbook = workbook;
	}
	
	public void createHeader(XSSFCell cell,Object value,int sheetIndex){
		XSSFCellStyle style = workbook.createCellStyle();
		
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		//workbook.getSheetAt(sheetIndex).autoSizeColumn(cell.getColumnIndex());
		
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		
		if(value instanceof Double){
			cell.setCellValue((Double)value);
		}else if(value instanceof String){
			cell.setCellValue((String)value);
		}else if(value instanceof Integer){
			cell.setCellValue((Integer)value);
		}else if(value instanceof Long){
			cell.setCellValue((Long)value);
		}
	}
	
	public void createDefaultCell(XSSFRow row,int index,Object value,short align){
		XSSFCell cell = row.createCell(index);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		createCell(cell,value,null,align,this.getThinBorderCellStyle(align),0);
	}
	
	public void createDefaultCell(XSSFCell cell,Object value,short align){
		createCell(cell,value,null,align,this.getThinBorderCellStyle(align),0);
	}
	/**
	 * Style에 맞게 데이터 세팅
	 * @param dataCellStyle
	 * @param cell
	 * @param value
	 */
	public void createStyleCell(XSSFCellStyle dataCellStyle,XSSFCell cell, Object value) {
		cell.setCellStyle(dataCellStyle);
		setValue(cell,value);
		
	}
	/**
	 * Value만 세팅
	 * @param cell
	 * @param value
	 */
	public void setValue(XSSFCell cell, Object value) {
		if(value instanceof Double){
			cell.setCellValue((Double)value);
		}else if(value instanceof String){
			cell.setCellValue((String)value);
		}else if(value instanceof Integer){
			cell.setCellValue((Integer)value);
		}else if(value instanceof Long){
			cell.setCellValue((Long)value);
		}
	}
	public void createDefaultCellNoneBorder(XSSFCell cell,Object value,short align){
		createCell(cell,value,null,align,getCellStyle(align,BorderStyle.NONE),0);
	}
	
	public void createDefaultCell(XSSFCell cell,Object value,Integer width,short align){
		createCell(cell,value,width,align,this.getThinBorderCellStyle(align),0);
	}
	
	public void createDefaultCell(XSSFCell cell,Object value,Integer width,short align,int sheetIndex){
		createCell(cell,value,width,align,this.getThinBorderCellStyle(align),sheetIndex);
	}
	
	public void createCell(XSSFCell cell,Object value,Integer width,short align,XSSFCellStyle style,int sheetIndex){
		cell.setCellStyle(style);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		
		if(width != null){
			workbook.getSheetAt(sheetIndex).setColumnWidth(cell.getColumnIndex(), width);
		}else{
			//workbook.getSheetAt(sheetIndex).autoSizeColumn(cell.getColumnIndex());
		}
		
		if(value instanceof Double){
			cell.setCellValue((Double)value);
		}else if(value instanceof String){
			cell.setCellValue((String)value);
		}else if(value instanceof Integer){
			cell.setCellValue((Integer)value);
		}else if(value instanceof Long){
			cell.setCellValue((Long)value);
		}
	}
	
	
	public XSSFCellStyle getThinBorderCellStyle(Short align){
		return getCellStyle(align,BorderStyle.THIN);
	}
	
	public XSSFCellStyle getCellStyle(short align,BorderStyle borderStyle){
		XSSFCellStyle result = workbook.createCellStyle();
		
		result.setAlignment(align);
		
		if(borderStyle != null){
			result.setBorderTop(borderStyle);
			result.setBorderLeft(borderStyle);
			result.setBorderRight(borderStyle);
			result.setBorderBottom(borderStyle);
		}
		
		return result;
	}
	
	public static Workbook getWorkbook(String filePath) {

        /*
         * FileInputStream은 파일의 경로에 있는 파일을
         * 읽어서 Byte로 가져온다.
         *
         * 파일이 존재하지 않는다면은
         * RuntimeException이 발생된다.
         */
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        Workbook workbook = null;

        /*
         * 파일의 확장자를 체크해서 .XLS 라면 HSSFWorkbook에
         * .XLSX라면 XSSFWorkbook에 각각 초기화 한다.
         */
        if(filePath.toUpperCase().endsWith(".XLS")) {
            try {
            	workbook = new HSSFWorkbook(fis);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        else if(filePath.toUpperCase().endsWith(".XLSX")) {
            try {
            	workbook = new XSSFWorkbook(fis);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        return workbook;

    }
}
