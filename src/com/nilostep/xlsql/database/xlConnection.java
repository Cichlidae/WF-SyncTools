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
package com.nilostep.xlsql.database;

import java.sql.*;


/**
 * Creates a JDBC Connection to xlSQL
 * 
 * @version $Revision: 1.6 $
 * @author $author$
 */
public class xlConnection {
    /**
     * @param database Path to database directory
     *
     * @return JDBC Connection
     *
     * @throws SQLException
     * @throws xlException
     */
    public static Connection create(String database) throws SQLException, 
                                                            xlException {
        try {
            String driver = "com.nilostep.xlsql.jdbc.xlDriver";
            Driver d = (Driver) Class.forName(driver).newInstance();
            String protocol = "jdbc:nilostep:excel";
            String url = protocol + ":" + database;
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException nfe) {
            throw new xlException("driver not found. Classpath set ?");
        } catch (InstantiationException ie) {
            throw new xlException("ERR: while instantiating. ???");
        } catch (IllegalAccessException iae) {
            throw new xlException("ERR: illegal access. Privileges?");
        } catch (Exception e) {
            throw new xlException(e.getMessage());
        }
    }
}