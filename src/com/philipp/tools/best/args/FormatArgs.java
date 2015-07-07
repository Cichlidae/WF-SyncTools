package com.philipp.tools.best.args;

import com.beust.jcommander.Parameter;
import com.philipp.tools.common.Statics;
import com.philipp.tools.jcconverters.CSVFormatConverter;
import com.philipp.tools.jcconverters.DateFormatConverter;
import com.philipp.tools.jcconverters.DelimiterConverter;
import com.philipp.tools.jcconverters.HeaderConverter;

public class FormatArgs {
	
	@Parameter(names = "-header", converter = HeaderConverter.class, description = "Resultset header off/on/1 (stdout/csv only)", required = false)
	public Statics.HeaderFlag header = Statics.HeaderFlag.OFF;
	
	@Parameter(names = "-quote", description = "All string values have quotes in out (stdout/csv only)", required = false)
	public boolean quotesOn = false;
	
	@Parameter(names = "-datefmt", converter = DateFormatConverter.class, description = "The format of date", required = false)
	public Statics.DateFormat dateFormat = Statics.DateFormat.ISO8601;  
	
	@Parameter(names = { "-enc", "-encoding"}, description = "The output encoding (csv only)", required = false)
	public String enc = null;
	
	@Parameter(names = { "-dlm", "-delimiter"}, converter = DelimiterConverter.class, description = "The value delimiter (csv only)", required = false)
	public Character delimiter = null;
	
	@Parameter(names = { "-f", "-format"}, converter = CSVFormatConverter.class, description = "CSV format/type (csv only)", required = false)
	public Statics.CSVFormat csvFormat = Statics.CSVFormat.EXCEL;
	
	@Parameter(names = "-key", description = "Primary index table key (for '-input' only)", required = false)
	public int key = -1;

}
