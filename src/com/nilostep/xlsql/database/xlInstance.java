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
package com.nilostep.xlsql.database;

import java.io.*;

import java.sql.*;

import java.util.logging.*;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


/**
 * Represents an instance of xlSQL
 * 
 * @author Jim Caprioli 
 * @changed Anna Semyonova, PremaServices.com, 2013
 */
public class xlInstance {
    private static Logger logger;
    private static xlInstance instance;
    private static final String XLSQL = "xlsql";
    private Configuration config;

    private String engine;
    
    /**
     * Creates an xlInstance with the name xlsql
     * 
     * @return xlInstance
     * 
     * @throws xlException [Tbd. When?]
     */
    public static xlInstance getInstance() throws xlException {
        xlInstance ret;
        ret = getInstance(XLSQL);

        return ret;
    }

    /**
     */
    public static void disconnect() {
        instance = null;
    }

    /**
     * Creates an xlInstance
     * 
     * @param cfg name of configuration [cfg]_config.xml on disk
     * 
     * @return xlInstance
     * 
     * @throws xlException [Tbd. When?]
     */
    public static xlInstance getInstance(String cfg) throws xlException {
        xlInstance ret = null;

        if (cfg == null) {
            cfg = XLSQL;
        }

        if (instance == null) {
            ret = new xlInstance(cfg);
        } else {
            ret = instance;
        }

        return ret;
    }

    private xlInstance(String cfg) throws xlException {
        logger = Logger.getLogger(this.getClass().getName());
        instance = this;    

        try {            	        	        
            	PropertiesConfiguration config = new PropertiesConfiguration();
            	config.load(this.getClass().getResourceAsStream(cfg + ".properties"));
            	this.config = config;
            	
            	engine = config.getString("general.engine");
            	            	            	             
                logger.info("Configuration engine: " + engine + " loaded");
        }
        catch (ConfigurationException e) {		
			e.printStackTrace();
			throw new xlException(e.getMessage());
		}           

        try {
            boolean append = true;
            FileHandler loghandler = new FileHandler(getLog(), append);
            loghandler.setFormatter(new SimpleFormatter());
            logger.addHandler(loghandler);
        } catch (IOException e) {
            throw new xlException("error while creating logfile");
        }
   
        logger.info("Instance created with engine " + getEngine());
    }

    /**
     * get log property
     * 
     * @return log
     */
    public String getLog() {
    	return config.getString("general.log", "xlsql.log");    	
    }

    /**
     * get log property
     * 
     * @return log
     */
    public String getDatabase() {
    	return config.getString("general.database", System.getProperty("user.dir"));    	   
    }

    /**
     * Excel database exporter
     * 
     * @param dir Path to database
     * 
     * @return Database exporter
     * 
     * @throws xlException When an error occurs
     * @throws IllegalArgumentException When dir is null or invalid
     */
    public AExporter getExporter(String dir) throws xlException {
        AExporter ret;

        if (dir != null) {
            File f = new File(dir);

            try {
                ret = xlDatabaseFactory.createExporter(f);
            } catch (xlDatabaseException xde) {
                throw new xlException("xlSQL/db reports '" + xde.getMessage()
                                      + "'");
            }

            ;
        } else {
            throw new IllegalArgumentException();
        }

        return ret;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @throws xlException DOCUMENT ME!
     */
    public ADatabase getXlDatabase() throws xlException {
        ADatabase ret = null;

        try {
            ret = xlDatabaseFactory.createDatabase(new File(getDatabase()), 
                                                   this);
        } catch (xlDatabaseException xde) {
            throw new xlException(xde.getMessage());
        }

        return ret;
    }

    /**
     * get java.sql.Connection to engine
     * 
     * @return Connection
     */
    public Connection connect() {
        Connection ret = null;

        try {
            String classname = this.getDriver();
            logger.info("=> loading driver: " + classname);

            Driver d = (Driver) Class.forName(classname).newInstance();
            logger.info("OK. " + classname + " loaded.");
            logger.info("=> registering driver: " + classname);
            DriverManager.registerDriver(new xlEngineDriver(d));
            logger.info("OK. ");

            String url = getUrl();
            String user = getUser();
            String password = getPassword();
            logger.info("=> connecting to: " + user + "/" + password + "@"
                        + url);
            ret = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException nfe) {
            logger.warning("Driver not found. Classpath set?");
        } catch (InstantiationException ie) {
            logger.warning("Error while instantiating driver class. ..?");
        } catch (IllegalAccessException iae) {
            logger.warning("Illegal access. Have sources been modified?");
        } catch (SQLException sqe) {
            logger.warning("java.sql package reports: '" + sqe.getMessage()
                           + ":" + sqe.getSQLState() + "' ..?");
        }

        return ret;
    }

    /**
     * get engine property
     * 
     * @return engine
     * 
     * @throws IllegalStateException DOCUMENT ME!
     */
    public String getEngine() {
        String ret;
        ret = config.getString("general.engine");

        if (ret == null) {
            throw new IllegalStateException("Cannot load engine info"); 
        }

        return ret;
    }

    /**
     * get jdbc driver of sql engine
     * 
     * @return driver
     */
    public String getDriver() {    	    	
        return config.getString(engine + ".driver");
    }

    /**
     * get url of sql engine
     * 
     * @return url
     */
    public String getUrl() {
    	 return config.getString(engine + ".url");     
    }

    /**
     * get schema, database, initial context of sql engine
     * 
     * @return schema
     */
    public String getSchema() {
    	 return config.getString(engine + ".schema");
    }

    /**
     * get user of sql engine
     * 
     * @return user
     */
    public String getUser() {
    	return config.getString(engine + ".user");  
    }

    /**
     * get password of sql engine user
     * 
     * @return password
     */
    public String getPassword() {
    	return config.getString(engine + ".password");       
    }

    /**
     * get logger
     * 
     * @return handle to instance logger
     */
    public Logger getLogger() {
        return logger;
    }
}

