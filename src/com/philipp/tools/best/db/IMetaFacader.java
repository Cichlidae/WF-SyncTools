package com.philipp.tools.best.db;

import java.util.Map;

public interface IMetaFacader {

	public Map<String, String> getColumns(Map<String, Integer> meta);
	public Map<String, QueryBridge.Type> getColumnsWithBridgeTypes (Map<String, Integer> meta);
	
}
