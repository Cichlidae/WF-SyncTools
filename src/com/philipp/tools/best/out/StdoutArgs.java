package com.philipp.tools.best.out;

import com.beust.jcommander.Parameter;
import com.philipp.tools.common.DateFormatConverter;
import com.philipp.tools.common.HeaderFlagConverter;
import com.philipp.tools.common.Statics;

public class StdoutArgs {
	
	@Parameter(names = "-header", converter = HeaderFlagConverter.class, description = "Resultset header off/on/1 (stdout only)", required = false)
	public Statics.HeaderFlag header = Statics.HeaderFlag.OFF;
	
	@Parameter(names = "-quote", description = "All string values have quotes in out (stdout only)")
	public boolean quotesOn = false;
	
	@Parameter(names = "-datefmt", converter = DateFormatConverter.class, description = "The format of date", required = false)
	public Statics.DateFormat dateFormat = Statics.DateFormat.ISO8601;  

}
