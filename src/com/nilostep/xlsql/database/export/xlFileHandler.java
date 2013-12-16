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
package com.nilostep.xlsql.database.export;

import java.io.*;
import java.util.*;

public class xlFileHandler implements IExportHandler {
    private BufferedWriter out;
    
    public xlFileHandler(File exportfile) throws xlExportException {
        try {
            FileWriter filewriter = new FileWriter(exportfile);
            out = new BufferedWriter(filewriter);
        }
        catch (IOException ioe) {
            throw new xlExportException(ioe.getMessage());
        }
    }
    
    public void write (List export) throws xlExportException {
        ListIterator l = export.listIterator();
        try {
            while (l.hasNext()) {
                String output;
                output = (String) l.next();
                out.write(output + "\n");
            }
        }
        catch (IOException ioe) {
            throw new xlExportException(ioe.getMessage());
        }
    }
    
    public void close() throws xlExportException {
        try {
            out.close();
        }
        catch (IOException ioe) {
            throw new xlExportException(ioe.getMessage());
        }
    }
}