/*
 * x l S Q L  
 * (c) Jim Caprioli, NiLOSTEP.com
 * See xlSQL-license.txt for license details
 *
 */
package com.nilostep.xlsql.jdbc;

import java.sql.*;


public class xlResultSetMetaData implements java.sql.ResultSetMetaData {

    private ResultSetMetaData dbRsMeta;
    private xlResultSet xlRs;
    //~ Constructors �����������������������������������������������������������

    /**
    * Constructs a new StatementImpl object.
    *
    */
    public xlResultSetMetaData(xlResultSet rs, ResultSetMetaData rsmeta) {
        xlRs = rs;
        dbRsMeta = rsmeta;
    }

    //~ Methods ����������������������������������������������������������������

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getCatalogName
    */
    public String getCatalogName(int column) throws java.sql.SQLException {
        return dbRsMeta.getCatalogName(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getColumnClassName
    */
    public String getColumnClassName(int column) throws java.sql.SQLException {
        return dbRsMeta.getColumnClassName(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getColumnCount
    */
    public int getColumnCount() throws java.sql.SQLException {
        return dbRsMeta.getColumnCount();
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getColumnDisplaySize
    */
    public int getColumnDisplaySize(int column) throws java.sql.SQLException {
        return dbRsMeta.getColumnDisplaySize(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getColumnLabel
    */
    public String getColumnLabel(int column) throws java.sql.SQLException {
        return dbRsMeta.getColumnLabel(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getColumnName
    */
    public String getColumnName(int column) throws java.sql.SQLException {
        return dbRsMeta.getColumnName(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getColumnType
    */
    public int getColumnType(int column) throws java.sql.SQLException {
        return dbRsMeta.getColumnType(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getColumnTypeName
    */
    public String getColumnTypeName(int column) throws java.sql.SQLException {
        return dbRsMeta.getColumnTypeName(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getPrecision
    */
    public int getPrecision(int column) throws java.sql.SQLException {
        return dbRsMeta.getPrecision(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getScale
    */
    public int getScale(int column) throws java.sql.SQLException {
        return dbRsMeta.getScale(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getSchemaName
    */
    public String getSchemaName(int column) throws java.sql.SQLException {
        return dbRsMeta.getSchemaName(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#getTableName
    */
    public String getTableName(int column) throws java.sql.SQLException {
        return dbRsMeta.getTableName(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#isAutoIncrement
    */
    public boolean isAutoIncrement(int column) throws java.sql.SQLException {
        return dbRsMeta.isAutoIncrement(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#isCaseSensitive
    */
    public boolean isCaseSensitive(int column) throws java.sql.SQLException {
        return dbRsMeta.isCaseSensitive(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#isCurrency
    */
    public boolean isCurrency(int column) throws java.sql.SQLException {
        return dbRsMeta.isCurrency(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#isDefinitelyWritable
    */
    public boolean isDefinitelyWritable(int column) throws java.sql.SQLException {
        return dbRsMeta.isDefinitelyWritable(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#isNullable
    */
    public int isNullable(int column) throws java.sql.SQLException {
        return dbRsMeta.isNullable(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#isReadOnly
    */
    public boolean isReadOnly(int column) throws java.sql.SQLException {
        return dbRsMeta.isReadOnly(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#isSearchable
    */
    public boolean isSearchable(int column) throws java.sql.SQLException {
        return dbRsMeta.isSearchable(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#isSigned
    */
    public boolean isSigned(int column) throws java.sql.SQLException {
        return dbRsMeta.isSigned(column);
    }

    /**
    * Implements method in interface java.sql.ResultSetMetaData
    * @see java.sql.ResultSetMetaData#isWritable
    */
    public boolean isWritable(int column) throws java.sql.SQLException {
        return dbRsMeta.isWritable(column);
    }

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}