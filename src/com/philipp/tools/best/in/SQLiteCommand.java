package com.philipp.tools.best.in;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

@Parameters(commandDescription = "SQLite database connection")
public class SQLiteCommand implements StdinCommand {
	
	public static final String NAME = "sqlite";
	
	@Parameter(description="Full qualified sqlite database name (*.db)", required = true) 
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
