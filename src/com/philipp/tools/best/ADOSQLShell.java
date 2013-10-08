package com.philipp.tools.best;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;

import com.jacob.impl.ado.Command;
import com.jacob.impl.ado.CommandTypeEnum;
import com.jacob.impl.ado.Connection;
import com.jacob.impl.ado.Recordset;

//import java.util.ArrayList;
//import java.util.List;
import java.util.Stack;

import com.philipp.tools.best.db.ADOConnector;
import com.philipp.tools.best.in.VFPCommand;
import com.philipp.tools.best.log.Logger;
import com.philipp.tools.best.out.LoggerOutput;
import com.philipp.tools.best.out.Output;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.converters.FileConverter;

public class ADOSQLShell {
	
	public static final String VERSION = "1.1.0.1";
	public static final String DESCRIPTION = "ADO SQL SHELL v" + VERSION;

	private final static int IN_PROCESS = 0;
	private final static int FAILED = 1;
	private final static int TERMINATED = 2;
	
	private static int STATUS = IN_PROCESS;	
	
	private static JCommander commander;
	
	private static VFPCommand vfpCommand = new VFPCommand();
	
	//@Parameter(description="Full qualified foxpro database name (*.dbc)") 
	//private List<String> parameters = new ArrayList<String>(); 

	@Parameter(names = { "-v", "-verbose"}, description = "STD.ERR logging on/off") 
	private boolean verbose = false;
	
	@Parameter(names = "-xlsx", converter = FileConverter.class, description = "Excel file to output (need external poi libs)")
	private File excel;
	
	@Parameter(names = {"-help", "-?"}, help = true, hidden = true) 
	private boolean help; 
	
	@Parameter(names = "-version", description = "Product version") 
	private boolean version;
	
	private Output out = new LoggerOutput();
	
	private Stack<String> commandStack = new Stack<String>();
		
	private ADOSQLShell (String[] args) {
		//commander = new JCommander(this, args);
		commander = new JCommander(this);
		commander.addCommand("vfp", vfpCommand);
		commander.parse(args);
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		
		File db = null;			
		
		ADOSQLShell manager = new ADOSQLShell(args);
		
		if (manager.help) {
			commander.usage();
			System.exit(0);
			return;
		}
		
		if (manager.version) {
			Logger.log(DESCRIPTION);
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
			
		if (commander.getParsedCommand() != null && commander.getParsedCommand().compareTo("vfp") == 0) {		
			db = new File(vfpCommand.getDbc().get(0)); 
			if (!db.exists()) {
				Logger.err("Such VFP database file not exists! Check correctness of path or filename.");
				System.exit(1);
				return;
			}		
		}		
		/*if (manager.parameters.size() > 0) {											
			db = new File(manager.parameters.get(0)); 
			if (!db.exists()) {
				Logger.err("Such database file not exists! Check correctness of path or filename.");
				System.exit(1);
				return;
			}
		}*/
		else {
			Logger.err("Required parameters not found: database url!");
			Logger.err("");
			commander.usage();
			System.exit(1);
			return;
		}
		
		Logger.debug(DESCRIPTION);			
		
		if (manager.excel != null) {
			if (!new File(manager.excel.getParent()).exists()) {
				Logger.err("Dir for " + manager.excel + " doesn't exist.");				
			}
			else if (manager.excel.exists() && !manager.excel.isFile()) {
				Logger.err(manager.excel + " is not a file.");
			}
			else {
				if (manager.excel.exists()) {	
					if (!manager.excel.delete()) {
						Logger.err("Cannot delete " + manager.excel + ". Check if it's busy and unlock.");
					}																				
				}
				try {
					Class<?> outClass = Class.forName("com.philipp.tools.best.out.ExcelOutput");
					Constructor<?> c = outClass.getConstructor(new Class[]{File.class});
					manager.out = (Output)c.newInstance(manager.excel);
					Logger.debug("USE ADO SQL EXCEL PLUGIN v" + manager.out.getVersion());
				}
				catch (Exception e) {
					Logger.err("Cannot use -xlsx out. Required poi excel libs not found.");
					e.printStackTrace();
				}					
			}
		}			
		
		Logger.debug("file.encoding: " + System.getProperty("file.encoding","undefined"));
		Logger.debug("console.encoding: " + System.getProperty("console.encoding","undefined"));
		
		manager.process(db);		
	}
	
	private void process (File db) {
		
		Connection connection = null;	
		int exit = 0;
		
		try {
			connection = ADOConnector.getDBFADOConnection(db.getAbsolutePath());
			Logger.debug("Driver version: " + connection.getVersion());
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
		        	exit = 1;
		        	throw e;
		        }	
	        }
	        Logger.debug("All queries executed successfully.");	         
		}
        catch (Exception e) {            	        
            e.printStackTrace();
            exit = 1;
        }	
		finally {
			if (connection != null) connection.Close();
			try {
				out.flushAll();
				Logger.debug("Flush out done.");
			}
			catch (IOException e) {
				Logger.err("Cannot flush out.");
				e.printStackTrace();			
			}
		}
		 System.exit(exit);
	}
	
	private void doQuery (Connection connection, String sql) {
					
		sql = sql.trim();
		if ("".equals(sql)) return;
		
		Logger.debug(sql + "\n");
		
		Command comm = new Command();
		comm.setActiveConnection(connection);
		comm.setCommandType(CommandTypeEnum.adCmdText);
		comm.setCommandText(sql);
		
		switch (checkResultSelect(sql)) {
			case COMMENT: commandStack.push(sql); break;
			case UPDATE: comm.Execute(); break;
			case SELECT:
			default: {
				Recordset rs = comm.Execute();	
				String note = DEFAULT_RESULT_NAME;
				if (!commandStack.empty()) { 
					note = parseComment(commandStack.pop());
				}	
				if (note != null) out.printRS(note, rs);
				commandStack.clear();
			}
		}			
		comm.Cancel();
	}
	
	private static final int COMMENT = 0;
	private static final int SELECT = 1;
	private static final int UPDATE = 2;
	
	private static final String DEFAULT_RESULT_NAME = "SQLR";
		
	private static int checkResultSelect (String sql) {		
		String uSQL = sql.toUpperCase().trim();						
		return uSQL.startsWith("NOTE") || uSQL.startsWith("*") ? COMMENT : (
					uSQL.startsWith("SELECT") && uSQL.indexOf("INTO CURSOR") == -1 ? SELECT : UPDATE				
			   );										
	}
	
	private static String parseComment (String sql) {		
		if (sql == null)  return DEFAULT_RESULT_NAME;
		String str = sql.trim().toUpperCase();
		if (!str.startsWith("NOTE ") && !str.startsWith("* ")) return DEFAULT_RESULT_NAME;			
		str = str.substring(str.indexOf(' '));
		if (str.length() < 2) return DEFAULT_RESULT_NAME;
		str = str.substring(1);
		if (str.charAt(0) == '!') return null;	
		return str.trim();
	}

}
