package com.philipp.tools.best.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
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
	
	public static final int STRING_LENGHT_LIMIT = 254;
	
	public QueryBridge(String tabName, Set<Entry<String, Type>> entries, StdinCommand c) {
		this.tabName = tabName.toUpperCase();
		this.c = c;
		this.entries = entries;		
	}
	
	public QueryBridge(String tabName, List<String> fields, List<QueryBridge.Type> types,  StdinCommand c) {
		this(tabName, fields, types, c, false);
	}
	
	public QueryBridge(String tabName, List<String> fields, List<QueryBridge.Type> types, StdinCommand c, boolean createBefore) {
		this.tabName = tabName.toUpperCase();
		this.types = types;
		this.c = c;
		
		if (!createBefore) return;
		
		if (c instanceof VFPCommand) {
			iSQLs.add("DROP TABLE " + this.tabName);
			StringBuilder csql = new StringBuilder("CREATE TABLE ").append(this.tabName).append("(");
		
			for (int i = 0; i < fields.size(); i++) {		
				csql.append(fields.get(i)).append(" ");
				QueryBridge.Type t = types.get(i);
				
				switch (t) {
					case STRING: csql.append("C(254)"); break;
					case INTEGER: csql.append("I"); break;
					case DOUBLE: csql.append("B"); break;
					case DATE: csql.append("D"); break;	
					case BOOLEAN: csql.append("L"); break;
					case MEMO: csql.append("M"); break;
					case NONE: csql.append("C(254)"); break;
				}
				csql.append(",");
			}
			csql.deleteCharAt(csql.lastIndexOf(",")).append(")");		
			iSQLs.add(csql.toString());		
		}
		else {
			throw new IllegalArgumentException("Unsupported command " + c.getClass().getName());
		}

	}

	public List<String> composeQuery (List<Object> values) {
		
		List<String> sqls = new ArrayList<String>(iSQLs);
		StringBuilder sql = new StringBuilder("INSERT INTO ").append(tabName).append(" VALUES ("); 	
		Splitter splitter = Splitter.fixedLength(STRING_LENGHT_LIMIT);
		Joiner joiner = Joiner.on("'+'");
		
		for (int i = 0; i < values.size(); i++) {
			Object value = values.get(i);
			if (types.get(i) == Type.MEMO) {
				String str = (String)value;
				if (str != null) str = StringEscapeUtils.escapeSql(str);			
				sql.append("'").append(joiner.join(splitter.split(str))).append("',");
			}			
			else if (value instanceof String) {	
				String str = (String)value;
				if (str != null) str = StringEscapeUtils.escapeSql(str); 
				sql.append("'").append(str).append("',");
			}
			else if (value instanceof Date) {
				if (c instanceof VFPCommand) {
					Date date = (Date)value; 							
					sql.append("{d'").append(Statics.DATE_FORMATTER.format(date)).append("'},");				
				}
				else {
					sql.append("'").append(value).append("',");
				}								
			}
			else if (value instanceof Blank) {
				switch (types.get(i)) {					
					case STRING: sql.append("'',"); break;
					case INTEGER:
					case DATE: sql.append("{d'1970-01-01'},"); break;	
					case DOUBLE: sql.append("0,"); break;	
					case BOOLEAN:					
					case NONE:	
					default: sql.append("'',"); 
				}												
			}
			else {								
				sql.append(value).append(",");
			}
		}	
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		sqls.add(sql.toString());			
		
		if (!iSQLs.isEmpty()) iSQLs.clear();
		return sqls;
	}
	
	public String composeQuery () {
				
		StringBuilder sql = new StringBuilder("INSERT INTO ").append(tabName).append(" (");
		StringBuilder sqlsuf = new StringBuilder(") SELECT ");
		
		Iterator<Entry<String, Type>> iterator = entries.iterator();
		while (iterator.hasNext()) {
			Entry<String, Type> entry = iterator.next();
			String field = entry.getKey();
			sql.append(field).append(", ");
			
			QueryBridge.Type t = entry.getValue();
			
			switch (t) {
				case MEMO:
				case DATE:
				case STRING: sqlsuf.append("'' AS ").append(field).append(", "); break;
				case DOUBLE:
				case BOOLEAN:
				case INTEGER: sqlsuf.append("0 AS ").append(field).append(", "); break;							
				case NONE: sqlsuf.append("NULL AS ").append(field).append(", "); break;
			}													
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		sqlsuf.deleteCharAt(sql.lastIndexOf(",")).append(")");
		sql.append(sqlsuf).append(" FROM <?>;");	
		return sql.toString();			
	}

}
