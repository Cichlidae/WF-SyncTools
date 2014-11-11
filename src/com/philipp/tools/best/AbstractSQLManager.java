package com.philipp.tools.best;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.philipp.tools.best.db.IMetaDataExtractor;
import com.philipp.tools.best.db.IMetaFacader;
import com.philipp.tools.best.db.QueryBridge;
import com.philipp.tools.best.db.QueryBridge.Type;
import com.philipp.tools.best.in.Input;
import com.philipp.tools.best.in.StdinCommand;
import com.philipp.tools.best.out.LoggerOutput;
import com.philipp.tools.best.out.Output;
import com.philipp.tools.common.log.Logger;

public abstract class AbstractSQLManager implements ISQLManager {

	public final static int IN_PROCESS = 0;
	public final static int FAILED = 1;
	public final static int TERMINATED = 2;

	protected static int STATUS = IN_PROCESS;	

	protected Output out = null;
	
	protected AbstractSQLManager (Output out) {
		this.out = out;
	}
	
	public Output getOut() {
		return out;
	}

	protected abstract void openConnection (StdinCommand incom) throws SQLException;
	
	protected abstract void closeConnection () throws SQLException;
	
	protected abstract void doQuery (String sql, String table) throws SQLException, IOException;
	
	protected abstract boolean exists (String table) throws SQLException;

	@Override
	public int process(StdinCommand incom, Input<String> in) {
		
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
			try {
				closeConnection();
			}
			catch (SQLException e) {};
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
	
	protected abstract IMetaDataExtractor getMetaDataExtractor() throws SQLException;
	
	@Override
	public int processMeta(StdinCommand incom) {

		int exit = 0;
		
		try {
			openConnection(incom);
	        Logger.debug("Database opened.");
	        
	        IMetaDataExtractor md = getMetaDataExtractor();	   
	        
	        if (incom.getMeta().isTables()) {
	        	List<String> tables = md.getTables(incom.getMeta().tableRegex);
	        	out.printRS(IMetaDataExtractor.TABLES, tables);
	        }	        
	        
	        if (incom.getMeta().isColumns()) {
	        	String[] splitted = incom.getMeta().columnTables.split(",");
	        	for (String s : splitted) {	        	
	        		Map<String, Integer> columns = md.getColumns(s);
	        		if (incom.getMeta().asInsertStatement) {	        		
	        			try {
	        				IMetaFacader fc = this.getMetaFacader(incom.getMetaFacade());	       	        			
	        				Map<String, QueryBridge.Type> qbColumns = fc.getColumnsWithBridgeTypes(columns);
	        				
	        				Set<Entry<String, Type>> entries = qbColumns.entrySet();	        					        				
	        				QueryBridge bridge = new QueryBridge(s, entries, incom);	       	        					        			
	        				
		        			Logger.log(s + "->" + IMetaDataExtractor.COLUMNS);
		        			Logger.log(bridge.composeQuery());		        		
	        			} 
	        			catch (Exception e) {
	        				e.printStackTrace();
	        				out.printRS(s + "->" + IMetaDataExtractor.COLUMNS, columns);
	        			};
	        		}	        			        		
	        		else if (incom.getMeta().facade) {
	        			try {
	        				IMetaFacader fc = this.getMetaFacader(incom.getMetaFacade());	        					        		
		        			out.printRS(s + "->" + IMetaDataExtractor.COLUMNS, fc.getColumns(columns));	   
	        			}
	        			catch (Exception e) {
	        				e.printStackTrace();
	        				out.printRS(s + "->" + IMetaDataExtractor.COLUMNS, columns);
	        			};	        			     			
	        		}		        		
	        		else {
	        			out.printRS(s + "->" + IMetaDataExtractor.COLUMNS, columns);
	        		}
	        	}
	        }
	        
	        if (incom.getMeta().isPrimaryKeys()) {
	        	String[] splitted = incom.getMeta().pkTables.split(",");
	        	for (String s : splitted) {	
		        	List<String> pks = md.getPrimaryKeys(s);
		        	out.printRS(s + "->" + IMetaDataExtractor.PRIMARY_KEYS, pks);
	        	}
	        }
	        	        	        										
	        Logger.debug("All queries executed successfully.");	
		}
		catch (Exception e) {
			 Logger.err(e.toString());
			 Logger.err(e);
			 exit = 1;
		}
		finally {
			try {
				closeConnection();
			}
			catch (SQLException e) {};
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

	public static final int COMMENT = 0;
	public static final int SELECT = 1;
	public static final int UPDATE = 2;
	public static final int DROP = 3;
	public static final int GUID = 4;

	public static final String DEFAULT_RESULT_NAME = "DEFAULT";
	
	public static final String COMMENT_MARKER = "--";
	public static final String HANDLER_MARKER = "@@";

	protected static int checkResultSelect (String sql) {
		String uSQL = sql.toUpperCase().trim();	
			
		return uSQL.startsWith(COMMENT_MARKER) ? COMMENT : (
					uSQL.startsWith("SELECT") && uSQL.indexOf("INTO CURSOR") == -1 ? SELECT : (
							uSQL.startsWith("DROP") ? DROP : UPDATE
					)				
			   );										
	}
	
	protected String[] matchComment (String sql) {
		
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
	
	protected String parseComment (String sql, List<String> handlers) {	
			
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
	
	protected boolean cancelComment (String comment) {
		if (!(out instanceof LoggerOutput)) {
			if (comment.charAt(0) == '!') return true;
		}
		return false;
	}
	
	protected IMetaFacader getMetaFacader (String name) throws Exception {		
		Class<?> fcClass = Class.forName("com.philipp.tools.best.db." + name + "MetaFacader");
		Constructor<?> c = fcClass.getConstructor();
		return (IMetaFacader)c.newInstance();				
	}

}
