package com.philipp.tools.best;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;

import com.jacob.com.Variant;
import com.jacob.impl.ado.Command;
import com.jacob.impl.ado.CommandTypeEnum;
import com.jacob.impl.ado.Connection;
import com.jacob.impl.ado.Recordset;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

import com.philipp.tools.best.args.StdinArgs;
import com.philipp.tools.best.db.ADOConnector;
import com.philipp.tools.best.db.ADOMetaDataExtractor;
import com.philipp.tools.best.in.CSVInput;
import com.philipp.tools.best.in.ExcelCommand;
import com.philipp.tools.best.in.Input;
import com.philipp.tools.best.in.InputListener;
import com.philipp.tools.best.in.MSSQLCommand;
import com.philipp.tools.best.in.StdinCommand;
import com.philipp.tools.best.in.VFPCommand;
import com.philipp.tools.best.out.CSVOutput;
import com.philipp.tools.best.out.LoggerOutput;
import com.philipp.tools.best.out.Output;
import com.philipp.tools.common.Statics;
import com.philipp.tools.common.log.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParametersDelegate;

public class ADOSQLShell {

	public static final String DESCRIPTION = "ADO SQL SHELL v" + Statics.VERSION;

	private final static int IN_PROCESS = 0;
	private final static int FAILED = 1;
	private final static int TERMINATED = 2;

	private static int STATUS = IN_PROCESS;	

	private static JCommander commander;

	private static VFPCommand vfpCommand = new VFPCommand();
	private static ExcelCommand excelCommand = new ExcelCommand();
	private static MSSQLCommand mssqlCommand = new MSSQLCommand();
	
	@ParametersDelegate
	private StdinArgs arguments = new StdinArgs();	

	private Connection connection = null;
	private Output out = null;
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
		manager.out = new LoggerOutput(manager.arguments.frmArgs);		
		
		if (manager.arguments.help) {
			commander.usage();
			System.exit(0);
			return;
		}
		
		if (manager.arguments.version) {
			Logger.log(DESCRIPTION);
			System.exit(0);
			return;
		}

		if (manager.arguments.verbose) {
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
		
		if (manager.arguments.excel != null) {						
			if (!new File(manager.arguments.excel.getParent()).exists()) {
				Logger.err("Dir for " + manager.arguments.excel + " doesn't exist.");					
			}
			else {
				if (!manager.arguments.excel.exists() && manager.arguments.input) {
					Logger.err(manager.arguments.excel + " does not exist.");
					System.exit(1);
					return;
				}
				else if (!manager.arguments.excel.exists() && !manager.arguments.rewrite) {
					manager.arguments.rewrite = true;					
				}	
				if (manager.arguments.excel.exists() && !manager.arguments.excel.isFile()) {
					Logger.err(manager.arguments.excel + " is not a file.");
				}
				else if (manager.arguments.input) {
					try {
						Class<?> inClass = Class.forName("com.philipp.tools.best.in.ExcelInput");
						Constructor<?> c = inClass.getConstructor(new Class[]{File.class});
						manager.in = (Input<String>)c.newInstance(manager.arguments.excel);
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
						Logger.debug("USING POI EXCEL IO PLUGIN");
					}
					catch (Exception e) {
						Logger.err("Cannot use xlsx in. Required poi excel libs not found or incompatible.");
						e.printStackTrace();
					}
				}				
				else {
					try {
						Class<?> outClass = Class.forName("com.philipp.tools.best.out.ExcelOutput");
						Constructor<?> c = outClass.getConstructor(new Class[]{File.class, boolean.class});
						manager.out = (Output)c.newInstance(manager.arguments.excel, manager.arguments.rewrite);
						Logger.debug("USING POI EXCEL IO PLUGIN");
					}			
					catch (Exception e) {
						Logger.err("Cannot use xlsx out. Required poi excel libs not found or incompatible.");
						e.printStackTrace();
					}					
				}
			}
		}
		else {
			switch (checkPlugin(manager.arguments.csv, manager.arguments.input, manager.arguments.rewrite)) {
				case PLUGIN_CRASH:
					System.exit(1);
					return;
				case PLUGIN_DISABLED:
					break;
				case PLUGIN_ENABLED_REWRITABLE:
					manager.arguments.rewrite = true;
				case PLUGIN_ENABLED:
					if (manager.arguments.input) {
						try {					
							manager.in = new CSVInput(manager.arguments.csv, manager.arguments.frmArgs);
							manager.in.addListener(
									new InputListener<String> () {
										@Override
										public void doEvent(String[] arg) {
											try {
												manager.doQuery(arg[0], arg[1]);
											}
											catch (Exception e) {
												e.printStackTrace();
											}
										}									
									}
								);
						}
						catch (Exception e) {
							Logger.err("Cannot use csv in.");
							e.printStackTrace();
						}
					}
					else {
						try {							
							manager.out = new CSVOutput(manager.arguments.csv, manager.arguments.rewrite, manager.arguments.frmArgs);																					
						}			
						catch (Exception e) {
							Logger.err("Cannot use csv out.");
							e.printStackTrace();
						}							
					}				
					break;
			}
		}
		
		Logger.debug("file.encoding: " + System.getProperty("file.encoding","undefined"));
		Logger.debug("console.encoding: " + System.getProperty("console.encoding","undefined"));
								
		System.exit(manager.process(incom));		
	}
	
