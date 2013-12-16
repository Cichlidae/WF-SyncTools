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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

import com.philipp.tools.best.db.ADOConnector;
import com.philipp.tools.best.db.ADOMetaDataExtractor;
import com.philipp.tools.best.in.ExcelCommand;
import com.philipp.tools.best.in.Input;
import com.philipp.tools.best.in.InputListener;
import com.philipp.tools.best.in.MSSQLCommand;
import com.philipp.tools.best.in.StdinCommand;
import com.philipp.tools.best.in.VFPCommand;
import com.philipp.tools.best.out.LoggerOutput;
import com.philipp.tools.best.out.Output;
import com.philipp.tools.common.log.Logger;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.converters.FileConverter;

public class ADOSQLShell {

	public static final String VERSION = "1.2.RC7";
	public static final String DESCRIPTION = "ADO SQL SHELL v" + VERSION;

	private final static int IN_PROCESS = 0;
	private final static int FAILED = 1;
	private final static int TERMINATED = 2;

	private static int STATUS = IN_PROCESS;	

	private static JCommander commander;

	private static VFPCommand vfpCommand = new VFPCommand();
	private static ExcelCommand excelCommand = new ExcelCommand();
	private static MSSQLCommand mssqlCommand = new MSSQLCommand();

	@Parameter(names = { "-v", "-verbose"}, description = "STD.ERR logging on/off") 
	private boolean verbose = false;

	@Parameter(names = "-xlsx", converter = FileConverter.class, description = "Excel file to output (needs excel-io-plugin.jar); input excel file if '-input' flag presents")
	private File excel;

	@Parameter(names = "-rewrite", description = "Flag if excel output (always create new file; used only with '-xlsx')") 
	private boolean rewrite = false;

	@Parameter(names = "-input", description = "Flag marked excel input (used only with '-xlsx')", hidden = true) 
	private boolean input = false;

	@Parameter(names = {"-help", "-?"}, description = "Help", help = true) 
	private boolean help; 

	@Parameter(names = "-version", description = "Product version") 
	private boolean version;

	private Connection connection = null;
	private Output out = new LoggerOutput();
	private Input<String> in = null;

	private Stack<String> commandStack = new Stack<String>();

	private ADOSQLShell (String[] args) {
		commander = new JCommander(this);
		commander.addCommand(VFPCommand.NAME, vfpCommand);
		commander.addCommand(ExcelCommand.NAME, excelCommand);
		commander.addCommand(MSSQLCommand.NAME, mssqlCommand);
		commander.parse(args);
	}
		
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {	
		
		File db = null;		
		StdinCommand incom = null;
		
		final ADOSQLShell manager = new ADOSQLShell(args);
		
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

		if (commander.getParsedCommand() != null && commander.getParsedCommand().compareTo(VFPCommand.NAME) == 0) {	
			incom = vfpCommand;
			db = new File(vfpCommand.getDbc().get(0)); 
			if (!db.exists()) {
				Logger.err(db.getAbsolutePath());
				Logger.err("Such VFP database file doesn't exist! Check correctness of path or filename.");
				System.exit(1);
				return;
			}					
		}	
		else if (commander.getParsedCommand() != null && commander.getParsedCommand().compareTo(ExcelCommand.NAME) == 0) {
			incom = excelCommand;
			db = new File(excelCommand.getOnlyXlsx()); 
			if (!db.exists()) {
				Logger.err(db.getAbsolutePath());
				Logger.err("Such excel file doesn't exist! Check correctness of path or filename.");
				System.exit(1);
				return;
			}										
		}
		else if (commander.getParsedCommand() != null && commander.getParsedCommand().compareTo(MSSQLCommand.NAME) == 0) {
			incom = mssqlCommand;
		}
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
			else {
				if (!manager.excel.exists() && manager.input) {
					Logger.err(manager.excel + " does not exist.");
					System.exit(1);
					return;
				}
				else if (!manager.excel.exists() && !manager.rewrite) {
					manager.rewrite = true;					
				}	
				if (manager.excel.exists() && !manager.excel.isFile()) {
					Logger.err(manager.excel + " is not a file.");
				}
				else if (manager.input) {
					try {
						Class<?> inClass = Class.forName("com.philipp.tools.best.in.ExcelInput");
						Constructor<?> c = inClass.getConstructor(new Class[]{File.class});
						manager.in = (Input<String>)c.newInstance(manager.excel);
						manager.in.addListener(
							new InputListener<String> () {
								@Override
								public void doEvent(String[] arg) {
									try {
										manager.doQuery(arg[0], arg[1]);
									}
									catch (Exception e) {}
								}									
							}
						);												
						Logger.debug("USE EXCEL IO PLUGIN");
					}
					catch (Exception e) {
						Logger.err("Cannot use -xlsx in. Required poi excel libs not found.");
						e.printStackTrace();
					}
				}				
				else {
					try {
						Class<?> outClass = Class.forName("com.philipp.tools.best.out.ExcelOutput");
						Constructor<?> c = outClass.getConstructor(new Class[]{File.class, boolean.class});
						manager.out = (Output)c.newInstance(manager.excel, manager.rewrite);
						Logger.debug("USE EXCEL IO PLUGIN");
					}			
					catch (Exception e) {
						Logger.err("Cannot use -xlsx out. Required poi excel libs not found.");
						e.printStackTrace();
					}					
				}
			}
		}
		
