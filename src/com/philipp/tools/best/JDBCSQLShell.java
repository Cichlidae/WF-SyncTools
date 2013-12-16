package com.philipp.tools.best;

import java.io.File;
import java.lang.reflect.Constructor;

import com.philipp.tools.best.in.Input;
import com.philipp.tools.best.in.InputListener;
import com.philipp.tools.best.in.MySQLCommand;
import com.philipp.tools.best.in.SQLiteCommand;
import com.philipp.tools.best.in.StdinArgs;
import com.philipp.tools.best.in.StdinCommand;
import com.philipp.tools.best.in.VFPCommand;
import com.philipp.tools.best.in.XBaseCommand;
import com.philipp.tools.best.in.XLSQLCommand;
import com.philipp.tools.best.out.LoggerOutput;
import com.philipp.tools.best.out.Output;
import com.philipp.tools.common.Statics;
import com.philipp.tools.common.log.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParametersDelegate;

public class JDBCSQLShell {

	public static final String VERSION = "1.2.RC7";
	public static final String DESCRIPTION = "JDBC SQL SHELL v" + VERSION;

	private static JCommander commander;

	private static SQLiteCommand sqliteCommand = new SQLiteCommand();
	private static MySQLCommand mysqlCommand = new MySQLCommand();
	private static XBaseCommand xbaseCommand = new XBaseCommand();
	private static VFPCommand vfpCommand = new VFPCommand();
	private static XLSQLCommand xlsqlCommand = new XLSQLCommand();

	@ParametersDelegate
	private StdinArgs arguments = new StdinArgs();

	private JDBCSQLShell (String[] args) {
		commander = new JCommander(this);
		commander.addCommand(SQLiteCommand.NAME, sqliteCommand);
		commander.addCommand(MySQLCommand.NAME, mysqlCommand);
		commander.addCommand(XBaseCommand.NAME, xbaseCommand);
		commander.addCommand(VFPCommand.NAME, vfpCommand);
		commander.addCommand(XLSQLCommand.NAME, xlsqlCommand);
		commander.parse(args);
	}

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {	

		File db = null;		
		StdinCommand incom = null;

		JDBCSQLShell shell = new JDBCSQLShell(args);			
		Input<String> in = null;
		Output out = new LoggerOutput();

		if (args.length == 0) {
			commander.usage();
			System.exit(1);
			return;
		}
		
		if (shell.arguments.help) {
			commander.usage();
			System.exit(0);
			return;
		}
						
		if (shell.arguments.version) {
			Logger.log(DESCRIPTION);
			System.exit(0);
			return;
		}
		
		if (shell.arguments.verbose) {
			Logger.DEBUG_ON = true;
		}				

		if (commander.getParsedCommand() != null && commander.getParsedCommand().compareTo(SQLiteCommand.NAME) == 0) {	
			incom = sqliteCommand;
			db = new File(sqliteCommand.getOnlyDb()); 
			if (!db.exists()) {
				Logger.err("Such SQLite database file not exists! Check correctness of path or filename.");
				System.exit(1);
				return;
			}					
		}	
		else if (commander.getParsedCommand() != null && commander.getParsedCommand().compareTo(MySQLCommand.NAME) == 0) {	
			incom = mysqlCommand;
			db = new File(mysqlCommand.getOnlyDb()); 
			if (!db.exists()) {
				Logger.err("Such MySQL database not exists! Check correctness of url.");
				System.exit(1);
				return;
			}					
		}	
		else if (commander.getParsedCommand() != null && commander.getParsedCommand().compareTo(XBaseCommand.NAME) == 0) {	
			incom = xbaseCommand;
			db = new File(xbaseCommand.getOnlyDbf()); 
			if (!db.exists()) {
				Logger.err("Such xBase database not exists! Check correctness of path or filename.");
				System.exit(1);
				return;
			}					
		}	
		else if (commander.getParsedCommand() != null && commander.getParsedCommand().compareTo(VFPCommand.NAME) == 0) {	
			
			if (Statics.isJvmArch64()) {
				System.exit(1);
				return;
			}
			
			incom = vfpCommand;
			db = new File(vfpCommand.getOnlyDbc()); 
			if (!db.exists()) {
				Logger.err("Such VFP database not exists! Check correctness of path or filename.");
				System.exit(1);
				return;
			}					
		}	
		else if (commander.getParsedCommand() != null && commander.getParsedCommand().compareTo(XLSQLCommand.NAME) == 0) {	
			incom = xlsqlCommand;
			db = new File(xlsqlCommand.getOnlyDb()); 
			if (!db.exists()) {
				Logger.err("Such xlSQL database not exists! Check correctness of the path.");
				System.exit(1);
				return;
			}					
		}					
		else {
			Logger.err("Database command not found!");
			Logger.err("");
			commander.usage();
			System.exit(1);
			return;
		}
		
		Logger.debug(DESCRIPTION);			
		
		if (shell.arguments.excel != null) {
			if (!new File(shell.arguments.excel.getParent()).exists()) {
				Logger.err("Dir for " + shell.arguments.excel + " doesn't exist.");					
			}
			else {
				if (!shell.arguments.excel.exists() && shell.arguments.input) {
					Logger.err(shell.arguments.excel + " does not exist.");
					System.exit(1);
					return;
				}
				else if (!shell.arguments.excel.exists() && !shell.arguments.rewrite) {
					shell.arguments.rewrite = true;					
				}	
				if (shell.arguments.excel.exists() && !shell.arguments.excel.isFile()) {
					Logger.err(shell.arguments.excel + " is not a file.");
				}
				else if (shell.arguments.input) {
					try {
						Class<?> inClass = Class.forName("com.philipp.tools.best.in.ExcelInput");
						Constructor<?> c = inClass.getConstructor(new Class[]{File.class});
						in = (Input<String>)c.newInstance(shell.arguments.excel);														
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
						out = (Output)c.newInstance(shell.arguments.excel, shell.arguments.rewrite);
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
						
		final AbstractSQLManager manager = new JDBCSQLManager(out);
		
		if (in != null) {
			in.addListener(
				new InputListener<String> () {
					@Override
					public void doEvent(String[] arg) throws Exception {
						manager.doQuery(arg[0], arg[1]);									
					}									
				}
			);	
		}
		
		if (!incom.getMeta().isMeta()) System.exit(manager.process(incom, in));									
		else System.exit(manager.processMeta(incom));
	}	

}