	static final int PLUGIN_CRASH = -1;
	static final int PLUGIN_DISABLED = 0;
	static final int PLUGIN_ENABLED = 1;
	static final int PLUGIN_ENABLED_REWRITABLE = 2;
			
	private static int checkPlugin (File pluginFile, boolean input, boolean rewrite) {
		
		int r = PLUGIN_DISABLED;
		
		if (pluginFile != null) {
			if (!new File(pluginFile.getParent()).exists()) {
				Logger.err("Dir for " + pluginFile + " doesn't exist.");
			}	
			else {
				if (!pluginFile.exists() && input) {
					Logger.err(pluginFile + " does not exist.");
					return PLUGIN_CRASH;
				}
				if (!pluginFile.exists() && !rewrite) {
					r = PLUGIN_ENABLED_REWRITABLE;					
				}	
				if (pluginFile.exists() && !pluginFile.isFile()) {
					Logger.err(pluginFile + " is not a file.");
					r = PLUGIN_DISABLED;
				}
				r = PLUGIN_ENABLED;
			}
		}
		return r;
		
	}
	
	private int process (StdinCommand incom) {
		
		int exit = 0;
		String s = "";
		
		try {		
			openConnection(incom);
	        Logger.debug("Database opened.");
	   
	        BufferedReader scan =
					new BufferedReader(new InputStreamReader(System.in, Logger.FILE_ENCODING));
	        
	        while (STATUS == IN_PROCESS) {	        
		        s = scan.readLine();		      		        
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
		        	Logger.err("\nFail query: " + s + "\n");		        	
		        	exit = 1;
		        	throw e;
		        }	
	        }
	        Logger.debug("All queries executed successfully.");	         
		}
        catch (Exception e) {
			Logger.err(e);
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
				case BEGIN_TRANSACTION: int st = connection.BeginTrans(); Logger.debug(st); break;
				case COMMIT_TRANSACTION: connection.CommitTrans(); break;
				case ROLLBACK_TRANSACTION: connection.RollbackTrans(); break;
				case UPDATE: comm.Execute(); break;		
				case PREPARED: {
					//TODO test
					Variant affectedCount = new Variant(0L);
					Variant parameter = comm.CreateStringInputParameter("Test", "Test");						
					comm.Execute(affectedCount, new Variant[] {parameter}); 							
		            break;
				}    
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
	private static final int PREPARED = 4;
	private static final int BEGIN_TRANSACTION = 5;
	private static final int COMMIT_TRANSACTION = 6;
	private static final int ROLLBACK_TRANSACTION = 7;
	

	private static final String DEFAULT_RESULT_NAME = "DEFAULT";
	
	private static final String COMMENT_MARKER = "--";
	public static final String HANDLER_MARKER = "@@";

	private static int checkResultSelect (String sql) {
		String uSQL = sql.toUpperCase().trim();	
		return uSQL.startsWith(COMMENT_MARKER) ? COMMENT : (
					uSQL.startsWith("SELECT") && uSQL.indexOf("INTO CURSOR") == -1 ? SELECT : (
							uSQL.startsWith("DROP") ? DROP : (
									uSQL.startsWith("BEGIN TRANSACTION") ? BEGIN_TRANSACTION : (
											uSQL.startsWith("COMMIT TRANSACTION") ? COMMIT_TRANSACTION : (
													uSQL.startsWith("ROLLBACK TRANSACTION") ? ROLLBACK_TRANSACTION : UPDATE
											)											
									)																		
							)			
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
