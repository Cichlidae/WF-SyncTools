package com.philipp.tools.best.in;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.philipp.tools.best.db.QueryBridge;

public class ExcelInput extends Input<String> {

	private Workbook wb;

	public ExcelInput (File file) throws IOException {		
				
		InputStream in = new FileInputStream(file);
		wb = new XSSFWorkbook(in);
		in.close();	
				
	}	
		
	public void convert (String sheetName, StdinCommand c) throws Exception {
		
		XSSFSheet sheet = (XSSFSheet)wb.getSheet(sheetName);									
		if (sheet != null) {
			Row header = sheet.getRow(0);		
			List<String> fields = new ArrayList<String>();
			List<QueryBridge.Type> types = new ArrayList<QueryBridge.Type>();
								
			Iterator<Cell> headerCells = header.cellIterator();
			while (headerCells.hasNext()) {
				Cell cell = headerCells.next();														
				fields.add(cell.getStringCellValue());
				types.add(QueryBridge.Type.NONE);
			}		
			
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				
				for (int j = 0; j < fields.size(); j++) {
					Cell cell = row.getCell(j);				
					QueryBridge.Type type = types.get(j);										
					
					switch (cell.getCellType()) {	
						case XSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								if (type == QueryBridge.Type.NONE)	
									types.set(j, QueryBridge.Type.DATE);	
							}
							else {
								if (type == QueryBridge.Type.NONE)
									types.set(j, QueryBridge.Type.DOUBLE);		
							}
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							if (type == QueryBridge.Type.NONE)	
								types.set(j, QueryBridge.Type.BOOLEAN);	
							break;
						case XSSFCell.CELL_TYPE_STRING:
							if (type == QueryBridge.Type.NONE)	
								types.set(j, QueryBridge.Type.STRING);
							break;
						case XSSFCell.CELL_TYPE_BLANK:
						default:	
					}										
				}								
			}
																		
			QueryBridge bridge = new QueryBridge(sheetName, fields, types, c, true);		
			
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				List<Object> values = new ArrayList<Object>();
				Row row = sheet.getRow(i);
																
				for (int j = 0; j < fields.size(); j++) {									
					Cell cell = row.getCell(j);
					Object value = "";								
					
					switch (cell.getCellType()) {		
						case XSSFCell.CELL_TYPE_NUMERIC:
							value = cell.getNumericCellValue();													
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							value = cell.getBooleanCellValue();													
							break;
						case XSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();															
							break;
						case XSSFCell.CELL_TYPE_BLANK:
						default: 							
							value = bridge.new Blank();
					}			
					values.add(value);															
				}		
			
				List<String> queries = bridge.composeQuery(values);					
				for (String query : queries) {					
					super.notifyListeners(new String[] {query, sheetName});
				}			
			}											
		}		
	}
		
}
