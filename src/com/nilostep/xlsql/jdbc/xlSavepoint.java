/*
 * x l S Q L  
 * (c) Jim Caprioli, NiLOSTEP.com
 * See xlSQL-license.txt for license details
 *
 */
package com.nilostep.xlsql.jdbc;

import java.sql.*;


public class xlSavepoint implements Savepoint {

    xlConnection xlCon;
    Savepoint dbSave;
    
    //~ Constructors ···························································

    /** Creates a new instance of SavepointImpl */
    public xlSavepoint(xlConnection con, Savepoint save) {
        xlCon = con;
        dbSave = save;
    }

    //~ Methods ································································

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.Savepoint#getSavepointId
    */
    public int getSavepointId() throws SQLException {
        return dbSave.getSavepointId();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.Savepoint#getSavepointName
    */
    public String getSavepointName() throws SQLException {
        return dbSave.getSavepointName();
    }
}