package com.philipp.tools.best.args;

import com.beust.jcommander.Parameter;

public class MetaArgs {
	
	@Parameter(names = {"-tab", "-tables"}, description = "Show database table list.") 
	public String tableRegex;
	
	@Parameter(names = {"-col", "-columns"}, description = "Show table(s) column list.") 
	public String columnTables;
	
	@Parameter(names = {"-pk", "-primary-keys"}, description = "Show table(s) primary keys.") 
	public String pkTables;
	
	@Parameter(names = {"-fc", "-facade"}, description = "Show description instead of codes.")
	public boolean facade = false;
	
	@Parameter(names = "-ist", description = "Show result as INSERT INTO ... SELECT statement (turns off -fc).")
	public boolean asInsertStatement = false;
	
	public boolean isMeta () {
		return tableRegex != null ||  columnTables != null || pkTables != null;
	}
	
	public boolean isTables () {
		return tableRegex != null;
	}
	
	public boolean isColumns () {
		return columnTables != null;
	}
	
	public boolean isPrimaryKeys () {
		return pkTables != null;
	}
	
}
