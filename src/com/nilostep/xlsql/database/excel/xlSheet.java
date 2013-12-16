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
package com.nilostep.xlsql.database.excel;

import com.nilostep.xlsql.database.*;
import com.nilostep.xlsql.database.sql.ASqlSelect;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.logging.*;

import jxl.*;
import jxl.write.*;


/**
 * xlSheet represents a single sheet in an Excel workbook.
 * 
 * @version $Revision: 1.21 $
 * @author $author$
 */
public class xlSheet extends AFile {
    private static final String XLS = ".xls";

    /**
     * Creates a new xlSheet object.
     * 
     * @param dir (relative) root directory contains excel files
     * @param folder name of workbook without extension
     * @param name sheet identifier as in excel
     * 
     * @throws xlDatabaseException when this object cannot be instantiated
     */
    xlSheet(File dir, String folder, String name) throws xlDatabaseException {
        super(dir, folder, name);
    }

    /**
     * Creates a new xlSheet object.
     * 
     * @param dir (relative) root directory contains excel files
     * @param folder name of workbook without extension
     * @param name sheet identifier as in excel
     * @param bdirty DOCUMENT ME!
     */
    xlSheet(File dir, String folder, String name, boolean bdirty) {
        super(dir, folder, name, bdirty);
    }

    private jxl.Sheet getSheet() throws xlDatabaseException {
        // COST! ( Performance problems?? Better fix JXL.ow first. :-) )
        Sheet ret = null;

        try {
            File f = new File(directory.getPath() + File.separator + 
                              subFolderName + XLS);
            ret = Workbook.getWorkbook(f).getSheet(fileName);
        } catch (IOException ioe) {
            throw new xlDatabaseException("xlSQL: -excel> io ERR: " + 
                                          ioe.getMessage());
        } catch (jxl.read.biff.BiffException biffe) {
            throw new xlDatabaseException("xlSQL: -excel> biff ERR: " + 
                                          biffe.getMessage());
        }

        return ret;
    }

    /**
     * Close a sheet and flush changes in the sql engine.
     * 
     * @param subOut Higher level object
     * @param select Engine
     * 
     * @throws xlDatabaseException Database error
     */
    public void close(Object subOut, ASqlSelect select)
               throws xlDatabaseException {
        Sheet workSheet;
        ResultSet rs;
        WritableWorkbook wbOut = (WritableWorkbook) subOut;

        if (isChanged[xlConstants.ADD]) {
            workSheet = wbOut.createSheet(fileName, wbOut.getNumberOfSheets());

            WritableSheet wsh = (WritableSheet) workSheet;

            try {
                rs = select.QueryData(subFolderName, fileName);
            } catch (SQLException sqe) {
                throw new xlDatabaseException(sqe.getMessage());
            }


            //Create new empty sheet
            write(wsh, rs);
        } else if (isChanged[xlConstants.UPDATE]) {
            // Remove sheet by name
            int i;
            WritableSheet _s;

            for (i = 0; i < wbOut.getNumberOfSheets(); i++) {
                _s = wbOut.getSheet(i);

                if (_s.getName().equals(fileName)) {
                    break;
                }
            }

            wbOut.removeSheet(i);

            try {
                rs = select.QueryData(subFolderName, fileName);
            } catch (SQLException sqe) {
                throw new xlDatabaseException(sqe.getMessage());
            }


            // Create new empty sheet
            write(wbOut.createSheet(fileName, i), rs);
        } else if (isChanged[xlConstants.DELETE]) {
            // Remove sheet by name, we have to find the index first
            int i;
            WritableSheet _s;

            for (i = 0; i < wbOut.getNumberOfSheets(); i++) {
                _s = wbOut.getSheet(i);

                if (_s.getName().equals(fileName)) {
                    break;
                }
            }

            wbOut.removeSheet(i);
        }
    }

