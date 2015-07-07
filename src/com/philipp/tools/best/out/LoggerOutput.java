package com.philipp.tools.best.out;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jacob.com.Variant;
import com.jacob.impl.ado.Field;
import com.jacob.impl.ado.Fields;
import com.jacob.impl.ado.Recordset;
import com.philipp.tools.best.args.FormatArgs;
import com.philipp.tools.common.Statics;
import com.philipp.tools.common.log.Logger;

public class LoggerOutput extends AbstractOutput {
	
	public LoggerOutput() {
		super();
	}

	public LoggerOutput(FormatArgs outArgs) {
		super(outArgs);
	}

	@Override
	public void printRS(String id, Recordset rs) {	
		
		Fields fs = rs.getFields();
		
		switch (outArgs.header) {
			case OFF:
				break;
			case FIRST:
				outArgs.header = Statics.HeaderFlag.OFF;
			default:	
			    String fields = "";
			    for (int i = 0; i < fs.getCount(); i++) {
			      fields += fs.getItem(i).getName() + "\t";
			    }
			    fields = fields.substring(0, fields.lastIndexOf('\t'));	    	    
			    Logger.log(fields);
		}
    
	    if (!rs.getEOF()) rs.MoveFirst();	    
	    while (!rs.getEOF()) {
	    	
	      String stroke = "";	
	      for (int i = 0; i < fs.getCount(); i++) {
	    	  
	        Field f = fs.getItem(i);
	        Variant v = f.getValue();
	       
	        if (v.getvt() == Variant.VariantDate) {
	        	Date date = v.getJavaDate();	
	        	switch (outArgs.dateFormat) {	        		
	        		case ODBC:
	        			stroke += date != null ? Statics.ODBC_DATE_FORMATTER.formatAsODBC(date) + "\t" : "\t";
	        			break;
	        		default:
	        			stroke += date != null ? Statics.DATE_FORMATTER.format(date) + "\t" : "\t";	      
	        	}
	        }
	        else if (v.getvt() == Variant.VariantDecimal) {	        		        	
	        	stroke += v.getDecimal().toPlainString() + "\t";	        	
	        }	        
	        else {	        		        		        	        	
	        	if (v.getvt() == Variant.VariantString && outArgs.quotesOn) {
	        		stroke += String.valueOf('\u0022') + v + String.valueOf('\u0022') + "\t";	        		
	        	}	
	        	else stroke += v + "\t";
	        }	
	      } 	
	      stroke = stroke.substring(0, stroke.lastIndexOf('\t'));	  
	      Logger.log(stroke);	      
	      rs.MoveNext();
	    }	    
	    
	}

	@Override
	public void flushAll() throws IOException {		
	}

	@Override
	public void flushAll(File file) throws IOException {	
	}

	@Override
	public void printRS(String id, ResultSet rs) throws SQLException {		
		
		String fields = "";
		ResultSetMetaData md = rs.getMetaData();
		
		switch (outArgs.header) {
			case OFF:
				break;
			case FIRST:
				outArgs.header = Statics.HeaderFlag.OFF;
			default:
				for (int i = 1; i <= md.getColumnCount(); i++) {
					fields += md.getColumnName(i) + "\t";
				}
				fields = fields.substring(0, fields.lastIndexOf('\t'));	    	    
			    Logger.log(fields);
		}
		
		while (rs.next()) {
			String stroke = "";	
			
			for (int i = 1; i <= md.getColumnCount(); i++) {				
				int type = md.getColumnType(i);
				
				switch (type) {
					case Types.DATE: {
						Date date = rs.getDate(i);
						stroke += date != null ? Statics.DATE_FORMATTER.format(date) + "\t" : "\t";
						break;
					}
					case Types.VARCHAR:
						if (outArgs.quotesOn) {
							try {
								stroke += String.valueOf('\u0022') + rs.getString(i) + String.valueOf('\u0022') + "\t";
							}
							catch (Exception e) {};		
							break;
						}						
					default:					
						try {
							stroke += rs.getString(i) + "\t";
						}
						catch (Exception e) {};
				}
			}	
			stroke = stroke.substring(0, stroke.lastIndexOf('\t'));	
			Logger.log(stroke);	 			
        }		
	}

	@Override
	public void printRS(String id, List<String> rs) {
		
		Logger.log(id);
		for (String row : rs) {					
			Logger.log(!outArgs.quotesOn ? row : "'" + row + "'");
		}			
	}

	@Override
	public void printRS(String id, Map<String, ?> rs) {
		
		Logger.log(id);
		for (String key : rs.keySet()) {
			Logger.log(key + "\t" + rs.get(key));
		}				
	}

	@Override
	public void printRS(String id, Recordset rs, List<String> handlers) {
		printRS(id, rs);
	}
	
	@Override
	public void printRS (String id, String s) {
		Logger.log(id);
		Logger.log(s);
	}

}
