package com.philipp.tools.best.db;

import java.util.HashMap;
import java.util.Map;

public class XBaseMetaFacader implements IMetaFacader {
		
	protected static final int VARCHAR_T = 1;
	protected static final int NUMERIC_T = 2;
	protected static final int LOGICAL_T = -7;
	protected static final int MEMO_T = -1;
	protected static final int DATE_T = 91;
	protected static final int UNKNOWN_T = 999;

	@Override
	public Map<String, String> getColumns(Map<String, Integer> meta) {		
		
		Map<String, String> facadeMap = new HashMap<String, String>(meta.size());
		
		for (String column : meta.keySet()) {
			int type = meta.get(column);
			
			switch (type) {
				case VARCHAR_T: facadeMap.put(column, "Character"); break;
				case NUMERIC_T: facadeMap.put(column, "Numeric"); break;
				case LOGICAL_T: facadeMap.put(column, "Logical"); break;
				case MEMO_T: facadeMap.put(column, "Memo"); break;
				case DATE_T: facadeMap.put(column, "Date"); break;
				default: facadeMap.put(column, "<?>"); break;	
			}		
		}						
		return facadeMap;
	}
	
	public Map<String, QueryBridge.Type> getColumnsWithBridgeTypes (Map<String, Integer> meta) {
		
		Map<String, QueryBridge.Type> facadeMap = new HashMap<String, QueryBridge.Type>(meta.size());
		
		for (String column : meta.keySet()) {
			int type = meta.get(column);
			
			switch (type) {
				case VARCHAR_T: facadeMap.put(column, QueryBridge.Type.STRING); break;
				case NUMERIC_T: facadeMap.put(column, QueryBridge.Type.DOUBLE); break;
				case LOGICAL_T: facadeMap.put(column, QueryBridge.Type.BOOLEAN); break;
				case MEMO_T: facadeMap.put(column, QueryBridge.Type.MEMO); break;
				case DATE_T: facadeMap.put(column, QueryBridge.Type.DATE); break;
				default: facadeMap.put(column, QueryBridge.Type.NONE); break;	
			}		
		}						
		return facadeMap;	
	}

}
