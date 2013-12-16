/*(Header: NiLOSTEP / xlSQL)

 Copyright (C) 2004 NiLOSTEP
   NiLOSTEP Information Sciences
   http://nilostep.com
   nilo.de.roock@nilostep.com

 This program is free software; you can redistribute it and/or modify it 
 under the terms of the GNU General Public License as published by the Free 
 Software Foundation; either version 2 of the License, or (at your option) 
 any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 more details. You should have received a copy of the GNU General Public 
 License along with this program; if not, write to the Free Software 
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package com.nilostep.xlsql.jdbc;

import com.nilostep.xlsql.database.xlEngineDriver;
import com.nilostep.xlsql.database.xlException;
import com.nilostep.xlsql.database.xlInstance;

import java.sql.*;
import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;


/**
 * xlDriver the main 'JDBC Driver' class, implements java.sql.Driver
 * 
 * @version $Revision: 1.10 $
 * @author $author$
 */
public class xlDriver implements Driver {
    private static final String PREFIX = "jdbc:nilostep:excel:";
    private static final int MAJOR_VERSION = 0;
    private static final int MINOR_VERSION = 0;
    private static final boolean JDBC_COMPLIANT = false;
    Logger logger;

    static {
        try {
            DriverManager.registerDriver(new xlDriver());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Checks if a url can be servived by this driver
     * 
     * @param url jdbc url
     * 
     * @return true if url starts with jdbc:nilostep:excel:
     * 
     * @throws SQLException when url is null
     */
    public boolean acceptsURL(String url) throws SQLException {
        if (url != null) {
            return (url.startsWith(PREFIX));
        } else {
            throw new SQLException("Invalid url");
        }
    }

    /**
     * Establish connection
     * 
     * @param url JDBC url
     * @param info not used by xlSQL
     * 
     * @return JDBC Connection to database
     * 
     * @throws SQLException If the url does not contain a valid database path
     */
    public Connection connect(String url, Properties info)
                       throws SQLException {
                           
        xlConnection ret = null;
        try {
            //
            // Connect to xlInstance
            String config = info.getProperty("config");
            xlInstance instance = xlInstance.getInstance(config);
            logger = instance.getLogger();
            
            //
            // Connect to SQL engine

            DriverManager.deregisterDriver(this);
            String classname = "com.mysql.jdbc.Driver";
            Driver d = (Driver) Class.forName(classname).newInstance();
            DriverManager.registerDriver(new xlEngineDriver(d));

            classname = "org.hsqldb.jdbcDriver";
            d = (Driver) Class.forName(classname).newInstance();
            DriverManager.registerDriver(new xlEngineDriver(d));
            
            classname = instance.getDriver();
            String eng_url = instance.getUrl();
            String eng_sch = instance.getSchema();
            String eng_usr = instance.getUser();
            String eng_pwd = instance.getPassword();

            Connection c = DriverManager.getConnection(eng_url, eng_usr, 
                                                       eng_pwd);
            DriverManager.registerDriver(this);
            //
            // Create a connection to xlSQL
            String database = instance.getDatabase();
            if (url.length() == PREFIX.length()) {
                if ((database == null) || (database.length() == 0)) {
                    url = PREFIX + System.getProperty("user.dir");
                } else {
                    url = PREFIX + database;
                }
            }
            ret = xlConnection.factory(url, c, eng_sch);
            String cName = c.getMetaData().getDatabaseProductName();
            logger.info("Connection to " + cName + " established.");
            
        } catch (xlException xe) {
            throw new SQLException(xe.getMessage()); 
        } catch (SQLException sqe) {
            throw sqe; 
        } catch (Exception e) {
        	e.printStackTrace();
            throw new SQLException(e.getMessage());             
        }

        return ret;
    }

    /**
     * Supplies Major Version
     * 
     * @return Major Version
     */
    public int getMajorVersion() {
        return MAJOR_VERSION;
    }

    /**
     * Supplies Minor Version
     * 
     * @return Minor Version
     */
    public int getMinorVersion() {
        return MINOR_VERSION;
    }

    /**
     * ( Required for generic tool bakers )
     * 
     * @param url JDBC url
     * @param info name of xlsql configuration
     * 
     * @return ( ? )
     */
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) {
        DriverPropertyInfo[] pinfo = new DriverPropertyInfo[1];
        DriverPropertyInfo p;

        p = new DriverPropertyInfo("config", null);
        p.description = "configuration";
        p.required = false;
        pinfo[0] = p;

        return pinfo;
    }

    /**
     * Supplies JDBC compliancy level ( true or false )
     * 
     * @return xlSQL reports always false
     */
    public boolean jdbcCompliant() {
        return JDBC_COMPLIANT;
    }
}

