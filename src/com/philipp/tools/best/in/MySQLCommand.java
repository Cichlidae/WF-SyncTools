package com.philipp.tools.best.in;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;

public class MySQLCommand implements StdinCommand {
	
	public static final String NAME = "mysql";
	
	@Parameter(description = "Database url", required = true)
	private List<String> db;
	
	@Parameter(names = {"-u", "-user"}, description = "Database user", required = true)
	private String user;
	
	@Parameter(names = {"-p", "-password"}, description = "Database password", required = true)
	private String password;
		
	@ParametersDelegate
	private MetaArgs meta = new MetaArgs();
	
	@Override
	public MetaArgs getMeta() {
		return meta;
	}

	public List<String> getDb() {
		return db;
	}
	
	public String getOnlyDb() {
		return db.get(0);
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getMetaFacade() {
		// TODO Auto-generated method stub
		return null;
	}		

}
