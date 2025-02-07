package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelUtility {

	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook wb;
	public XSSFSheet ws;
	public XSSFRow row;
	public XSSFCell cell;
	String path;
	
	public ExcelUtility(String path) {
		// TODO Auto-generated constructor stub
		this.path = path;
	}
	
	public int getRowCount(String SheetName) throws IOException {
		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(SheetName);
		int rowCount = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowCount;
	}
	
	public int getCellCount(String SheetName,int rownum) throws IOException {
		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(SheetName);
		row = ws.getRow(rownum);
		int cellCount = row.getLastCellNum();
		wb.close();
		fi.close();
		return cellCount;
	}
	
	public String getCellData(String SheetName,int rownum,int colnum) throws IOException {
		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(SheetName);
		row = ws.getRow(rownum);
		cell = row.getCell(colnum);
		
		DataFormatter formatter = new DataFormatter();
		String data;
		 	try {
		 		data = formatter.formatCellValue(cell);
		 	}catch (Exception e) {
				// TODO: handle exception
		 		data = "";
			}
		
		wb.close();
		fi.close();
		return data;
	}
	
	public void setCellData(String SheetName,int rownum,int colnum,String data) throws IOException {
		//fi = new FileInputStream(path);
		//wb = new XSSFWorkbook(fi);
//		ws = wb.getSheet(SheetName);
//		row = ws.getRow(rownum);
//		cell = row.getCell(colnum);
		
		File xlfile=new File(path);
		if(!xlfile.exists()) {
			wb = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			wb.write(fo);
		}
		
		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		
		if(wb.getSheetIndex(SheetName)==-1) {
			wb.createSheet();
			ws = wb.getSheet(SheetName);
		}
		if(ws.getRow(rownum)==null) {
			ws.createRow(rownum);
			row=ws.getRow(rownum);
		}
		
		cell = row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(path);
		wb.write(fo);
		
		
		wb.close();
		fi.close();
		fo.close();
		
	}
	
	
}
