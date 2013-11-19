package com.philipp.tools.best.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IMetaDataExtractor {

	public static final String TABLES = "TABLES";
	public static final String COLUMNS = "COLUMNS";
	public static final String PRIMARY_KEYS = "PRIMARY-KEYS";
	public static final String INDEXES = "INDEXES";
	public static final String FOREIGN_KEYS = "FOREIGH-KEYS";

	public abstract List<String> getTables(String regex) throws SQLException;

	public abstract Map<String, Integer> getColumns(String table) throws SQLException;

	public abstract List<String> getPrimaryKeys(String table) throws SQLException;
	
	public abstract Map<String, String> getIndexes(String table) throws SQLException;
	
	public abstract Map<String, String> getExportedKeys(String table) throws SQLException;

}