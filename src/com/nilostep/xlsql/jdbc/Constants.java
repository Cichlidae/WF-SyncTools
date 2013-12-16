/*(Header: NiLOSTEP / xlSQL)

    Copyright (C) 2004 NiLOSTEP Information Sciences, all 
    rights reserved.
    
    This program is licensed under the terms of the GNU 
    General Public License.You should have received a copy 
    of the GNU General Public License along with this 
    program;
*/

package com.nilostep.xlsql.jdbc;

/**
 *  Global xlSQL constants
 *
 * @version $Revision: 1.1 $
 * @author Jim Caprioli
 */
public interface Constants {
    //~ Static variables/initializers ������������������������������������������
    public static final String APP = "xlSQL> ";
    
    public static final String DRIVER_NAME = "NiLOSTEP/xlSQL Excel JDBC Driver";
    public static final String DRIVER_RELEASE = "alpha:Y7";
    public static final String DRIVER_CLASS = "com.nilostep.xlsql.jdbc.jdbcDriverXls";
    public static final int MAJOR_VERSION = 0;
    public static final int MINOR_VERSION = 0;
    public static final String URL_PFX_XLS = "jdbc:nilostep:excel:";
    public static final String URL_PFX_CSV = "jdbc:nilostep:csv:";
    public static final boolean JDBC_COMPLIANT = false;
    public static final int JDBC_MAJOR_VERSION = 3;
    public static final int JDBC_MINOR_VERSION = 0;
    
    public static final int MAJOR_XLSQL_VERSION = 0;
    public static final int MINOR_XLSQL_VERSION = 0;
    public static final String XLSQL = "xlSQL (with HSQL database engine)";
    public static final String XLSQL_RELEASE = "pre-alpha:X0";
    
    public static final String URL = "url";
}