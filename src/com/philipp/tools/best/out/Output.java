package com.philipp.tools.best.out;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.jacob.impl.ado.Recordset;

public interface Output {
	
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	
	public void printRS (String id, Recordset rs);
	
	public void flushAll () throws IOException;
	
	public void flushAll (File file) throws IOException;
	
	public String getVersion();

}
