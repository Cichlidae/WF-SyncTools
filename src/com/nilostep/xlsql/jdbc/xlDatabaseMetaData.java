/*
 * x l S Q L  
 * (c) Jim Caprioli, NiLOSTEP.com
 * See xlSQL-license.txt for license details
 *
 */
package com.nilostep.xlsql.jdbc;

import java.sql.*;

public class xlDatabaseMetaData implements DatabaseMetaData, Constants {
    //~ Static variables/initializers ������������������������������������������

    private xlConnection xlCon;
    private DatabaseMetaData dbMeta;

    public static final int NOT_SUPPORTED = -1;

    //~ Constructors �����������������������������������������������������������

    /** Creates a new instance of DatabaseMetaDataImpl */
    protected xlDatabaseMetaData(xlConnection con, DatabaseMetaData meta) {
        xlCon = con;
        dbMeta = meta;
    }

    //~ Methods ����������������������������������������������������������������

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#allProceduresAreCallable
    */
    public boolean allProceduresAreCallable() throws SQLException {
        return dbMeta.allProceduresAreCallable();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#allTablesAreSelectable
    */
    public boolean allTablesAreSelectable() throws SQLException {
        return dbMeta.allTablesAreSelectable();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#dataDefinitionCausesTransactionCommit
    */
    public boolean dataDefinitionCausesTransactionCommit()
                                                  throws SQLException {
        return dbMeta.dataDefinitionCausesTransactionCommit();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#dataDefinitionIgnoredInTransactions
    */
    public boolean dataDefinitionIgnoredInTransactions()
                                                throws SQLException {
        return dbMeta.dataDefinitionIgnoredInTransactions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#deletesAreDetected
    */
    public boolean deletesAreDetected(int type) throws SQLException {
        return dbMeta.deletesAreDetected(type);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#doesMaxRowSizeIncludeBlobs
    */
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return dbMeta.doesMaxRowSizeIncludeBlobs();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getAttributes
    */
    public ResultSet getAttributes(String catalog, String schemaPattern, 
                                   String typeNamePattern, 
                                   String attributeNamePattern)
                            throws SQLException {
        return dbMeta.getAttributes(catalog, schemaPattern, 
                        typeNamePattern, attributeNamePattern);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getBestRowIdentifier
    */
    public ResultSet getBestRowIdentifier(String catalog, String schema, 
                                          String table, int scope, 
                                          boolean nullable)
                                   throws SQLException {
        return dbMeta.getBestRowIdentifier(catalog, schema, 
                                            table, scope, nullable);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getCatalogSeparator
    */
    public String getCatalogSeparator() throws SQLException {
//NiLOSTEP...
//  [ ISSUE ]        
        return dbMeta.getCatalogSeparator();
//End        
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getCatalogTerm
    */
    public String getCatalogTerm() throws SQLException {
//NiLOSTEP...
// [ ISSUE ]
        return dbMeta.getCatalogTerm();
//End        
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getCatalogs
    */
    public ResultSet getCatalogs() throws SQLException {
//NiLOSTEP...
// [ ISSUE ]        
        return dbMeta.getCatalogs();
// End        
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getColumnPrivileges
    */
    public ResultSet getColumnPrivileges(String catalog, String schema, 
                                         String table, String columnNamePattern)
                                  throws SQLException {
        return dbMeta.getColumnPrivileges(catalog, schema, 
                                            table, columnNamePattern);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getColumns
    */
    public ResultSet getColumns(String catalog, String schemaPattern, 
                                String tableNamePattern, 
                                String columnNamePattern)
                         throws SQLException {
        return dbMeta.getColumns(catalog, schemaPattern, 
                                tableNamePattern, columnNamePattern);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getConnection
    */
    public Connection getConnection() throws SQLException {
        return xlCon;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getCrossReference
    */
    public ResultSet getCrossReference(String primaryCatalog, 
                                       String primarySchema, 
                                       String primaryTable, 
                                       String foreignCatalog, 
                                       String foreignSchema, 
                                       String foreignTable)
                                throws SQLException {
        return dbMeta.getCrossReference(primaryCatalog, primarySchema, 
                    primaryTable, foreignCatalog, foreignSchema, foreignTable);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getDatabaseMajorVersion
    */
    public int getDatabaseMajorVersion() throws SQLException {
        return MAJOR_XLSQL_VERSION;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getDatabaseMinorVersion
    */
    public int getDatabaseMinorVersion() throws SQLException {
        return MINOR_XLSQL_VERSION;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getDatabaseProductName
    */
    public String getDatabaseProductName() throws SQLException {
        return XLSQL;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getDatabaseProductVersion
    */
    public String getDatabaseProductVersion() throws SQLException {
        return XLSQL_RELEASE;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getDefaultTransactionIsolation
    */
    public int getDefaultTransactionIsolation() throws SQLException {
        return dbMeta.getDefaultTransactionIsolation();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getDriverMajorVersion
    */
    public int getDriverMajorVersion() {
        return MAJOR_VERSION;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getDriverMinorVersion
    */
    public int getDriverMinorVersion() {
        return MINOR_VERSION;        
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getDriverName
    */
    public String getDriverName() throws SQLException {
        return DRIVER_NAME;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getDriverVersion
    */
    public String getDriverVersion() throws SQLException {
        return DRIVER_RELEASE;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getExportedKeys
    */
    public ResultSet getExportedKeys(String catalog, String schema, 
                                     String table) throws SQLException {
        return dbMeta.getExportedKeys(catalog, schema, table);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getExtraNameCharacters
    */
    public String getExtraNameCharacters() throws SQLException {
        return dbMeta.getExtraNameCharacters();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getIdentifierQuoteString
    */
    public String getIdentifierQuoteString() throws SQLException {
        return dbMeta.getIdentifierQuoteString();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getImportedKeys
    */
    public ResultSet getImportedKeys(String catalog, String schema, 
                                     String table) throws SQLException {
        return dbMeta.getImportedKeys(catalog, schema, table);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getIndexInfo
    */
    public ResultSet getIndexInfo(String catalog, String schema, String table, 
                                  boolean unique, boolean approximate)
                           throws SQLException {
        return dbMeta.getIndexInfo(catalog, schema, table, unique, approximate);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getJDBCMajorVersion
    */
    public int getJDBCMajorVersion() throws SQLException {
        return JDBC_MAJOR_VERSION;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getJDBCMinorVersion
    */
    public int getJDBCMinorVersion() throws SQLException {
        return JDBC_MINOR_VERSION;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxBinaryLiteralLength
    */
    public int getMaxBinaryLiteralLength() throws SQLException {
        return dbMeta.getMaxBinaryLiteralLength();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxCatalogNameLength
    */
    public int getMaxCatalogNameLength() throws SQLException {
// NiLOSTEP...
//    ( ISSUE )        
        return dbMeta.getMaxCatalogNameLength();
// End        
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxCharLiteralLength
    */
    public int getMaxCharLiteralLength() throws SQLException {
        return dbMeta.getMaxCharLiteralLength();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxColumnNameLength
    */
    public int getMaxColumnNameLength() throws SQLException {
        return dbMeta.getMaxColumnNameLength();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxColumnsInGroupBy
    */
    public int getMaxColumnsInGroupBy() throws SQLException {
        return dbMeta.getMaxColumnsInGroupBy();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxColumnsInIndex
    */
    public int getMaxColumnsInIndex() throws SQLException {
        return dbMeta.getMaxColumnsInIndex();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxColumnsInOrderBy
    */
    public int getMaxColumnsInOrderBy() throws SQLException {
        return dbMeta.getMaxColumnsInOrderBy();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxColumnsInSelect
    */
    public int getMaxColumnsInSelect() throws SQLException {
        return dbMeta.getMaxColumnsInSelect();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxColumnsInTable
    */
    public int getMaxColumnsInTable() throws SQLException {
// NiLOSTEP...
//  ISSUE        
        return 255;
// End
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxConnections
    */
    public int getMaxConnections() throws SQLException {
// NiLOSTEP...
//   ISSUE        
        return 1;
//End
    }
    
    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxCursorNameLength
    */
    public int getMaxCursorNameLength() throws SQLException {
        return dbMeta.getMaxCursorNameLength();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxIndexLength
    */
    public int getMaxIndexLength() throws SQLException {
        return dbMeta.getMaxIndexLength();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxProcedureNameLength
    */
    public int getMaxProcedureNameLength() throws SQLException {
        return dbMeta.getMaxProcedureNameLength();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxRowSize
    */
    public int getMaxRowSize() throws SQLException {
        return dbMeta.getMaxRowSize();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxSchemaNameLength
    */
    public int getMaxSchemaNameLength() throws SQLException {
        return dbMeta.getMaxSchemaNameLength();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxStatementLength
    */
    public int getMaxStatementLength() throws SQLException {
        return dbMeta.getMaxStatementLength();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxStatements
    */
    public int getMaxStatements() throws SQLException {
        return dbMeta.getMaxStatements();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxTableNameLength
    */
    public int getMaxTableNameLength() throws SQLException {
        return dbMeta.getMaxTableNameLength();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxTablesInSelect
    */
    public int getMaxTablesInSelect() throws SQLException {
        return dbMeta.getMaxTablesInSelect();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getMaxUserNameLength
    */
    public int getMaxUserNameLength() throws SQLException {
        return dbMeta.getMaxUserNameLength();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getNumericFunctions
    */
    public String getNumericFunctions() throws SQLException {
        return dbMeta.getNumericFunctions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getPrimaryKeys
    */
    public ResultSet getPrimaryKeys(String catalog, String schema, String table)
                             throws SQLException {
        return dbMeta.getPrimaryKeys(catalog, schema, table);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getProcedureColumns
    */
    public ResultSet getProcedureColumns(String catalog, String schemaPattern, 
                                         String procedureNamePattern, 
                                         String columnNamePattern)
                                  throws SQLException {
        return dbMeta.getProcedureColumns(catalog, schemaPattern, 
                                    procedureNamePattern, columnNamePattern);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getProcedureTerm
    */
    public String getProcedureTerm() throws SQLException {
        return dbMeta.getProcedureTerm();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getProcedures
    */
    public ResultSet getProcedures(String catalog, String schemaPattern, 
                                   String procedureNamePattern)
                            throws SQLException {
        return dbMeta.getProcedures(catalog, schemaPattern, 
                                                procedureNamePattern);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getResultSetHoldability
    */
    public int getResultSetHoldability() throws SQLException {
        return dbMeta.getResultSetHoldability();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getSQLKeywords
    */
    public String getSQLKeywords() throws SQLException {
        return dbMeta.getSQLKeywords();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getSQLStateType
    */
    public int getSQLStateType() throws SQLException {
        return dbMeta.getSQLStateType();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getSchemaTerm
    */
    public String getSchemaTerm() throws SQLException {
        return dbMeta.getSchemaTerm();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getSchemas
    */
    public ResultSet getSchemas() throws SQLException {
//NiLOSTEP...
//  [ ISSUE ]
//End        
        return dbMeta.getSchemas();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getSearchStringEscape
    */
    public String getSearchStringEscape() throws SQLException {
        return dbMeta.getSearchStringEscape();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getStringFunctions
    */
    public String getStringFunctions() throws SQLException {
        return dbMeta.getStringFunctions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getSuperTables
    */
    public ResultSet getSuperTables(String catalog, String schemaPattern, 
                                    String tableNamePattern)
                             throws SQLException {
         return dbMeta.getSuperTables(catalog, schemaPattern, tableNamePattern);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getSuperTypes
    */
    public ResultSet getSuperTypes(String catalog, String schemaPattern, 
                                   String typeNamePattern)
                            throws SQLException {

        return dbMeta.getSuperTypes(catalog, schemaPattern, typeNamePattern);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getSystemFunctions
    */
    public String getSystemFunctions() throws SQLException {
         return dbMeta.getSystemFunctions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getTablePrivileges
    */
    public ResultSet getTablePrivileges(String catalog, String schemaPattern, 
                                        String tableNamePattern)
                                 throws SQLException {
         return dbMeta.getTablePrivileges(catalog, schemaPattern, 
                                                    tableNamePattern);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getTableTypes
    */
    public ResultSet getTableTypes() throws SQLException {
        return dbMeta.getTableTypes();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getTables
    */
    public ResultSet getTables(String catalog, String schemaPattern, 
                               String tableNamePattern, String[] types)
                        throws SQLException {
        return dbMeta.getTables(catalog, schemaPattern, tableNamePattern, types);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getTimeDateFunctions
    */
    public String getTimeDateFunctions() throws SQLException {
         return dbMeta.getTimeDateFunctions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getTypeInfo
    */
    public ResultSet getTypeInfo() throws SQLException {
         return dbMeta.getTypeInfo();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getUDTs
    */
    public ResultSet getUDTs(String catalog, String schemaPattern, 
                             String typeNamePattern, int[] types)
                      throws SQLException {
        return dbMeta.getUDTs(catalog, schemaPattern, typeNamePattern, types);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getURL
    */
    public String getURL() throws SQLException {
        return xlCon.URL;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getUserName
    */
    public String getUserName() throws SQLException {
// NiLOSTEP...
//    [ ISSUE ]
//        
        return null;
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#getVersionColumns
    */
    public ResultSet getVersionColumns(String catalog, String schema, 
                                       String table) throws SQLException {
        return dbMeta.getVersionColumns(catalog, schema, table);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#insertsAreDetected
    */
    public boolean insertsAreDetected(int type) throws SQLException {
        return dbMeta.insertsAreDetected(type);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#isCatalogAtStart
    */
    public boolean isCatalogAtStart() throws SQLException {
        return dbMeta.isCatalogAtStart();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#isReadOnly
    */
    public boolean isReadOnly() throws SQLException {
        return dbMeta.isReadOnly();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#locatorsUpdateCopy
    */
    public boolean locatorsUpdateCopy() throws SQLException {
        return dbMeta.locatorsUpdateCopy();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#nullPlusNonNullIsNull
    */
    public boolean nullPlusNonNullIsNull() throws SQLException {
        return dbMeta.nullPlusNonNullIsNull();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#nullsAreSortedAtEnd
    */
    public boolean nullsAreSortedAtEnd() throws SQLException {
        return dbMeta.nullsAreSortedAtEnd();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#nullsAreSortedAtStart
    */
    public boolean nullsAreSortedAtStart() throws SQLException {
        return dbMeta.nullsAreSortedAtStart();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#nullsAreSortedHigh
    */
    public boolean nullsAreSortedHigh() throws SQLException {
        return dbMeta.nullsAreSortedHigh();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#nullsAreSortedLow
    */
    public boolean nullsAreSortedLow() throws SQLException {
        return dbMeta.nullsAreSortedLow();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#othersDeletesAreVisible
    */
    public boolean othersDeletesAreVisible(int type) throws SQLException {
        return dbMeta.othersDeletesAreVisible(type);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#othersInsertsAreVisible
    */
    public boolean othersInsertsAreVisible(int type) throws SQLException {
        return dbMeta.othersInsertsAreVisible(type);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#othersUpdatesAreVisible
    */
    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        return dbMeta.othersUpdatesAreVisible(type);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#ownDeletesAreVisible
    */
    public boolean ownDeletesAreVisible(int type) throws SQLException {
        return dbMeta.ownDeletesAreVisible(type);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#ownInsertsAreVisible
    */
    public boolean ownInsertsAreVisible(int type) throws SQLException {
        return dbMeta.ownInsertsAreVisible(type);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#ownUpdatesAreVisible
    */
    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        return dbMeta.ownUpdatesAreVisible(type);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#storesLowerCaseIdentifiers
    */
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return dbMeta.storesLowerCaseIdentifiers();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#storesLowerCaseQuotedIdentifiers
    */
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return dbMeta.storesLowerCaseQuotedIdentifiers();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#storesMixedCaseIdentifiers
    */
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return dbMeta.storesMixedCaseIdentifiers();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#storesMixedCaseQuotedIdentifiers
    */
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return dbMeta.storesMixedCaseQuotedIdentifiers();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#storesUpperCaseIdentifiers
    */
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return dbMeta.storesUpperCaseIdentifiers();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#storesUpperCaseQuotedIdentifiers
    */
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return dbMeta.storesUpperCaseQuotedIdentifiers();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsANSI92EntryLevelSQL
    */
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return dbMeta.supportsANSI92EntryLevelSQL();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsANSI92FullSQL
    */
    public boolean supportsANSI92FullSQL() throws SQLException {
        return dbMeta.supportsANSI92FullSQL();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsANSI92IntermediateSQL
    */
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return dbMeta.supportsANSI92IntermediateSQL();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsAlterTableWithAddColumn
    */
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return dbMeta.supportsAlterTableWithAddColumn();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsAlterTableWithDropColumn
    */
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return dbMeta.supportsAlterTableWithDropColumn();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsBatchUpdates
    */
    public boolean supportsBatchUpdates() throws SQLException {
        return dbMeta.supportsBatchUpdates();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsCatalogsInDataManipulation
    */
    public boolean supportsCatalogsInDataManipulation()
                                               throws SQLException {
        return dbMeta.supportsCatalogsInDataManipulation();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsCatalogsInIndexDefinitions
    */
    public boolean supportsCatalogsInIndexDefinitions()
                                               throws SQLException {
        return dbMeta.supportsCatalogsInIndexDefinitions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsCatalogsInPrivilegeDefinitions
    */
    public boolean supportsCatalogsInPrivilegeDefinitions()
                                                   throws SQLException {
        return dbMeta.supportsCatalogsInPrivilegeDefinitions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsCatalogsInProcedureCalls
    */
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return dbMeta.supportsCatalogsInProcedureCalls();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsCatalogsInTableDefinitions
    */
    public boolean supportsCatalogsInTableDefinitions()
                                               throws SQLException {
        return dbMeta.supportsCatalogsInTableDefinitions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsColumnAliasing
    */
    public boolean supportsColumnAliasing() throws SQLException {
        return dbMeta.supportsColumnAliasing();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsConvert
    */
    public boolean supportsConvert() throws SQLException {
        return dbMeta.supportsConvert();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsConvert
    */
    public boolean supportsConvert(int fromType, int toType)
                            throws SQLException {
        return dbMeta.supportsConvert(fromType, toType);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsCoreSQLGrammar
    */
    public boolean supportsCoreSQLGrammar() throws SQLException {
        return dbMeta.supportsCoreSQLGrammar();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsCorrelatedSubqueries
    */
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return dbMeta.supportsCorrelatedSubqueries();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsDataDefinitionAndDataManipulationTransactions
    */
    public boolean supportsDataDefinitionAndDataManipulationTransactions()
        throws SQLException {
        return dbMeta.supportsDataDefinitionAndDataManipulationTransactions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsDataManipulationTransactionsOnly
    */
    public boolean supportsDataManipulationTransactionsOnly()
                                                     throws SQLException {
        return dbMeta.supportsDataManipulationTransactionsOnly();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsDifferentTableCorrelationNames
    */
    public boolean supportsDifferentTableCorrelationNames()
                                                   throws SQLException {
        return dbMeta.supportsDifferentTableCorrelationNames();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsExpressionsInOrderBy
    */
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return dbMeta.supportsExpressionsInOrderBy();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsExtendedSQLGrammar
    */
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return dbMeta.supportsExtendedSQLGrammar();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsFullOuterJoins
    */
    public boolean supportsFullOuterJoins() throws SQLException {
        return dbMeta.supportsFullOuterJoins();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsGetGeneratedKeys
    */
    public boolean supportsGetGeneratedKeys() throws SQLException {
        return dbMeta.supportsGetGeneratedKeys();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsGroupBy
    */
    public boolean supportsGroupBy() throws SQLException {
        return dbMeta.supportsGroupBy();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsGroupByBeyondSelect
    */
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return dbMeta.supportsGroupByBeyondSelect();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsGroupByUnrelated
    */
    public boolean supportsGroupByUnrelated() throws SQLException {
        return dbMeta.supportsGroupByUnrelated();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsIntegrityEnhancementFacility
    */
    public boolean supportsIntegrityEnhancementFacility()
                                                 throws SQLException {
        return dbMeta.supportsIntegrityEnhancementFacility();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsLikeEscapeClause
    */
    public boolean supportsLikeEscapeClause() throws SQLException {
        return dbMeta.supportsLikeEscapeClause();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsLimitedOuterJoins
    */
    public boolean supportsLimitedOuterJoins() throws SQLException {
        return dbMeta.supportsLimitedOuterJoins();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsMinimumSQLGrammar
    */
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return dbMeta.supportsMinimumSQLGrammar();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsMixedCaseIdentifiers
    */
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return dbMeta.supportsMixedCaseIdentifiers();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsMixedCaseQuotedIdentifiers
    */
    public boolean supportsMixedCaseQuotedIdentifiers()
                                               throws SQLException {
        return dbMeta.supportsMixedCaseQuotedIdentifiers();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsMultipleOpenResults
    */
    public boolean supportsMultipleOpenResults() throws SQLException {
        return dbMeta.supportsMultipleOpenResults();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsMultipleResultSets
    */
    public boolean supportsMultipleResultSets() throws SQLException {
        return dbMeta.supportsMultipleResultSets();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsMultipleTransactions
    */
    public boolean supportsMultipleTransactions() throws SQLException {
        return dbMeta.supportsMultipleTransactions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsNamedParameters
    */
    public boolean supportsNamedParameters() throws SQLException {
        return dbMeta.supportsNamedParameters();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsNonNullableColumns
    */
    public boolean supportsNonNullableColumns() throws SQLException {
        return dbMeta.supportsNonNullableColumns();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsOpenCursorsAcrossCommit
    */
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return dbMeta.supportsOpenCursorsAcrossCommit();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsOpenCursorsAcrossRollback
    */
    public boolean supportsOpenCursorsAcrossRollback()
                                              throws SQLException {
        return dbMeta.supportsOpenCursorsAcrossRollback();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsOpenStatementsAcrossCommit
    */
    public boolean supportsOpenStatementsAcrossCommit()
                                               throws SQLException {
        return dbMeta.supportsOpenStatementsAcrossCommit();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsOpenStatementsAcrossRollback
    */
    public boolean supportsOpenStatementsAcrossRollback()
                                                 throws SQLException {
        return dbMeta.supportsOpenStatementsAcrossRollback();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsOrderByUnrelated
    */
    public boolean supportsOrderByUnrelated() throws SQLException {
        return dbMeta.supportsOrderByUnrelated();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsOuterJoins
    */
    public boolean supportsOuterJoins() throws SQLException {
        return dbMeta.supportsOuterJoins();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsPositionedDelete
    */
    public boolean supportsPositionedDelete() throws SQLException {
        return dbMeta.supportsPositionedDelete();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsPositionedUpdate
    */
    public boolean supportsPositionedUpdate() throws SQLException {
        return dbMeta.supportsPositionedUpdate();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsResultSetConcurrency
    */
    public boolean supportsResultSetConcurrency(int type, int concurrency)
                                         throws SQLException {
        return dbMeta.supportsResultSetConcurrency(type, concurrency);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsResultSetHoldability
    */
    public boolean supportsResultSetHoldability(int holdability)
                                         throws SQLException {
        return dbMeta.supportsResultSetHoldability(holdability);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsResultSetType
    */
    public boolean supportsResultSetType(int type) throws SQLException {
        return dbMeta.supportsResultSetType(type);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsSavepoints
    */
    public boolean supportsSavepoints() throws SQLException {
        return dbMeta.supportsSavepoints();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsSchemasInDataManipulation
    */
    public boolean supportsSchemasInDataManipulation()
                                              throws SQLException {
        return dbMeta.supportsSchemasInDataManipulation();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsSchemasInIndexDefinitions
    */
    public boolean supportsSchemasInIndexDefinitions()
                                              throws SQLException {
        return dbMeta.supportsSchemasInIndexDefinitions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsSchemasInPrivilegeDefinitions
    */
    public boolean supportsSchemasInPrivilegeDefinitions()
                                                  throws SQLException {
        return dbMeta.supportsSchemasInPrivilegeDefinitions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsSchemasInProcedureCalls
    */
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return dbMeta.supportsSchemasInProcedureCalls();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsSchemasInTableDefinitions
    */
    public boolean supportsSchemasInTableDefinitions()
                                              throws SQLException {
        return dbMeta.supportsSchemasInTableDefinitions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsSelectForUpdate
    */
    public boolean supportsSelectForUpdate() throws SQLException {
        return dbMeta.supportsSelectForUpdate();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsStatementPooling
    */
    public boolean supportsStatementPooling() throws SQLException {
        return dbMeta.supportsStatementPooling();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsStoredProcedures
    */
    public boolean supportsStoredProcedures() throws SQLException {
        return dbMeta.supportsStoredProcedures();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsSubqueriesInComparisons
    */
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return dbMeta.supportsSubqueriesInComparisons();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsSubqueriesInExists
    */
    public boolean supportsSubqueriesInExists() throws SQLException {
        return dbMeta.supportsSubqueriesInExists();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsSubqueriesInIns
    */
    public boolean supportsSubqueriesInIns() throws SQLException {
        return dbMeta.supportsSubqueriesInIns();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsSubqueriesInQuantifieds
    */
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return dbMeta.supportsSubqueriesInQuantifieds();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsTableCorrelationNames
    */
    public boolean supportsTableCorrelationNames() throws SQLException {
        return dbMeta.supportsTableCorrelationNames();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsTransactionIsolationLevel
    */
    public boolean supportsTransactionIsolationLevel(int level)
                                              throws SQLException {
        return dbMeta.supportsTransactionIsolationLevel(level);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsTransactions
    */
    public boolean supportsTransactions() throws SQLException {
        return dbMeta.supportsTransactions();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsUnion
    */
    public boolean supportsUnion() throws SQLException {
        return dbMeta.supportsUnion();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#supportsUnionAll
    */
    public boolean supportsUnionAll() throws SQLException {
        return dbMeta.supportsUnionAll();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#updatesAreDetected
    */
    public boolean updatesAreDetected(int type) throws SQLException {
        return dbMeta.updatesAreDetected(type);
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#usesLocalFilePerTable
    */
    public boolean usesLocalFilePerTable() throws SQLException {
        return dbMeta.usesLocalFilePerTable();
    }

    /**
    * Implements method in interface java.sql.Connection
    * @see java.sql.DatabaseMetaData#usesLocalFiles
    */
    public boolean usesLocalFiles() throws SQLException {
        return dbMeta.usesLocalFiles();
    }

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultSet getClientInfoProperties() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getFunctionColumns(String arg0, String arg1, String arg2,
			String arg3) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getFunctions(String arg0, String arg1, String arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowIdLifetime getRowIdLifetime() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getSchemas(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}