    protected boolean readFile() throws xlDatabaseException {
        Sheet sheet = getSheet();
        boolean ret = true;
        columnCount = sheet.getColumns();
        rowCount = sheet.getRows();

        // invalidate sheets with either 0 columns or 0 rows
        // as reported by the JXL Sheet interface
        for (int z = 0; z < 1; z++) {
            if ((columnCount == 0) || (rowCount == 0)) {
                ret = false;

                break;
            }

            // investigate the 'header row', invalidate sheets with
            // incomplete or empty cells as they become table columns
            Cell[] c = sheet.getRow(0);

            if ((c.length == 0) || (c.length != columnCount)) {
                ret = false;

                break;
            }


            // the header row is OK, so we can transfer the data to the 
            // columnNames String[] array
            columnNames = new String[c.length];
            columnTypes = new String[c.length];            

            // we have to look a bit closer to each column because we have 
            // to be sure that the CREATE TABLE is accepted in all(!) SQL 
            // dialects.
            for (int i = 0; i < c.length; i++) {
                // the header must be of type LABEL, otherwise invalidate
                if (c[i].getType() != CellType.LABEL) {
                    ret = false;

                    break;
                }

                // the COLUMNNAME must match a regular expression pattern, 
                // notice we are conservative here, but all SQLs must accept it
                // ( "^[A-Za-z0-9._-]{1,30}+$" is the so called 'pattern' )
                // if at least one column does not match: invalidate table
                if (!c[i].getContents().matches("^[A-Za-z0-9._-]{1,30}+$")) {
                    ret = false;

                    break;
                }


                // columnnames are OK, so transfer
                columnNames[i] = c[i].getContents();
            }

            // *BUG* issue 163. Duplicate columnnames
            if (!ret) {
                break;
            }

            // we have to check if all columnnames are different
            HashMap index = new HashMap();
            String key;
            String value;

            for (int n = 0; n < columnNames.length; n++) {
                key = columnNames[n].toUpperCase();
                value = columnNames[n];

                if (index.containsKey(key)) {
                    ret = false;

                    break;
                } else {
                    index.put(key, value);
                }
            }

            if (!ret) {
                break;
            }

            //
            Cell[] t;

            if (rowCount == 1) {
                t = c;
                logger.warning(fileName + " has no data, assuming VARCHAR");
            } else {
                // investigate the first row with data: determine datatypes
                t = sheet.getRow(1);

                // when there are less values than columns..
                if (t.length != c.length) {
                    logger.warning(fileName 
                                + " may contain invalid data, continuing...");
                    // not all columns can be evaluated init all to VARCHAR
                    for (int k = 0; k < columnTypes.length; k++) {
                        columnTypes[k] = "VARCHAR";
                    }
                }
            }

            columnTypes = new String[c.length];

            // look closer at the values of the columns in the first row, the 
            // SQL datatype that will be used in the engine is determined here
            for (int j = 0; j < t.length; j++) {
                if ((t[j].getType() == CellType.NUMBER) || 
                        (t[j].getType() == CellType.NUMBER_FORMULA)) {
                    columnTypes[j] = "DOUBLE";
                } else if ((t[j].getType() == CellType.LABEL) || 
                               (t[j].getType() == CellType.STRING_FORMULA)) {
                    columnTypes[j] = "VARCHAR";
                } else if ((t[j].getType() == CellType.DATE) || 
                               (t[j].getType() == CellType.DATE_FORMULA)) {
                    columnTypes[j] = "DATE";
                } else if ((t[j].getType() == CellType.BOOLEAN) || 
                               (t[j].getType() == CellType.BOOLEAN_FORMULA)) {
                    columnTypes[j] = "BIT";

                    // finally there could be an empty or null cell, we can only
                    // assume VARCHAR --> unless we would look at the next row
                    // NiLOSTEP... says
                    //      (*BUG* fixed! issue 162. Empty data. 29/8-'04)
                } else if (t[j].getType() == CellType.EMPTY) {
                    columnTypes[j] = "VARCHAR";
                } else {
                    // we -could- add some assert here ?
                    ret = false;

                    break;
                }
            }
        }

        if (!ret) {
            logger.info(fileName + " contains no SQL data: invalidated");
        }

        sheet = null;

        return ret;
    }

