package com.philipp.tools.best.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.philipp.tools.best.log.Logger;

public class VFPDriverConnector {
	
	private VFPDriverConnector () {
	}
	
	public static Connection getHXTTConnection (String databasePath) throws SQLException {
		
		try {
			Class.forName("com.hxtt.sql.dbf.DBFDriver");
		}
		catch (ClassNotFoundException e) {
			Logger.err("Sun JDBS-ODB� ������� (bridge) �� ������.");
			return null;
		}
		
		String url = "jdbc:dbf:/" + databasePath;
 
		Logger.log(url);
		return  DriverManager.getConnection(url);
	}
	
	public static Connection getODBCConnection (String databasePath) throws SQLException {
		
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch (ClassNotFoundException e) {
			Logger.err("Sun JDBS-ODB� ������� (bridge) �� ������.");
			return null;
		}
		
		String url = "jdbc:odbc:Driver={Microsoft Visual FoxPro Driver};UID=;" + 
		             "SourceDB=" + databasePath + ";" + 
      		         "SourceType=DBC;Exclusive=No;BackgroundFetch=Yes;Collate=Machine;Null=Yes;Deleted=Yes;";
      
		Logger.log(url);
		return  DriverManager.getConnection(url);	
	}
	
	public static void getDatabaseMetaData (Connection connection) throws SQLException {
		
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		Logger.log("\nTABLES:\n");
		ResultSet tables = databaseMetaData.getTables(null, null, "sclad_*", null);
		while(tables.next()) {
		    String tableName = tables.getString(3);
		    Logger.log(tableName);
		}
						
		Logger.log("\nCOLUMNS:\n");
		ResultSet columns = databaseMetaData.getColumns(null, null, "sclad_mlabel", null);
		while(columns.next()) {
		    String columnName = columns.getString(4);
		    int columnType = columns.getInt(5);
		    Logger.log(columnName + "(" + columnType + ")");
		}
		
		Logger.log("\nPRIMARY KEYS:\n");
		ResultSet pkeys = databaseMetaData.getPrimaryKeys(null, null, "sclad_mlabel");
		while(pkeys.next()) {
		    String keyName = pkeys.getString(4);
		    Logger.log(keyName);
		}
		
		Logger.log("\nINDEXES:\n");
		ResultSet indexes = databaseMetaData.getIndexInfo(null, null, "sclad_mlabel", false, false);
		while(indexes.next()) {
		    String indexName = indexes.getString(6);
		    String indexUnique = indexes.getString(4);
		    String columnName = indexes.getString(9);
		    Logger.log(indexName + ": " + indexUnique + ": " + columnName);
		}
		
		Logger.log("\nFOREIGN KEYS:\n");
		ResultSet fkeys = databaseMetaData.getExportedKeys(null, null, "sclad_mlabel");
		while(fkeys.next()) {
			String ptab = indexes.getString(3);
			String ftab = indexes.getString(7);
			String pfield = indexes.getString(4);
			String ffield = indexes.getString(8);
			Logger.log(ptab + ": " + pfield + " <- " + ftab + ":" + ffield);
		}

	}

}
