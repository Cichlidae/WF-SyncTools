/*(Header: NiLOSTEP / xlSQL)

 Copyright (C) 2004 NiLOSTEP
   NiLOSTEP Information Sciences
   http://nilostep.com
   nilo.de.roock@nilostep.com

 This program is free software; you can redistribute it and/or modify it under 
 the terms of the GNU General Public License as published by the Free Software 
 Foundation; either version 2 of the License, or (at your option) any later 
 version.

 This program is distributed in the hope that it will be useful, 
 but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 more details. You should have received a copy of the GNU General Public License 
 along with this program; if not, write to the Free Software Foundation, 
 Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package com.nilostep.xlsql.database.sql;


import java.sql.*;


/**
 * Native xlSQL - ALTER TABLE 
 * 
 * @author Jim Caprioli
 */
public class xlSqlAlterTable implements ICommand {
    protected com.nilostep.xlsql.database.ADatabase db;
    protected String _schema;
    protected String _table;

    /**
     * Creates a new instance of type xlSqlAlterTable.
     * 
     * @param database
     * @param schema
     * @param table
     * @throws NullPointerException DOCUMENT ME!
     */
    public xlSqlAlterTable(com.nilostep.xlsql.database.ADatabase database, 
                           String schema, String table) {
        if ((database != null) && (schema != null) && (table != null)) {
            db = database;
            _schema = schema;
            _table = table;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return always true
     * @throws SQLException
     */
    public boolean execAllowed() throws SQLException {
        return true;
    }

    /**
     * @throws SQLException
     */
    public void execute() throws SQLException {
        db.touchSchema(_schema);
        db.touchTable(_schema, _table);
    }
}