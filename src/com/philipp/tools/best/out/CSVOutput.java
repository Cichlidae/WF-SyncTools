package com.philipp.tools.best.out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import com.jacob.com.Variant;
import com.jacob.impl.ado.Field;
import com.jacob.impl.ado.Fields;
import com.jacob.impl.ado.Recordset;
import com.philipp.tools.best.args.FormatArgs;
import com.philipp.tools.common.Statics;
import com.philipp.tools.common.log.Logger;

public class CSVOutput extends AbstractOutput {
	
	private CSVFormat format;
	private CSVPrinter printer;

	public CSVOutput (File file) throws IOException {
		this(file, false, new FormatArgs());					
	}
	
	public CSVOutput (File file, boolean rewrite, FormatArgs outArgs) throws IOException {
		
		super(outArgs);
				
		if (rewrite) {
			if (file.exists() && !file.delete()) {
				Logger.err("Cannot delete " + file + ". Check if it's busy and unlock.");
				throw new IOException("Cannot delete " + file + ". Check if it's busy and unlock.");
			}	
		}
		
		switch (outArgs.csvFormat) {
			case EXCEL:
				format = CSVFormat.EXCEL.withIgnoreSurroundingSpaces().withQuoteMode(QuoteMode.MINIMAL);
				if (outArgs.delimiter != null)
					format = format.withDelimiter(outArgs.delimiter);
				break;
			case TDF:
				format = CSVFormat.TDF.withQuoteMode(QuoteMode.MINIMAL);
				break;
			case MYSQL:
				format = CSVFormat.MYSQL;
				break;
			default: throw new IOException("Incorrect csv format");	
		}
						
		if (outArgs.quotesOn && outArgs.csvFormat != Statics.CSVFormat.MYSQL) 
			format = format.withQuoteMode(QuoteMode.NON_NUMERIC);
									
		if (outArgs.enc != null)
			this.printer = new CSVPrinter(new OutputStreamWriter(new FileOutputStream(file, true), outArgs.enc), format);
		else
			this.printer = new CSVPrinter(new OutputStreamWriter(new FileOutputStream(file, true)), format);
		
	}

	@Override
	public void printRS(String id, Recordset rs) throws IOException {
		printRS(id, rs, null);
	}

	@Override
	public void printRS(String id, Recordset rs, List<String> handlers) throws IOException {
	
		Fields fs = rs.getFields();
		
		switch (outArgs.header) {
			case OFF:
				break;
			case FIRST:
				outArgs.header = Statics.HeaderFlag.OFF;
			default:	
			    for (int i = 0; i < fs.getCount(); i++) {
			    	printer.print(fs.getItem(i).getName());
			    }
			    printer.println();
		}
		
		if (!rs.getEOF()) rs.MoveFirst();
		while (!rs.getEOF()) {
			
			for (int i = 0; i < fs.getCount(); i++) {
				
				Field f = fs.getItem(i);
			    Variant v = f.getValue();
			    
			    if (v.getvt() == Variant.VariantDate) {
		        	Date date = v.getJavaDate();	
		        	switch (outArgs.dateFormat) {	        		
		        		case ODBC:
		        			printer.print(date != null ? Statics.ODBC_DATE_FORMATTER.formatAsODBC(date) + "" : "");
		        			break;
		        		default:
		        			printer.print(date != null ? Statics.DATE_FORMATTER.format(date) + "" : "");	      
		        	}
		        }
		        else if (v.getvt() == Variant.VariantDecimal) {	        		        	
		        	printer.print(v.getDecimal().toPlainString());
		        }	        
		        else if (v.getvt() == Variant.VariantString) {
		        		printer.print(v.toString().trim());
		        }
		        else printer.print(v);				    				
			}						
			printer.println();
			rs.MoveNext();
		}

	}

	@Override
	public void printRS(String id, ResultSet rs) throws SQLException, IOException {

		ResultSetMetaData md = rs.getMetaData();
		
		switch (outArgs.header) {
			case OFF:
				break;
			case FIRST:
				outArgs.header = Statics.HeaderFlag.OFF;
			default:	
			    for (int i = 0; i < md.getColumnCount(); i++) {
			    	printer.print(md.getColumnName(i));
			    }
			    printer.println();
		}
				
		printer.printRecords(rs);			
	}

	@Override
	public void printRS(String id, List<String> rs) throws IOException {

		printer.print(id);
		printer.println();
		
		for (String row : rs) {					
			printer.print(row);
			printer.println();
		}			
	}

	@Override
	public void printRS(String id, Map<String, ?> rs) throws IOException {
		
		printer.print(id);
		printer.println();
		
		for (String key : rs.keySet()) {
			printer.print(key);
			printer.print(rs.get(key));
			printer.println();	
		}			
	}

	@Override
	public void printRS(String id, String s) throws IOException {
		printer.print(id);
		printer.print(s);
		printer.println();
	}

	@Override
	public void flushAll() throws IOException {
		printer.flush();
		printer.close();
	}

	@Override
	public void flushAll(File file) throws IOException {
		flushAll();
	}

}
