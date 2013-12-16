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

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.jconfig.*;
import org.jconfig.event.*;
import org.jconfig.handler.*;


/**
 * Represents an instance of xlSQL
 * 
 * @author Jim Caprioli
 */
public class xlInstanceOLD implements ConfigurationListener {
    private static Logger logger;
    private static xlInstanceOLD instance;
    private static final String XLSQL = "xlsql";
    private String name;
    private File file;
    private XMLFileHandler handler;
    private ConfigurationManager cm;
    private Configuration config;

    //
    private String log;
    private String engine;
    private String database;

    //
    private String driver;
    private String url;
    private String schema;
    private String user;
    private String password;

    /**
     * Creates an xlInstance with the name xlsql
     * 
     * @return xlInstance
     * 
     * @throws xlException [Tbd. When?]
     */
    public static xlInstanceOLD getInstance() throws xlException {
        xlInstanceOLD ret;
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
    public static xlInstanceOLD getInstance(String cfg) throws xlException {
        xlInstanceOLD ret = null;

        if (cfg == null) {
            cfg = XLSQL;
        }

        if (instance == null) {
            ret = new xlInstanceOLD(cfg);
        } else {
            ret = instance;
        }

        return ret;
    }

    private xlInstanceOLD(String cfg) throws xlException {
        logger = Logger.getLogger(this.getClass().getName());
        instance = this;
        //name = cfg;

        try {
//            file = new File(cfg + "_config.xml");
//            handler = new XMLFileHandler();
//            handler.setFile(file);
//
//            cm = ConfigurationManager.getInstance();
//            config = cm.getConfiguration(name);
//            config.addConfigurationListener(this);
//
//            if (file.exists()) {
            	        	        	
            	PropertiesConfiguration config = new PropertiesConfiguration();
            	config.load(this.getClass().getResourceAsStream(cfg + ".properties"));
            	String engine = config.getString("general.engine");
            	            	            	
                //cm.load(handler, name);                
                //Category cat = config.getCategory("general");                                                                
                //engine = config.getProperty("engine", null, "general");
                //config.setCategory(engine, true);
                //logger.info("Configuration file: " + file + " loaded");
                logger.info("Configuration engine: " + engine + " loaded");
//            } else {
            
                //
//                assert (config.isNew());
//
//                //
//                config.setCategory("general", true);
//
//                String engine = "hsqldb";
//                setLog("xlsql.log");
//                setDatabase(System.getProperty("user.dir"));
//
//                //
//                this.engine = engine;
//                this.config.setProperty("engine", engine);
//                addEngine(engine);
//                config.setCategory(engine, true);
//
//
//                //
//                setDriver("org.hsqldb.jdbcDriver");
//                setUrl("jdbc:hsqldb:.");
//                setSchema("");
//                setUser("sa");
//                setPassword("");
//                config.setCategory(getEngine(), true);
//                logger.info("Configuration file: " + file + " created.");
//            }
        } 
        //catch (ConfigurationManagerException cme) {
        //   config = cm.getConfiguration(name);
        //} 
        catch (ConfigurationException e) {		
			e.printStackTrace();
			throw new xlException(e.getMessage());
		}           

        try {
            if (getLog() == null) {
                setLog("xlsql.log");
            }

            boolean append = true;
            FileHandler loghandler = new FileHandler(getLog(), append);
            loghandler.setFormatter(new SimpleFormatter());
            logger.addHandler(loghandler);
        } catch (IOException e) {
            throw new xlException("error while creating logfile");
        }

        //logger.info("Instance created with engine " + getEngine());
        logger.info("Instance created with engine " + engine);

        //
        //
        //
    }

    /**
     * get log property
     * 
     * @return log
     */
    public String getLog() {
        return config.getProperty("log", "xlsql.log", "general");
    }

    /**
     * get log property
     * 
     * @return log
     */
    public String getDatabase() {
        return config.getProperty("database", System.getProperty("user.dir"), 
                                  "general");
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
            throw new IllegalArgumentException(); //desc
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

     //   try {
          //  ret = xlDatabaseFactory.createDatabase(new File(getDatabase()), 
           //                                        this);
     //   } catch (xlDatabaseException xde) {
      //      throw new xlException(xde.getMessage());
       // }

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
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public String[] getEngines() {
        return config.getCategoryNames();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param engine DOCUMENT ME!
     */
    public void addEngine(java.lang.String engine) {
        this.engine = engine;
        config.setCategory(engine, true);
        config.setProperty("engine", engine, "general");


        //
        setDriver("");
        setUrl("");
        setSchema("");
        setUser("");
        setPassword("");
    }

    /**
     * DOCUMENT ME!
     * 
     * @param engine DOCUMENT ME!
     */
    public void removeEngine(java.lang.String engine) {
        if (engine.equalsIgnoreCase(engine)) {
            config.removeCategory(engine);
        }
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
        ret = config.getProperty("engine", null, "general");

        if (ret == null) {
            throw new IllegalStateException("Cannot load engine info"); //desc
        }

        return ret;
    }

    /**
     * get jdbc driver of sql engine
     * 
     * @return driver
     */
    public String getDriver() {
        return config.getProperty("driver", engine);
    }

    /**
     * get url of sql engine
     * 
     * @return url
     */
    public String getUrl() {
        return config.getProperty("url", engine);
    }

    /**
     * get schema, database, initial context of sql engine
     * 
     * @return schema
     */
    public String getSchema() {
        return config.getProperty("schema", engine);
    }

    /**
     * get user of sql engine
     * 
     * @return user
     */
    public String getUser() {
        return config.getProperty("user", engine);
    }

    /**
     * get password of sql engine user
     * 
     * @return password
     */
    public String getPassword() {
        return config.getProperty("password", engine);
    }

    /**
     * get logger
     * 
     * @return handle to instance logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * set log property
     * 
     * @param log location of xlsql logfile
     */
    public void setLog(String log) {
        this.log = log;
        config.setProperty("log", log, "general");
    }

    /**
     * set engine
     * 
     * @param newengine any of the supported sql engines
     * 
     * @throws xlException DOCUMENT ME!
     */
    public void setEngine(String newengine) throws xlException {
        String[] engines = getEngines();
        boolean found = false;

        for (int i = 0; i < engines.length; i++) {
            if (newengine.equalsIgnoreCase(engines[i])) {
                found = true;

                break;
            }
        }

        if (found) {
            this.engine = newengine;
            config.setProperty("engine", newengine, "general");
            config.setCategory(newengine, true);
        } else {
            throw new xlException("Engine " + newengine + "..?! Verify.");
        }
    }

    /**
     * set database
     * 
     * @param database path to database
     */
    public void setDatabase(String database) {
        this.database = database;
        config.setProperty("database", database, "general");
    }

    /**
     * set driver
     * 
     * @param driver java class of driver of sql engine
     */
    public void setDriver(String driver) {
        this.driver = driver;
        config.setCategory(engine, true);
        config.setProperty("driver", driver, engine);
    }

    /**
     * set url
     * 
     * @param url required url for connecting to sql engine
     */
    public void setUrl(String url) {
        this.url = url;
        config.setCategory(engine, true);
        config.setProperty("url", url, engine);
    }

    /**
     * set schema
     * 
     * @param schema initial schema after connect to sql engine
     */
    public void setSchema(String schema) {
        this.schema = schema;
        config.setCategory(engine, true);
        config.setProperty("schema", schema, engine);
    }

    /**
     * set user
     * 
     * @param user user which connects to sql engine
     */
    public void setUser(String user) {
        this.user = user;
        config.setCategory(engine, true);
        config.setProperty("user", user, engine);
    }

    /**
     * set password
     * 
     * @param password password of sql engine user
     */
    public void setPassword(String password) {
        this.password = password;
        config.setCategory(engine, true);
        config.setProperty("password", password, engine);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param e DOCUMENT ME!
     */
    public void configurationChanged(org.jconfig.event.ConfigurationChangedEvent e) {
        saveConfig();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param categoryChangedEvent DOCUMENT ME!
     */
    public void categoryChanged(org.jconfig.event.CategoryChangedEvent categoryChangedEvent) {
        saveConfig();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param propertyChangedEvent DOCUMENT ME!
     */
    public void propertyChanged(org.jconfig.event.PropertyChangedEvent propertyChangedEvent) {
        saveConfig();
    }

    private void saveConfig() {
        try {
            if (config.isNew()) {
                cm.save(handler, config);
            } else {
                cm.save(name);
                cm.save(handler, config);
            }
        } catch (ConfigurationManagerException cfe) {
            logger.warning("xlSQL> -WRN: ConfigurationManagerException ..?");
        }

        config.resetCreated();
    }
}

