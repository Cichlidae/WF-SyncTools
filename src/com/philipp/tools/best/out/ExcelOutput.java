package com.philipp.tools.best.out;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

import com.jacob.com.Variant;
import com.jacob.impl.ado.Field;
import com.jacob.impl.ado.Fields;
import com.jacob.impl.ado.Recordset;
import com.philipp.tools.common.GuidGenerator;
import com.philipp.tools.common.Statics;
import com.philipp.tools.common.log.Logger;
import com.premaservices.tools.collection.FilterPredicate;
import com.premaservices.tools.collection.MatcherPredicate;

public class ExcelOutput implements Output {

	private File file;
	private Workbook wb;
	private int counter = 0;		
	
	public static final String HANDLER_GUID = "GUID";
	public static final String HANDLER_DATE = "DATE";
	public static final String HANDLER_TIME = "TIME";
	public static final String HANDLER_FLUSH = "FLUSH";
	
	private final MatcherPredicate timeMatcher = new MatcherPredicate(HANDLER_TIME + ":.*");	

	public ExcelOutput (File file) throws IOException {		
		this(file, false);				
	}

	public ExcelOutput (File file, boolean rewrite) throws IOException {

		this.file = file;

		if (!rewrite) {						
			read(file);			
		}
		else {							
			if (file.exists() && !file.delete()) {
				Logger.err("Cannot delete " + file + ". Check if it's busy and unlock.");
				throw new IOException("Cannot delete " + file + ". Check if it's busy and unlock.");
			}																								
			wb = new XSSFWorkbook();
		}					
		
	}
	
	protected void read (File file) throws IOException {
		
		InputStream in = new FileInputStream(file);
		wb = new XSSFWorkbook(in);
		in.close();			
	}
	
	public void appendSheet (String name, Recordset rs, List<String> handlers) throws IOException {
		
		String sheetName = WorkbookUtil.createSafeSheetName(name);
		
		if (wb.getSheet(sheetName) != null) {
			sheetName += counter;
			counter++;
		}
		
		Sheet sheet = wb.createSheet(sheetName);
						
		int rowCounter = 0;
		int columnHandlerCounter = 0;
		Fields fs = rs.getFields();
		Row header = sheet.createRow(rowCounter++);
		
		XSSFFont headerFont = (XSSFFont)wb.createFont();
		headerFont.setColor(IndexedColors.WHITE.index);
		headerFont.setBold(true);
		
		XSSFFont font = (XSSFFont)wb.createFont();
		font.setColor(IndexedColors.BLACK.index);
		
		XSSFColor borderColor = new XSSFColor(Color.decode("#95B3D7"));
						
		XSSFCellStyle headerStyle = (XSSFCellStyle)sheet.getWorkbook().createCellStyle();	
		XSSFColor headerColor = new XSSFColor(Color.decode("#4F81BD"));			
		headerStyle.setFillForegroundColor(headerColor);			
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);						
		headerStyle.setFont(headerFont);
		
