package com.philipp.tools.best.db;

import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCMetaDataExtractor implements IMetaDataExtractor {
	
	private DatabaseMetaData dbmd;
	
	public JDBCMetaDataExtractor (Connection c) throws SQLException {		
		this.dbmd = c.getMetaData();
	}

	@Override
	public List<String> getTables (String regex) throws SQLException {
		
		List<String> r = new ArrayList<String>();

		ResultSet tables = dbmd.getTables(null, null, regex, null);
		while (tables.next()) {			
		    r.add(tables.getString(3));
		}
		return r;			
	}

	@Override
	public Map<String, Integer> getColumns (String table) throws SQLException {
		
		Map<String, Integer> r = new HashMap<String, Integer>();
		
		ResultSet columns = dbmd.getColumns(null, null, table, null);
		while (columns.next()) {
		    String columnName = columns.getString(4);
		    int columnType = columns.getInt(5);
		    r.put(columnName, columnType);
		}
		return r;		
	}
	
	@Override
	public List<String> getPrimaryKeys (String table) throws SQLException {
		
		List<String> r = new ArrayList<String>();
		
		ResultSet pkeys = dbmd.getPrimaryKeys(null, null, table);
		while (pkeys.next()) {
		    r.add(pkeys.getString(4));
		}
		return r;
	}
	
	@Override
	public Map<String, String> getIndexes (String table) throws SQLException {
		
		Map<String, String> m = new HashMap<String, String>();
		
		ResultSet indexes = dbmd.getIndexInfo(null, null, table, false, false);
		while (indexes.next()) {
		    String indexName = indexes.getString(6);
		    boolean indexUnique = Boolean.parseBoolean(indexes.getString(4));
		    String columnName = indexes.getString(9);
		    m.put(indexName + (indexUnique ? "*" : ""), columnName);	
		}		
		return m;
	}
	
	@Override
	public Map<String, String> getExportedKeys(String table) throws SQLException {
		
		Map<String, String> m = new HashMap<String, String>();
		
		ResultSet fkeys = dbmd.getExportedKeys(null, null, table);
		while(fkeys.next()) {
			String ptab = fkeys.getString(3);
			String ftab = fkeys.getString(7);
			String pfield = fkeys.getString(4);
			String ffield = fkeys.getString(8);
			m.put(ptab + "." + pfield, ftab + "." + ffield);					
		}		
		return m;
	}

}
