package com.philipp.tools.best.out;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.jacob.com.Variant;
import com.jacob.impl.ado.Field;
import com.jacob.impl.ado.Fields;
import com.jacob.impl.ado.Recordset;
import com.philipp.tools.best.log.Logger;

public class LoggerOutput implements Output {

	@Override
	public void printRS(String id, Recordset rs) {	
		
		Fields fs = rs.getFields();

	    String fields = "";
	    for (int i = 0; i < fs.getCount(); i++) {
	      fields += fs.getItem(i).getName() + "\t";
	    }
	    fields = fields.substring(0, fields.lastIndexOf('\t'));	    	    
	    Logger.log(fields);
    
	    if (!rs.getEOF()) rs.MoveFirst();	    
	    while (!rs.getEOF()) {
	    	
	      String stroke = "";	
	      for (int i = 0; i < fs.getCount(); i++) {
	    	  
	        Field f = fs.getItem(i);
	        Variant v = f.getValue();
	       
	        if (v.getvt() == Variant.VariantDate) {
	        	Date date = v.getJavaDate();	        	
	        	stroke += date != null ? Output.DATE_FORMATTER.format(date) + "\t" : "\t";	        	
	        }
	        else {
	        	stroke += v + "\t";
	        }	
	      } 	
	      Logger.log(stroke);	      
	      rs.MoveNext();
	    }	    
		
	}

	@Override
	public void flushAll() throws IOException {
		// TODO Auto-generated method stub		
	}

	@Override
	public void flushAll(File file) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

}