		Logger.debug("file.encoding: " + System.getProperty("file.encoding","undefined"));
		Logger.debug("console.encoding: " + System.getProperty("console.encoding","undefined"));
								
		System.exit(manager.process(incom));		
	}
	
	private int process (StdinCommand incom) {
		
		int exit = 0;
		
		try {		
			openConnection(incom);
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
		        	if (in != null) in.convert(s, incom);
		        	else doQuery(s, null);		        
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
			closeConnection();
			try {
				out.flushAll();
				Logger.debug("Flush out done.");
			}
			catch (IOException e) {
				Logger.err("Cannot flush out.");
				e.printStackTrace();			
			}
		}
		return exit;
	}
	
	private void openConnection (StdinCommand incom) {
		
		connection = ADOConnector.getADOConnection(incom);
		Logger.debug("Driver version: " + connection.getVersion());
		
	}
	
	private void closeConnection () {	
		if (connection != null) connection.Close();
	}
	
	private void doQuery (String sql, String table) throws Exception {
					
		sql = sql.trim();
		if ("".equals(sql)) return;
		
		Logger.debug(sql + "\n");
		
		Command comm = new Command();
		comm.setActiveConnection(connection);
		comm.setCommandType(CommandTypeEnum.adCmdText);		
		
		while (true) {
			comm.setCommandText(sql);
			switch (checkResultSelect(sql)) {
				case COMMENT: {
					String[] matched = matchComment(sql);
					if (matched != null) {						
						commandStack.push(COMMENT_MARKER + matched[0]);
						if (matched[1] != null) {
							sql = matched[1];
							continue;
						}			
						break;
					}	
					else {									
						commandStack.push(sql);
					}
					break;
				}
				case DROP: {	
					if (exists(table)) {					
						comm.Execute();					
					}	
					break;
				}
				case UPDATE: comm.Execute(); break;
				case SELECT:
				default: {
					Recordset rs = comm.Execute();	
					String note = DEFAULT_RESULT_NAME;
					List<String> handlers = new ArrayList<String>(0);
					
					if (!commandStack.empty()) { 
						note = parseComment(commandStack.pop(), handlers);					
					}
					
					if (!cancelComment(note)) out.printRS(note, rs, handlers);
					commandStack.clear();
				}
			}
			break;
		}
		comm.Cancel();
	}

	private static final int COMMENT = 0;
	private static final int SELECT = 1;
	private static final int UPDATE = 2;
	private static final int DROP = 3;

	private static final String DEFAULT_RESULT_NAME = "DEFAULT";
	
	private static final String COMMENT_MARKER = "--";
	public static final String HANDLER_MARKER = "@@";

	private static int checkResultSelect (String sql) {
		String uSQL = sql.toUpperCase().trim();	
		return uSQL.startsWith(COMMENT_MARKER) ? COMMENT : (
					uSQL.startsWith("SELECT") && uSQL.indexOf("INTO CURSOR") == -1 ? SELECT : (
							uSQL.startsWith("DROP") ? DROP : UPDATE
					)				
			   );										
	}

	private String[] matchComment (String sql) {
		
		String[] result = new String[2];		
		String uSQL = sql.trim();
	
		if (uSQL.matches(COMMENT_MARKER + ".+" + COMMENT_MARKER + ".*")) {	
			String source = uSQL.substring(COMMENT_MARKER.length());
			int idx = source.indexOf(COMMENT_MARKER);				
			result[0] = source.substring(0, idx);
			idx += COMMENT_MARKER.length();
			if (idx < source.length())			
				result[1] = source.substring(idx);
		}					
		return result;		
	}

	private String parseComment (String sql, List<String> handlers) {	
			
		String str = sql.trim().toUpperCase();
		
		if (!str.startsWith("--")) return DEFAULT_RESULT_NAME;
		str = str.substring(2).trim();
		
		if (str.length() == 0) return DEFAULT_RESULT_NAME;
		
		if (handlers != null) {			
			String[] pstr = StringUtils.split(str);
			str = "";
			for (String s : pstr) {
				if (s.startsWith(HANDLER_MARKER)) {
					handlers.add(s.substring(HANDLER_MARKER.length()));					
				}
				else {
					str += s;
				}
				Logger.debug(s);
			}		
		}				
		return str;
	}
	
	private boolean cancelComment (String comment) {
		if (!(out instanceof LoggerOutput)) {
			if (comment.charAt(0) == '!') return true;
		}
		return false;
	}
	
	private boolean exists (String table) {	
		List<String> tables = new ADOMetaDataExtractor(connection).getTables("");
		if (tables.contains(table)) return true;		
		return false;
	}

}
