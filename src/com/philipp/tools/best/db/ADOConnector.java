package com.philipp.tools.best.db;

import com.jacob.impl.ado.Connection;
import com.philipp.tools.best.in.ExcelCommand;
import com.philipp.tools.best.in.MSSQLCommand;
import com.philipp.tools.best.in.StdinCommand;
import com.philipp.tools.best.in.VFPCommand;

public class ADOConnector {
	
	private ADOConnector () {		
	}		
	
	public static Connection getDBFADOConnection (String databasePath) {
		
		String url = "Provider=VFPOLEDB.1;Data Source=" + databasePath + 
				     ";Mode=Share Deny None;Extended Properties='';User ID='';Mask Password=False;Cache Authentication=False;Encrypt Password=False;Collating Sequence=MACHINE;DSN='';DELETED=True;CODEPAGE=1251;MVCOUNT=16384;ENGINEBEHAVIOR=90;TABLEVALIDATE=3;REFRESH=5;VARCHARMAPPING=False;ANSI=True;REPROCESS=5";
		
		Connection c = new Connection();
		c.setConnectionString(url);
		c.Open();					
		return c;
	}
	
	public static Connection getExcelADOConnection (String filePath) {
			
		String url = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source=" + filePath +
				     ";Extended Properties='Excel 12.0;HDR=Yes;IMEX=1';";
						
		Connection c = new Connection();
		c.setConnectionString(url);
		c.Open();					
		return c;		
	}
	
	public static Connection getMSSQLADOConnection (String databasePath, String initialCatalog, String workstationID) {
		
		String url = "Provider=SQLOLEDB.1;Integrated Security=SSPI;Persist Security Info=True;" +
		             "Initial Catalog=" + initialCatalog + ";Data Source=" + databasePath +
		             ";Use Procedure for Prepare=1;Auto Translate=True;Packet Size=4096;" +
		             "Workstation ID=" + workstationID + ";Use Encryption for Data=False;Tag with column collation when possible=False";

		Connection c = new Connection();
		c.setConnectionString(url);
		c.Open();					
		return c;		
	}
	
	public static Connection getADOConnection (StdinCommand incom) throws IllegalArgumentException {
		
		if (incom instanceof VFPCommand) {
			return ADOConnector.getDBFADOConnection(((VFPCommand)incom).getOnlyDbc());
		}
		else if (incom instanceof ExcelCommand) {
			return ADOConnector.getExcelADOConnection(((ExcelCommand)incom).getOnlyXlsx());
		}
		else if (incom instanceof MSSQLCommand) {
			MSSQLCommand c = (MSSQLCommand)incom;
			return ADOConnector.getMSSQLADOConnection(c.getDataSource(), c.getOnlyCatalog(), c.getWorkstationId());
		}			
		else {
			throw new IllegalArgumentException("Unrecognized database type (need to be VFP *.DBC or Excel *.XLSX or MSSQL initial catalog.");
		}
	}

}
