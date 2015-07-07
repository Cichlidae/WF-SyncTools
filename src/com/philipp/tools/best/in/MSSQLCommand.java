package com.philipp.tools.best.in;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.philipp.tools.best.args.MetaArgs;

@Parameters(separators = "=", commandDescription = "SQL Server connection")
public class MSSQLCommand implements StdinCommand {
	
	public static final String NAME = "mssql";

	@Parameter(description="Initial catalog name", required = true) 
	private List<String> catalog;
	
	@Parameter(names = {"-ds", "-datasource"}, description = "Data source") 
	private String dataSource = "localhost";
		
	@Parameter(names = "-wid", description = "Workstation ID", required = true)
	private String workstationId;

	public List<String> getCatalog() {
		return catalog;
	}
	
	public String getOnlyCatalog() {
		return getCatalog().get(0);
	}

	public String getDataSource() {
		return dataSource;
	}

	public String getWorkstationId() {
		return workstationId;
	}

	@Override
	public MetaArgs getMeta() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMetaFacade() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
