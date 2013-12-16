/*
 * xlSqlWriter.java
 *
 * Created on 5 juli 2004, 10:32
 */

package com.nilostep.xlsql.database.sql;

import java.sql.*;

/**
 *
 * @author  Jim Caprioli
 */

public abstract class ASqlSelect {
    
    protected Connection jdbc;
    
    /** Creates a new instance of xlSqlSelect */
    public ASqlSelect(Connection con) {
        jdbc = con;
    }
    
    public abstract ResultSet QueryData(String workbook, String sheet)
                                                            throws SQLException;
}