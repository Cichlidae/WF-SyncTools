package com.philipp.tools.best.in;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.philipp.tools.best.args.MetaArgs;

@Parameters(commandDescription = "Visual Fox Pro database connection (only 32-bit JVM supported)")
public class DBFCommand implements StdinCommand {
	
	public static final String NAME = "dbf";
	
	@Parameter(description="Full qualified foxpro database catalog (*.dbf)", required = true) 
	private List<String> dbf;
	
	@Parameter(names= {"-enc", "-encoding"}, description = "Database codepage")
	private String encoding = "cp1251";
	
	@ParametersDelegate
	private MetaArgs meta = new MetaArgs();

	public List<String> getDbf() {
		return dbf;
	} 	
	
	public String getOnlyDbf () {
		return getDbf().get(0);
	}

	@Override
	public MetaArgs getMeta() {
		return meta;
	}

	@Override
	public String getMetaFacade() {		
		return "DBF";
	}

	public String getEncoding() {
		return encoding;
	}

}
