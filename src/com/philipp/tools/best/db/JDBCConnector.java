package com.philipp.tools.best.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import com.philipp.tools.best.in.DBFCommand;
import com.philipp.tools.best.in.MySQLCommand;
import com.philipp.tools.best.in.SQLiteCommand;
import com.philipp.tools.best.in.StdinCommand;
import com.philipp.tools.best.in.VFPCommand;
import com.philipp.tools.best.in.XBaseCommand;
import com.philipp.tools.best.in.XLSQLCommand;
import com.philipp.tools.common.log.Logger;

public class JDBCConnector {

	private JDBCConnector () {}

	public static Connection getXBaseConnection (String databasePath, String encoding) throws SQLException {
		
		try {
			Class.forName("com.hxtt.sql.dbf.DBFDriver");
		}
		catch (ClassNotFoundException e) {
			Logger.err("com.hxtt.sql.dbf.DBFDriver not found.");
			return null;
		}
		
		java.util.Properties connInfo = new java.util.Properties(); 
        connInfo.put("charSet", encoding);
		
		String url = "jdbc:dbf:/" + databasePath;	
		return  DriverManager.getConnection(url, connInfo);
	}

	public static Connection getVFPConnection (String databasePath, String encoding) throws SQLException {
		
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch (ClassNotFoundException e) {
			Logger.err("sun.jdbc.odbc.JdbcOdbcDriver not found.");
			return null;
		}
		
		java.util.Properties connInfo = new java.util.Properties(); 
        connInfo.put("charSet", encoding);
		
		String url = "jdbc:odbc:Driver={Microsoft FoxPro VFP Driver (*.dbf)};UID=;" + 
		             "SourceDB=" + databasePath + ";" + 
      		         "SourceType=DBC;Exclusive=No;BackgroundFetch=Yes;Collate=Machine;Null=Yes;Deleted=Yes;";
      
		return  DriverManager.getConnection(url, connInfo);	
	}
	
	public static Connection getDBFConnection (String databasePath, String encoding) throws SQLException {
		
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch (ClassNotFoundException e) {
			Logger.err("sun.jdbc.odbc.JdbcOdbcDriver not found.");
			return null;
		}
		
		java.util.Properties connInfo = new java.util.Properties(); 
        connInfo.put("charSet", encoding);
		
		String url = "jdbc:odbc:Driver={Microsoft FoxPro VFP Driver (*.dbf)};UID=;" + 
		             "SourceDB=" + databasePath + ";" + 
      		         "SourceType=DBF;Exclusive=No;BackgroundFetch=Yes;Collate=Machine;Null=Yes;Deleted=Yes;";
      
		return  DriverManager.getConnection(url, connInfo);	
	}

	public static Connection getSQLiteConnection (String databasePath) throws SQLException {
		
		try {
			Class.forName("org.sqlite.JDBC");  
		}
		catch (ClassNotFoundException e) {
			Logger.err("org.sqlite.JDBC not found.");
			return null;
		}

		String url = "jdbc:sqlite:" + databasePath;
		return DriverManager.getConnection(url);
	}

	public static Connection getMySQLConnection (String databasePath, String user, String password) throws SQLException {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			throw new SQLException ("Database jdbc driver 'com.mysql.jdbc.Driver' not found.");
		}
		
		String url = "jdbc:mysql://" + databasePath;
		Logger.debug(url);
		if (StringUtils.isNotBlank(user)) {
			if (StringUtils.isNotBlank(password)) {
				return  DriverManager.getConnection(url, user, password);
			}			
		}		 				
		return  DriverManager.getConnection(url);	

	}

	public static Connection getXLSQLConnection (String databasePath)throws SQLException {

		try {
			Class.forName("com.nilostep.xlsql.jdbc.xlDriver");
		}
		catch (ClassNotFoundException e) {
			throw new SQLException ("Database jdbc driver 'com.nilostep.xlsql.jdbc.xlDriver' not found.");
		}
		
		String url = "jdbc:nilostep:excel:" + databasePath;
		Logger.debug(url);
		return DriverManager.getConnection(url);
	}

	public static Connection getJDBCConnection (StdinCommand incom) throws IllegalArgumentException, SQLException {

		if (incom instanceof SQLiteCommand) {
			return JDBCConnector.getSQLiteConnection(((SQLiteCommand)incom).getOnlyDb());			
		}
		else if (incom instanceof MySQLCommand) {
			return JDBCConnector.getMySQLConnection(((MySQLCommand)incom).getOnlyDb(), ((MySQLCommand)incom).getUser(), ((MySQLCommand)incom).getPassword());
		}
		else if (incom instanceof VFPCommand) {
			return JDBCConnector.getVFPConnection(((VFPCommand)incom).getOnlyDbc(), ((VFPCommand)incom).getEncoding());
		}
		else if (incom instanceof DBFCommand) {
			return JDBCConnector.getDBFConnection(((DBFCommand)incom).getOnlyDbf(), ((DBFCommand)incom).getEncoding());
		}
		else if (incom instanceof XBaseCommand) {
			return JDBCConnector.getXBaseConnection(((XBaseCommand)incom).getOnlyDbf(), ((XBaseCommand)incom).getEncoding());
		}
		else if (incom instanceof XLSQLCommand) {
			return JDBCConnector.getXLSQLConnection(((XLSQLCommand)incom).getOnlyDb());
		}
		else {
			throw new IllegalArgumentException("Unrecognized database type (need to be one of: SQL Lite *.DB, MySQL url, VFP *.DBC, XBase *.DBF");
		}			
	}

}
