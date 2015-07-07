package com.philipp.tools.best.in;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

import com.philipp.tools.best.args.FormatArgs;
import com.philipp.tools.best.db.QueryBridge;
import com.philipp.tools.common.Statics;
import com.philipp.tools.common.log.Logger;
import com.premaservices.util.TransliterateUtil;

public class CSVInput extends Input<String> {
	
	private CSVFormat format;
	private CSVParser parser;

	public CSVInput (File file, FormatArgs inArgs) throws IOException  {
		super(inArgs);
				
		switch (inArgs.csvFormat) {
			case EXCEL:
				format = CSVFormat.EXCEL.withIgnoreSurroundingSpaces().withQuoteMode(QuoteMode.MINIMAL);
				if (inArgs.delimiter != null)
					format = format.withDelimiter(inArgs.delimiter);
				break;
			case TDF:
				format = CSVFormat.TDF.withQuoteMode(QuoteMode.MINIMAL);
				break;
			case MYSQL:
				format = CSVFormat.MYSQL;
				break;
			default: throw new IOException("Incorrect csv format");	
		}

		this.parser = CSVParser.parse(file, Charset.forName(inArgs.enc != null ? inArgs.enc : "utf-8"), format);				
		
	}
	
	@Override
	public void convert(String id, StdinCommand c) throws Exception {
		
		String[] header = null;
		boolean hasHeader = false;
		
		switch (inArgs.header) {
			case OFF:
				break;
			case FIRST:
				inArgs.header = Statics.HeaderFlag.OFF;
			default:				  
			    header = format.getHeader();
			    hasHeader = true;
		}
		
		List<String> fields = new ArrayList<String>();
		List<QueryBridge.Type> types = new ArrayList<QueryBridge.Type>();
		
		if (header != null)  {
			for (String col : header) {
				fields.add(col);
				types.add(QueryBridge.Type.NONE);
			}				
		}				
		
		List<CSVRecord> records = parser.getRecords();		
	
		for (CSVRecord record : records) {
			
			if (header == null) {
				header = new String[record.size()];														
				for (int i = 0; i < header.length; i++) {					
					if (hasHeader) fields.add(header[i] = transliterate(record.get(i)));
					else fields.add("column_" + i);		
					types.add(QueryBridge.Type.NONE);
				}				
				if (hasHeader) continue;
			}		
																				
			if (!record.isConsistent())
				throw new IOException("Record #" + record.getRecordNumber() + "size not consistent to header size");
								
			for (int i = 0; i < header.length; i++) {
				String value = record.get(i);
				QueryBridge.Type type = types.get(i);	
				
				if (value != null && value.length() > 0) {				
					
					if (value.toLowerCase().compareTo("true") == 0 || value.toLowerCase().compareTo("false") == 0) {
						if (type == QueryBridge.Type.NONE)
							types.set(i, QueryBridge.Type.BOOLEAN);
					}
					else if (value.matches("\\d")) {
						if (type == QueryBridge.Type.NONE)
							types.set(i, QueryBridge.Type.INTEGER);
					}
					else if (value.matches("[\\d]+[\\u002E]{1}[\\d]+")) {
						if (type == QueryBridge.Type.NONE || type == QueryBridge.Type.INTEGER)
							types.set(i, QueryBridge.Type.DOUBLE);
					}
					else if (value.length() > QueryBridge.STRING_LENGHT_LIMIT) {
						if (type != QueryBridge.Type.MEMO)
							types.set(i, QueryBridge.Type.MEMO);
					}
					else {
						if (type != QueryBridge.Type.STRING && type != QueryBridge.Type.MEMO)
							types.set(i, QueryBridge.Type.STRING);
					}
					
				}								
			}
		}	
		
		if (hasHeader) records.remove(0);
		
		Logger.debug("get column names: " + fields.size());
		for (int j = 0; j < fields.size(); j++) {			
			Logger.debug(fields.get(j));
		}
				
		QueryBridge bridge = new QueryBridge(id, fields, types, c, true);				
		
		for (CSVRecord record : records) {				
			List<Object> values = new ArrayList<Object>();
			
			for (int j = 0; j < fields.size(); j++) {					
				Object value = record.get(j);
				QueryBridge.Type type = types.get(j);	
				
				if (value != null) {
					switch (type) {
						case INTEGER:
							value = Integer.parseInt((String)value); 
							break;
						case BOOLEAN:
							value = Boolean.parseBoolean((String)value);
							break;
						case DOUBLE:
							value = Double.parseDouble((String)value);
							break;
						case DATE:		
						case MEMO:
						case STRING:
							break;
						case NONE:	
							value = bridge.new Blank();
					}
				}
				else {
					value = bridge.new Blank();
				}
				values.add(value);								
			}
					
			List<String> queries = bridge.composeQuery(values);	
			for (String query : queries) {
				super.notifyListeners(new String[] {query, id});
			}	
						
		}
		
	}
	
	protected static String transliterate (String str) throws IOException {
		
		String name = TransliterateUtil.replaceSpaces(str);
		
		if (TransliterateUtil.checkAlphabetIsValid(name)) {
			Logger.debug("Transliterate: " + str);
			name = TransliterateUtil.transliterate(name);
			name = TransliterateUtil.replaceApostrofs(name);
		}
		return name;
	}

}