    private void write(WritableSheet wsh, ResultSet rs)
                throws xlDatabaseException {
        // Transfer a query to an Excel sheet in the JXL API
        try {
            ResultSetMetaData rsmd = rs.getMetaData();

            // Write header row
            int col = 0;
            int row = 0;
            int cols = rsmd.getColumnCount();
            int[] type = new int[cols];

            for (col = 0; col < cols; col++) {
                Label label = new Label(col, row, rsmd.getColumnName(col + 1));
                type[col] = xlConstants.xlType(rsmd.getColumnType(col + 1));
                wsh.addCell(label);
            }


            //Write data rows
            row++;

            while (rs.next()) {
                for (col = 0; col < cols; col++) {
                    switch (type[col]) {
                    case 1: //Number

                        jxl.write.Number nm = new jxl.write.Number(col, row, 
                                                                   rs.getDouble(col + 1));
                        wsh.addCell(nm);

                        break;

                    case 2: //Text

                        Label lb = new Label(col, row, rs.getString(col + 1));
                        wsh.addCell(lb);

                        break;

                    case 3: //Date

                        java.util.Date bug;

                        if (rs.getDate(col + 1) == null) {
                            bug = new java.util.Date(0);
                        } else {
                            bug = rs.getDate(col + 1);
                        }

                        DateTime dt = new DateTime(col, row, bug);
                        wsh.addCell(dt);

                        break;

                    case 4: //Boolean

                        jxl.write.Boolean bl = new jxl.write.Boolean(col, row, 
                                                                     rs.getBoolean(col + 1));
                        wsh.addCell(bl);

                        break;

                    default:

                        //NiLOSTEP...
                        //   workaround for MySQL 'TEXT' ...
                        //End                            
                        lb = new Label(col, row, rs.getString(col + 1));
                        wsh.addCell(lb);
                    }
                }

                row++;
            }
        } catch (SQLException sqe) {
            throw new xlDatabaseException(sqe.getMessage());
        } catch (jxl.write.WriteException jxw) {
            throw new xlDatabaseException(jxw.getMessage());
        }
    }

    private static int xlType(int sqlType) {
        int ret = 0;

        switch (sqlType) {
            case (-6):
            case (-5):
            case (-2):
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                ret = 1;
                break;
            
            case 1:
            case 12:
            case 70:
                ret = 2;
                break;

            case 91:
            case 92:
            case 93:
                ret = 3;
                break;

            case -7:
            case 16:
                ret = 4;
                break;

            default:
                ret = 0;
        }

        return ret;
    }

    /**
     * Return sheet as String matrix
     * 
     * @return table as 2D array
     * 
     * @throws xlDatabaseException DOCUMENT ME!
     * @throws IllegalArgumentException if called on invalidated data
     */
    public String[][] getValues() throws xlDatabaseException {
        String[][] ret = 
        {
            { "" }
        };

        if (validAsSqlTable) {
            Sheet sheet = getSheet();
            ret = new String[columnCount][rowCount - 1];

            for (int i = 0; i < (rowCount - 1); i++) {
                // Process all columns for row: i
                for (int j = 0; j < columnCount; j++) {
                    Cell c = sheet.getCell(j, i + 1);

                    if ((c == null) || (c.getType() == CellType.EMPTY)) {
                        ret[j][i] = "";
                    } else if ((c.getType() == CellType.NUMBER) || 
                                   (c.getType() == CellType.NUMBER_FORMULA)) {
                        try {
                            Locale.setDefault(new Locale("en", "US"));

                            Double db = new Double(((NumberCell) c).getValue());
                            ret[j][i] = db.toString();
                        } catch (ClassCastException ce) {
                            try {
                                SimpleDateFormat dateFormat = 
                                        new SimpleDateFormat("dd/MM/yyyy");
                                java.util.Date d;
                                d = dateFormat.parse(c.getContents());
                                ret[j][i] = c.getContents();
                            } catch (ParseException pe) {
                                ret[j][i] = "";
                            }
                        }
                    } else {
                        ret[j][i] = c.getContents();
// BUG>
// Maak hier een REGEXP van!
//                        ret[j][i] = ret[j][i].replaceAll("'", "''");
                    }
                }
            }

            sheet = null;
        } else {
            throw new IllegalArgumentException(xlConstants.NOARGS);
        }

        return ret;
    }
}