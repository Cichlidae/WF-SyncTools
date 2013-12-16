/*(Header: NiLOSTEP / xlSQL)

    Copyright (C) 2004 NiLOSTEP Information Sciences, all 
    rights reserved.
    
    This program is licensed under the terms of the GNU 
    General Public License.You should have received a copy 
    of the GNU General Public License along with this 
    program;
*/

/*
 * xlConnectionHSQL.java
 *
 * Created on 5 juli 2004, 23:59
 */
package com.nilostep.xlsql.jdbc;

import com.nilostep.xlsql.database.sql.xlSqlSelectFactory;
import com.nilostep.xlsql.database.sql.xlSqlParserFactory;
import com.nilostep.xlsql.database.export.xlSqlFormatterFactory;
import com.nilostep.xlsql.database.*;

import java.sql.*;
import java.util.*;

/**
 * DOCUMENT ME!
 * 
 * @author Jim Caprioli
 */
public class xlConnectionMySQL extends xlConnection {

    private static final String MYSQL = "mysql";
    private String context;

    public xlConnectionMySQL(String url, Connection c, 
                             String schema) throws SQLException {
        dialect = "mysql";
        context = schema;
        URL = url;
        w = xlSqlFormatterFactory.create(MYSQL);
        dbCon = c; //? Stack Overflow ?
        query = xlSqlSelectFactory.create(MYSQL, dbCon);
        startup();
        xlsql = xlSqlParserFactory.create(MYSQL, datastore, context);
    }

    //~ Methods ����������������������������������������������������������������

    public void shutdown() throws Exception {
        if (!closed) {
            logger.info("Executing MySQL clean-up...");
            String[] schemas = datastore.getSchemas();
            Statement stm = dbCon.createStatement();
            stm.execute("USE " + context);
            for (int i = 0; i < schemas.length; i++) {
                stm.execute("DROP DATABASE " + schemas[i]);
            }
            dbCon.close();
            closed = true;
            logger.info("MySQL clean-up done");
        }
    }

	@Override
	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
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