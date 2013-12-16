/*(Header: NiLOSTEP / xlSQL)

    Copyright (C) 2004 NiLOSTEP Information Sciences, all 
    rights reserved.
    
    This program is licensed under the terms of the GNU 
    General Public License.You should have received a copy 
    of the GNU General Public License along with this 
    program;
*/

package com.nilostep.xlsql.database.sql;

import java.sql.*;

/**
 * DOCUMENT ME!
 * 
 * @author Jim Caprioli
 */
public class xlSqlDelete implements ICommand {
    //~ Instance variables ·····················································

    protected com.nilostep.xlsql.database.ADatabase db;
    protected String _schema;
    protected String _table;

    /**
     * Creates a new instance of type xlSqlDelete.
     *
     *
     * @param database 	
     * @param schema 	
     * @param table 	
     */
    public xlSqlDelete(com.nilostep.xlsql.database.ADatabase database, String schema, String table) {
        if (database == null) {
            throw new NullPointerException("xlSQL: database null");
        } else {
            db = database;
        }

        if (schema == null) {
            throw new NullPointerException("xlSQL: schema null");
        } else {
            _schema = schema;
        }

        if (table == null) {
            throw new NullPointerException("xlSQL: table null");
        } else {
            _table = table;
        }
    }

    /**
     * TODO: javadoc
     *
     * @return true if allowed
     *
     * @throws SQLException
     */
    public boolean execAllowed() throws SQLException {
        boolean ret = true;
        return ret;
    }

    /**
     * TODO: javadoc
     *
     * @throws SQLException
     */
    public void execute() throws SQLException {
        db.touchSchema(_schema);
        db.touchTable(_schema, _table);
    }
}