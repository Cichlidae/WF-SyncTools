package com.philipp.tools.best.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import com.jacob.com.Variant;
import com.jacob.impl.ado.Command;
import com.jacob.impl.ado.CommandTypeEnum;
import com.jacob.impl.ado.Connection;
import com.jacob.impl.ado.Field;
import com.jacob.impl.ado.Fields;
import com.jacob.impl.ado.Recordset;

import java.util.ArrayList;
import java.util.List;

import com.philipp.tools.best.db.DBFADOConnector;
import com.philipp.tools.best.log.Logger;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;

public class ADOQueryManager {

	private final static int IN_PROCESS = 0;
	private final static int FAILED = 1;
	private final static int TERMINATED = 2;
	
	private static int STATUS = IN_PROCESS;
	
	private static JCommander commander;
	
	@Parameter(description="Full qualified foxpro database name (*.dbc)") 
	private List<String> parameters = new ArrayList<String>(); 

	@Parameter(names = { "-v", "-verbose"}, description = "STD.ERR logging on/off") 
	private boolean verbose = false;
	
	@Parameter(names = "--help", help = true, hidden = true) 
	private boolean help; 
	
	private ADOQueryManager (String[] args) {
		commander = new JCommander(this, args);
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		
		File db = null;			
		
		ADOQueryManager manager = new ADOQueryManager(args);
		
		if (manager.help) {
			commander.usage();
			System.exit(0);
			return;
		}
		
		if (manager.verbose) {
			Logger.DEBUG_ON = true;
		}
						
		String jvmArch = System.getProperty("sun.arch.data.model");
		if (jvmArch.contains("64")) {
			Logger.err("You have JVM 64-bit installed by default, but it needs JVM 32-bit for correct work.");
			System.exit(1);
			return;
		}	
		
		if (manager.parameters.size() > 0) {											
			db = new File(manager.parameters.get(0)); 
			if (!db.exists()) {
				Logger.err("Such database file not exists! Check correctness of path.");
				System.exit(1);
				return;
			}	
		}
		else {
			Logger.err("Required parameters not found: database url!");
			Logger.err("");
			commander.usage();
			System.exit(1);
			return;
		}
		
		Logger.debug("file.encoding: " + System.getProperty("file.encoding","undefined"));
		Logger.debug("console.encoding: " + System.getProperty("console.encoding","undefined"));		
		
		process(db);						
		
	}
	
	private static void process (File db) {
		
		Connection connection = null;
		
		try {
			connection = DBFADOConnector.getDBFADOConnection(db.getAbsolutePath());
			Logger.debug("Version: " + connection.getVersion());
	        Logger.debug("Database opened.");
	   
	        BufferedReader scan =
					new BufferedReader(new InputStreamReader(System.in, Logger.FILE_ENCODING));
	        
	        while (STATUS == IN_PROCESS) {	       		       		        
		    	       
		        String s = scan.readLine();		      		        
		        if (s == null || s.toLowerCase().compareTo("exit") == 0) {
		        	STATUS = TERMINATED;
		        	continue;
		        }
		        try {		              		       
		        	doQuery(connection, s);
		        }
		        catch (Exception e) {
		        	STATUS = FAILED;
		        	Logger.err("Oops! Query execution failed.");
		        	e.printStackTrace();
		        	System.exit(1);
		        	throw e;
		        }
		
	        }
	        Logger.debug("All queries executed successfully."); 
	        System.exit(0);
		}
        catch (Exception e) {            	        
            e.printStackTrace();
            System.exit(1);
        }	
		finally {
			if (connection != null) connection.Close();
		}
	}
	
	private static void doQuery (Connection connection, String sql) {
						
		sql = sql.trim();
		
		if ("".equals(sql)) return;
						
		Logger.debug(sql + "\n");
		
		Command comm = new Command();
		comm.setActiveConnection(connection);
		comm.setCommandType(CommandTypeEnum.adCmdText);
		comm.setCommandText(sql);
		
		if (!checkResultSelect(sql)) {						
			comm.Execute();			
		}
		else {					
			Recordset rs = comm.Execute();
			printRS(rs);					
		}					
	}
	
	public static void printRS(Recordset rs) {
	    Fields fs = rs.getFields();

	    for (int i = 0; i < fs.getCount(); i++) {
	      Logger.debug(fs.getItem(i).getName() + "\t");
	    }
	    Logger.debug("");
    
	    if (!rs.getEOF()) rs.MoveFirst();
	    while (!rs.getEOF()) {
	      String stroke = "";	
	      for (int i = 0; i < fs.getCount(); i++) {
	        Field f = fs.getItem(i);
	        Variant v = f.getValue();
	        stroke += v + "\t";
	      } 	
	      Logger.log(stroke);	      
	      rs.MoveNext();
	    }
	    Logger.log("");
	}
	
	private static boolean checkResultSelect (String sql) {
		
		String uSQL = sql.toUpperCase();	
		return uSQL.startsWith("SELECT") && uSQL.indexOf("INTO CURSOR") == -1;
		
	}
	
}
