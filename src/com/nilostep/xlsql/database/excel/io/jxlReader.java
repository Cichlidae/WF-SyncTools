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
package com.nilostep.xlsql.database.excel.io;

import com.nilostep.xlsql.database.excel.IExcelReader;
import com.nilostep.xlsql.database.excel.IExcelStore;
import com.nilostep.xlsql.database.excel.xlWorkbook;
import com.nilostep.xlsql.database.excel.xlXlsFilter;
import com.nilostep.xlsql.database.*;

import java.io.*;
import java.util.logging.*;

import jxl.*;


/**
 * Interface between xlSQL and the JXL library for reading Excel files
 * 
 * @author Jim Caprioli
 */
public class jxlReader implements IExcelReader {
    protected static final Logger logger = Logger.getAnonymousLogger();
    private final IExcelStore store;


    /**
     *
     * @param store
     * @throws xlDatabaseException
     */    
    public jxlReader(IExcelStore store) throws xlDatabaseException {
        this.store = store;
    }

    /**
     *
     * @param dir directory
     * @throws xlDatabaseException when an error occurs
     */    
    public void readWorkbooks(java.io.File dir) throws xlDatabaseException {

        File[] f = dir.listFiles(new xlXlsFilter());

        if (f == null) {
            throw new xlDatabaseException("jxlReader cannot read from " + dir);
        }

        for (int i = 0; i < f.length; i++) {
            try {
                Workbook workbook = Workbook.getWorkbook(f[i]);
                String name = f[i].getName()
                                  .substring(0, f[i].getName().lastIndexOf('.'));
                ASubFolder obj = new xlWorkbook(dir, name);
                store.getStore().put(name.toUpperCase(), obj);
            } catch (jxl.read.biff.BiffException jxle) {
                logger.info(f[i] + "-java excel api reports: '" + 
                               jxle.getMessage() + "' ..?");

                continue; // to next in for loop
            } catch (IOException ioe) {
                logger.warning(f[i] + "-ioe exception: '" + 
                               ioe.getMessage() + "' continuing..");

                continue; // to next in for loop
            }
        }
    }
}