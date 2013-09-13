package com.philipp.tools.best.db;

import com.jacob.impl.ado.Connection;

public class DBFADOConnector {
	
	private DBFADOConnector () {		
	}
	
	public static Connection getDBFADOConnection (String databasePath) {
		
		String url = "Provider=VFPOLEDB.1;Data Source=" + databasePath + 
				     ";Mode=Share Deny None;Extended Properties='';User ID='';Mask Password=False;Cache Authentication=False;Encrypt Password=False;Collating Sequence=MACHINE;DSN='';DELETED=True;CODEPAGE=1251;MVCOUNT=16384;ENGINEBEHAVIOR=90;TABLEVALIDATE=3;REFRESH=5;VARCHARMAPPING=False;ANSI=True;REPROCESS=5";
		
		Connection c = new Connection();
		c.setConnectionString(url);
		c.Open();					
		return c;
	}

}
