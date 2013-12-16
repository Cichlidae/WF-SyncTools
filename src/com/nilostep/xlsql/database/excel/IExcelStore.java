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

/*
 * IExcelStore.java
 *
 * Created on 10 september 2004, 15:49
 */
package com.nilostep.xlsql.database.excel;

import java.util.*;

/**
 * For classes which store data read by an IExcelReader
 * 
 * @author Jim Caprioli
 */
public interface IExcelStore {
    /**
     * Return map where data can be stored
     *
     * @return Map
     */
    public Map getStore();
}