		XSSFCellStyle oddStyle = (XSSFCellStyle)sheet.getWorkbook().createCellStyle();	
		XSSFColor oddColor = new XSSFColor(Color.decode("#DCE6F1"));		
		oddStyle.setFillForegroundColor(oddColor);	
		oddStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);	
		oddStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		oddStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		oddStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		oddStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		oddStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, borderColor); 
		oddStyle.setBorderColor(XSSFCellBorder.BorderSide.TOP, borderColor);
		oddStyle.setBorderColor(XSSFCellBorder.BorderSide.LEFT, borderColor);
		oddStyle.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, borderColor);
		oddStyle.setFont(font);
		
		XSSFCellStyle evenStyle = (XSSFCellStyle)sheet.getWorkbook().createCellStyle();	
		evenStyle.setFillForegroundColor(IndexedColors.WHITE.index);			
		evenStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);	
		evenStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		evenStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		evenStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		evenStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		evenStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, borderColor);
		evenStyle.setBorderColor(XSSFCellBorder.BorderSide.TOP, borderColor);
		evenStyle.setBorderColor(XSSFCellBorder.BorderSide.LEFT, borderColor);
		evenStyle.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, borderColor);
		evenStyle.setFont(font);
			
	    for (int i = 0; i < fs.getCount(); i++) {
	    	Cell cell = header.createCell(i);
	    	cell.setCellValue(fs.getItem(i).getName());
	    	cell.setCellStyle(headerStyle);
	    }	
	    
	    boolean handling = handlers != null && !handlers.isEmpty() ? true : false;	
	    Collection<String> dateHandlers = null;	 
	    
	    if (handling) {
	    	if (handlers.contains(HANDLER_GUID)) {
	    		Cell cell = header.createCell(fs.getCount());
	    		cell.setCellValue(HANDLER_GUID);
	    		cell.setCellStyle(headerStyle);	
	    		columnHandlerCounter++;
	    	}		    	
	    	dateHandlers = CollectionUtils.select(handlers, new FilterPredicate(HANDLER_DATE + " " + HANDLER_TIME + ":.*"));
	    }
	    	
	    if (!rs.getEOF()) rs.MoveFirst();	    
	    while (!rs.getEOF()) {
	    	Row row = sheet.createRow(rowCounter++);
	    	
			for (int i = 0; i < fs.getCount(); i++) {	  
				Cell cell = row.createCell(i);  

				if (rowCounter % 2 != 0) {				
					cell.setCellStyle(evenStyle);
				}
				else {
					cell.setCellStyle(oddStyle);
				}
				
				String caption = sheet.getRow(0).getCell(i).getStringCellValue().trim().toUpperCase();				

			    Field f = fs.getItem(i);
			    Variant v = f.getValue();
			    			   	
			    switch (v.getvt()) {			  
			    	case Variant.VariantDate: {
			    		Date date = v.getJavaDate();
			    		boolean asDefault = true;
			    		
			    		if (CollectionUtils.isNotEmpty(dateHandlers)) {			    			
			    			for (String h : dateHandlers) {
			    				
			    				if (h.compareTo(HANDLER_DATE) == 0) {
			    					cell.setCellValue(date);
					    			CellStyle style = wb.createCellStyle();
				    				style.cloneStyleFrom(cell.getCellStyle());				    						    						    	
					    			XSSFDataFormat df = (XSSFDataFormat)wb.createDataFormat();			    		
					    			style.setDataFormat(df.getFormat("dd.MM.yyyy"));
					    			cell.setCellStyle(style);	
					    			asDefault = false;
			    				}
			    				else {
			    					String target = CollectionUtils.find(dateHandlers, timeMatcher);
			    					if (target != null) {
					    				List<String> pfi = this.getHandlerProperties(target);			    							    							    				
					    				if (pfi.contains(caption) || target.compareTo(HANDLER_TIME) == 0) {			    								    				
						    				cell.setCellValue(date);
						    				CellStyle style = wb.createCellStyle();
						    				style.cloneStyleFrom(cell.getCellStyle());				    								    								    							    								    				
						    				XSSFDataFormat df = (XSSFDataFormat)wb.createDataFormat();
						    				style.setDataFormat(df.getFormat("h:mm:ss"));
						    				cell.setCellStyle(style);	
						    				asDefault = false;
					    				}
					    			}			    								    					
			    				}			    							    				
			    			}			    						    			
			    		}			    					    					    		
			    					    	
			    		if (asDefault)
			    			cell.setCellValue(date != null ? Statics.DATE_FORMATTER.format(date) : "");			    		
			    		break;
			    	}	
			    	case Variant.VariantShort:
			    		cell.setCellValue(v.getShort());			    		
			    		break;
			    	case Variant.VariantInt:					    	
			    		cell.setCellValue(v.getInt());
			    		break;	
			    	case Variant.VariantDecimal:	
			    		BigDecimal val = v.getDecimal();			    		
			    		cell.setCellValue(val.doubleValue());			    					    		
			    		break;
			    	case Variant.VariantFloat:
			    		cell.setCellValue(v.getFloat());
			    		break;
			    	case Variant.VariantDouble:
			    		cell.setCellValue(v.getDouble());
			    		break;
			    	case Variant.VariantNull:			    	
			    		break;
			    	default:				    		
			    		cell.setCellValue(v + "");
			    }			    			    	        	        	        	        	     		
			} 

			if (handling) {
				if (handlers.contains(HANDLER_GUID)) {
					Cell cell = row.createCell(fs.getCount()); 
					cell.setCellValue(GuidGenerator.getCustomGuid(22, true));	
					
					if (rowCounter % 2 != 0) {				
						cell.setCellStyle(evenStyle);
					}
					else {
						cell.setCellStyle(oddStyle);
					}					
				}													
			}												
			rs.MoveNext();
	    }	    	   
	    
	    for (int i = 0; i < fs.getCount() + columnHandlerCounter; i++) {
	    	sheet.autoSizeColumn(i);
	    }	    	    
	    	 
	    if (fs.getCount() > 0) {
		    XSSFCell lastCell = (XSSFCell)header.getCell(header.getLastCellNum() - 1);
		    String ref = lastCell.getReference();
		    ref = ref.substring(0, ref.length() - 1);

		    sheet.setAutoFilter(CellRangeAddress.valueOf("A" + String.valueOf(1) + ":" + ref + String.valueOf(rowCounter)));
	    }
	    
	    if (handling) {
	    	if (handlers.contains(HANDLER_FLUSH)) {	    	
	    		flushAll();
	    		read(file);
	    		Logger.debug("Flush out done.");
	    	}
	    }		   
	}
	
	@Override
	public void flushAll () throws IOException {		
		flushAll(file.getAbsoluteFile());		
	}

	@Override
	public void printRS(String id, Recordset rs) throws IOException {			
		appendSheet(id, rs, null);		
	}

	@Override
	public void flushAll(File file) throws IOException {
		
		FileOutputStream fileOut = new FileOutputStream(file);
	    wb.write(fileOut);
	    fileOut.flush();
	    fileOut.close();	
	    Logger.debug(file.getAbsolutePath());
	    
	}

	@Override
	public void printRS(String id, ResultSet rs) throws SQLException {	
		throw new UnsupportedOperationException("ExcelOutput.printRS<String, ResultSet>");
	}

	@Override
	public void printRS(String id, List<String> rs) {
		// TODO Auto-generated method stub	
		throw new UnsupportedOperationException("ExcelOutput.printRS<String, List<String>>");
	}

	@Override
	public void printRS(String id, Map<String, ?> rs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ExcelOutput.printRS<String, Map<String, ?>>");
	}

	@Override
	public void printRS(String id, Recordset rs, List<String> handlers) throws IOException {
		appendSheet(id, rs, handlers);				
	}
	
	private List<String> getHandlerProperties (String handler) {
		
		List<String> list = new ArrayList<String>();
		
		String str = StringUtils.substringAfter(handler, ":");
		if (str != null) {
			String[] props = StringUtils.split(str, ',');
			list = Arrays.asList(props);
		}
		return list;
	}

}
