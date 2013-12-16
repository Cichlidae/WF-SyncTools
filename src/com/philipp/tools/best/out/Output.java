package com.philipp.tools.best.out;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jacob.impl.ado.Recordset;

public interface Output {		
	
	public void printRS (String id, Recordset rs) throws IOException;
	
	public void printRS (String id, Recordset rs, List<String> handlers) throws IOException;
	
	public void printRS (String id, ResultSet rs) throws SQLException;
	
	public void printRS (String id, List<String> rs);
	
	public void printRS (String id, Map<String, ?> rs);
	
	public void flushAll () throws IOException;
	
	public void flushAll (File file) throws IOException;

}
