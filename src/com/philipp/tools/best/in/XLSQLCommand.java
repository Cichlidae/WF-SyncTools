package com.philipp.tools.best.in;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

@Parameters(commandDescription = "xlSQL database connection: excel, xml, csv")
public class XLSQLCommand implements StdinCommand {
	
	public static final String NAME = "xlsql";
	
	@Parameter(description="Full qualified xlsql database dir", required = true) 
	private List<String> db;
	
	@ParametersDelegate
	private MetaArgs meta = new MetaArgs();

	public List<String> getDb() {
		return db;
	} 	
	
	public String getOnlyDb () {
		return getDb().get(0);
	}

	@Override
	public MetaArgs getMeta() {
		// TODO Auto-generated method stub
		return meta;
	}

	@Override
	public String getMetaFacade() {
		// TODO Auto-generated method stub
		return null;
	}

}
