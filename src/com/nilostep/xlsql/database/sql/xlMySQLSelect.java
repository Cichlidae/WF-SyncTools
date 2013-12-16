/*
 * xlQueryHsqldbForExcel.java
 *
 * Created on 9 juli 2004, 13:20
 */

package com.nilostep.xlsql.database.sql;

import java.sql.*;

/**
 *
 * @author  Jim Caprioli
 */
public class xlMySQLSelect extends ASqlSelect {
    
    
    /** Creates a new instance of xlMySQLSelect */
    public xlMySQLSelect(Connection con) {
        super(con);
    }
    
    public ResultSet QueryData(String workbook, String sheet) 
                                                        throws SQLException {
        Statement s = jdbc.createStatement();
        String sql = "SELECT * FROM " + workbook + "." + sheet;
        return s.executeQuery(sql);
    }
}