/*
 * x l S Q L  
 * (c) Jim Caprioli, NiLOSTEP.com
 * See xlSQL-license.txt for license details
 *
 */
package com.nilostep.xlsql.jdbc;

import java.sql.*;


public class xlParameterMetaData implements ParameterMetaData {
    private xlPreparedStatement xlPstm;
    private ParameterMetaData dbPsMeta;
    
    //~ Constructors �����������������������������������������������������������

    /** Creates a new instance of ParameterMetaData */
    protected xlParameterMetaData(xlPreparedStatement pstm, 
                                            ParameterMetaData psmeta) {
        xlPstm = pstm;
        dbPsMeta = psmeta;
    }

    //~ Methods ����������������������������������������������������������������

    /**
    * Implements method in interface java.sql.ParameterMetaData
    * @see java.sql.ParameterMetaData#getParameterClassName
    */
    public String getParameterClassName(int param) throws SQLException {
        return dbPsMeta.getParameterClassName(param);
    }

    /**
    * Implements method in interface java.sql.ParameterMetaData
    * @see java.sql.ParameterMetaData#getParameterCount
    */
    public int getParameterCount() throws SQLException {
        return dbPsMeta.getParameterCount();
    }

    /**
    * Implements method in interface java.sql.ParameterMetaData
    * @see java.sql.ParameterMetaData#getParameterMode
    */
    public int getParameterMode(int param) throws SQLException {
        return dbPsMeta.getParameterCount();
    }

    /**
    * Implements method in interface java.sql.ParameterMetaData
    * @see java.sql.ParameterMetaData#getParameterType
    */
    public int getParameterType(int param) throws SQLException {
        return dbPsMeta.getParameterType(param);
    }

    /**
    * Implements method in interface java.sql.ParameterMetaData
    * @see java.sql.ParameterMetaData#getParameterTypeName
    */
    public String getParameterTypeName(int param) throws SQLException {
        return dbPsMeta.getParameterTypeName(param);
    }

    /**
    * Implements method in interface java.sql.ParameterMetaData
    * @see java.sql.ParameterMetaData#getPrecision
    */
    public int getPrecision(int param) throws SQLException {
        return dbPsMeta.getPrecision(param);
    }

    /**
    * Implements method in interface java.sql.ParameterMetaData
    * @see java.sql.ParameterMetaData#getScale
    */
    public int getScale(int param) throws SQLException {
        return dbPsMeta.getScale(param);
    }

    /**
    * Implements method in interface java.sql.ParameterMetaData
    * @see java.sql.ParameterMetaData#isNullable
    */
    public int isNullable(int param) throws SQLException {
        return dbPsMeta.isNullable(param);
    }

    /**
    * Implements method in interface java.sql.ParameterMetaData
    * @see java.sql.ParameterMetaData#isSigned
    */
    public boolean isSigned(int param) throws SQLException {
        return dbPsMeta.isSigned(param);
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