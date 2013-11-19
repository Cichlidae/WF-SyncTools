package com.philipp.tools.best.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;

import com.philipp.tools.best.in.StdinCommand;
import com.philipp.tools.best.in.VFPCommand;
import com.philipp.tools.common.Statics;

public class QueryBridge {

	public enum Type {
		NONE, STRING, INTEGER, DOUBLE, DATE, BOOLEAN, MEMO
	}		
	
	public final class Blank extends Object {
		public Blank () {
			super();
		}
	}

	private StdinCommand c;
	private String tabName;
	private List<QueryBridge.Type> types;
	private Set<Entry<String, Type>> entries;
	private List<String> iSQLs = new ArrayList<String>(2);
	
	public QueryBridge(String tabName, Set<Entry<String, Type>> entries, StdinCommand c) {
		this.tabName = tabName.toUpperCase();
		this.c = c;
		this.entries = entries;		
	}
	
	public QueryBridge(String tabName, List<String> fields, List<QueryBridge.Type> types,  StdinCommand c) {
		this(tabName, fields, types, c, false);
	}
	
	public QueryBridge(String tabName, List<String> fields, List<QueryBridge.Type> types,  StdinCommand c, boolean createBefore) {
		this.tabName = tabName.toUpperCase();
		this.types = types;
		this.c = c;
		
		if (!createBefore) return;
		
		if (c instanceof VFPCommand) {
			iSQLs.add("DROP TABLE " + this.tabName);
			String csql = "CREATE TABLE " + this.tabName + "(";
		
			for (int i = 0; i < fields.size(); i++) {		
				csql += fields.get(i) + " ";
				QueryBridge.Type t = types.get(i);
				
				switch (t) {
					case STRING: csql += "C(254)"; break;
					case INTEGER: csql += "I"; break;
					case DOUBLE: csql += "B"; break;
					case DATE: csql += "D"; break;	
					case BOOLEAN: csql += "L"; break;
					case MEMO: csql += "M"; break;
					case NONE: csql += "G"; break;
				}
				csql += ",";
			}
			csql = csql.substring(0, csql.length() - 1) +  ")";	
			iSQLs.add(csql);
		}
		else {
			throw new IllegalArgumentException("Unsupported command " + c.getClass().getName());
		}

	}

	public List<String> composeQuery (List<Object> values) {
		
		List<String> sqls = new ArrayList<String>(iSQLs);
		
		String sql = "INSERT INTO " + tabName + " VALUES (";
		for (int i = 0; i < values.size(); i++) {
			Object value = values.get(i);
			if (value instanceof String) {	
				String str = (String)value;
				if (str != null) str = StringEscapeUtils.escapeSql(str); 									
				sql += "'" + str + "',";			
			}
			else if (value instanceof Date) {
				if (c instanceof VFPCommand) {
					Date date = (Date)value; 										
					sql += "{d'" + Statics.DATE_FORMATTER.format(date) + "},";				
				}
				else {
					sql += value + ",";
				}								
			}
			else if (value instanceof Blank) {
				switch (types.get(i)) {					
					case STRING: sql += "'',"; break;
					case INTEGER:
					case DOUBLE: sql += "0,"; break;	
					case BOOLEAN:		
					case NONE:	
					default: sql += ","; 
				}												
			}
			else {								
				sql += value + ",";
			}
		}
		sql = sql.substring(0, sql.length() - 1) +  ")";	
		sqls.add(sql);
		if (!iSQLs.isEmpty()) iSQLs.clear();
		return sqls;
	}
	
	public String composeQuery () {
				
		String sql = "INSERT INTO " + tabName + " (";
		String sqlsuf = ") SELECT ";
		
		Iterator<Entry<String, Type>> iterator = entries.iterator();
		while (iterator.hasNext()) {
			Entry<String, Type> entry = iterator.next();
			String field = entry.getKey();
			sql += field + ", ";
			
			QueryBridge.Type t = entry.getValue();
			
			switch (t) {
				case MEMO:
				case DATE:
				case STRING: sqlsuf += "'' AS " + field + ", "; break;
				case DOUBLE:
				case BOOLEAN:
				case INTEGER: sqlsuf += "0 AS " + field + ", "; break;							
				case NONE: sqlsuf += "NULL AS " + field + ", "; break;
			}													
		}
		sql = sql.substring(0, sql.length() - 2);
		sqlsuf = sqlsuf.substring(0, sqlsuf.length() - 2);
		
		sql += sqlsuf + " FROM <?>;";	
		return sql;			
	}

}
