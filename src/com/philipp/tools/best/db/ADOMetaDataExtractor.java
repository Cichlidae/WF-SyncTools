package com.philipp.tools.best.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jacob.com.Variant;
import com.jacob.impl.ado.Connection;
import com.jacob.impl.ado.Field;
import com.jacob.impl.ado.Fields;
import com.jacob.impl.ado.Recordset;
import com.jacob.impl.ado.SchemaEnum;

public class ADOMetaDataExtractor implements IMetaDataExtractor {
	
	private Connection connection;
	
	public ADOMetaDataExtractor(Connection connection) {
		this.connection = connection;
	}
	
	public List<String> getTables (String regex) {
		
		List<String> r = new ArrayList<String>();	
				
		Recordset rs = new Recordset(connection.OpenSchema(SchemaEnum.adSchemaTables).toDispatch());		
		Fields fs = rs.getFields();
		
		if (!rs.getEOF()) rs.MoveFirst();
		while (!rs.getEOF()) {
			Field f = fs.getItem(2);
	        Variant v = f.getValue();
	        r.add(v.getString());	      
	        rs.MoveNext();
		}
		return r;
	}

	@Override
	public Map<String, Integer> getColumns(String table) {
		throw new UnsupportedOperationException("ADOMetaDataExtractor.getColumns");
	}

	@Override
	public List<String> getPrimaryKeys(String table) {
		throw new UnsupportedOperationException("ADOMetaDataExtractor.getPrimaryKeys");
	}

	@Override
	public Map<String, String> getIndexes(String table) throws SQLException {
		throw new UnsupportedOperationException("ADOMetaDataExtractor.getIndexes");
	}

	@Override
	public Map<String, String> getExportedKeys(String table) throws SQLException {
		throw new UnsupportedOperationException("ADOMetaDataExtractor.getExportedKeys");
	}

}
