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
public class xlSqlNull implements ICommand {
    //~ Instance variables ·····················································


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
        ;
    }
}