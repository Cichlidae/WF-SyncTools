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

/**
 * Class
 * 
 * @version $Revision: 1.4 $
 * @author Jim Caprioli
 */
public class xlException extends Exception {
    /**
     * Creates a new instance of type xlException.
     */
    public xlException() {
        super();
    }

    /**
     * Creates a new instance of type xlException.
     * 
     * @param msg
     */
    public xlException(String msg) {
        super(msg);
    }
}