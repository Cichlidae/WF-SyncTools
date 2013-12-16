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
 * Command as in the 'Command Pattern'
 *
 * @version $Revision: 1.1 $
 * @author $author$
 */
public interface ICommand {
    //~ Methods ································································

    boolean execAllowed() throws SQLException;

    void execute() throws SQLException;
}