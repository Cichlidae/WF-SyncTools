package com.philipp.tools.best.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.jacob.com.Variant;
import com.jacob.impl.ado.Command;
import com.jacob.impl.ado.CommandTypeEnum;
import com.jacob.impl.ado.Field;
import com.jacob.impl.ado.Fields;
import com.jacob.impl.ado.Recordset;
import com.philipp.tools.best.db.DBFADOConnector;
import com.philipp.tools.best.db.VFPDriverConnector;
import com.philipp.tools.best.log.Logger;

public class NomenclatureSync {
	
	static final ArrayList<Index> INDEXES = new ArrayList<Index>(2);
	
	private static class Index {
		
		String CSVName;
		String DBFName;
		int column = -1;
					
		Index (String csv, String dbf) {
			this.CSVName = csv;
			this.DBFName = dbf;
		}
				
	}
	
	private static class IndexString {
		String csvIndex;
		
		IndexString (String csv) {
			this.csvIndex = csv;
		}
		
		public boolean equals (Object o) {		
			if (o instanceof Index) {
				Index that = (Index)o;
				if (that.CSVName.compareTo(csvIndex) == 0) return true;
			}		
			return false;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length > 0) {
			File db = new File(args[0]); //���� ���� ������
			if (!db.exists()) {
				System.err.println("��������� ���� ���� ������ �� ����������! ��������� ������������ ���������� ����.");
				return;
			}		
			File csv = new File(args[1]); //CSV ����
			if (!csv.exists()) {
				System.err.println("��������� ���� CSV �� ����������! ��������� ������������ ���������� ����.");
				return;
			}
			String indexes = args[2];
			if (isBlankOrEmpty(indexes)) {
				System.err.println("�� ������ �������� ��������� ������� (DBF:CSV). �������� ����� ; ��� ��������.");
				return;
			}	
			String fields = args[3];
			if (isBlankOrEmpty(fields)) {
				System.err.println("�� ������ �������� ������������ ���� � �� � ������� CSV c �������� ����������. �������� ��� <DBF:CSV>.");
				return;
			}
			
			String jvmArch = System.getProperty("sun.arch.data.model");
			if (jvmArch.contains("64")) {
				Logger.log("� ��� ����������� JVM 64-bit, �� ���������� �������� ������ �� JVM 32-bit, ��������� ���������� ����������� 32-x ��������� FVP �������.");
				return;
			}													
			handleCSV(db, csv, indexes, fields);			
		}
		else {
			System.err.println("�� ������ ����������� ���������!");
		}
		
	}
		
	private static void handleCSV (File db, File csv, String indexes, String fields) {
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(csv));
			Logger.log("���� CSV ��������.");
			
			com.jacob.impl.ado.Connection connection = DBFADOConnector.getDBFADOConnection(db.getAbsolutePath());
	        Logger.log("����������� � �� �������. " + connection);
	        
	        String sql = "SELECT name FROM sclad_mlabel where name=LIKE(*Г*)";
	        
	        Command comm = new Command();
			comm.setActiveConnection(connection);
			comm.setCommandType(CommandTypeEnum.adCmdText);
			comm.setCommandText(sql);
			Recordset rs = comm.Execute();
			printRS(rs);
	        
	        
	        /*String sql = "SELECT * FROM 'd:/work/db/trade/sclad/mlabel'";
	        ResultSet result = connection.createStatement().executeQuery(sql);
	        while (result.next()) {
	        	Logger.log(result.getString(result.findColumn("nnum")));
	        }*/
	        
            
           /* StringTokenizer tokenizer = new StringTokenizer(indexes, ";");
            while (tokenizer.hasMoreTokens()) {
            	String index = tokenizer.nextToken();
            	if (isBlankOrEmpty(index)) {
            		Logger.err("�������� ���� ��� ����� ������ � ��������. �������� ���: <DBF:CSV;DBF:CSV;...DBF:CSV> ��� ��������.");
            		return;
            	}
            	int idx = index.indexOf(':');
            	if (isNotLast(index, idx) && idx > 0) {            		       	
            		INDEXES.add(new Index(index.substring(idx + 1), index.substring(0, idx)));
            	}
            	else {
            		Logger.err("�������� ���� ��� ����� ������ � ��������. �������� ���: <DBF:CSV;DBF:CSV;...DBF:CSV> ��� ��������.");
            		return;
            	}	
            }
            
            for (Index index : INDEXES) {
            	Logger.log("INDEX: " + index.CSVName + ":" + index.DBFName);
            }
            
            String DBF_FIELD = "code";
            String CSV_FIELD = "ARTIKUL_ARTMAIN";

            int fidx = fields.indexOf(':');
            if (isNotLast(fields, fidx) && fidx > 0) {   
            	DBF_FIELD = fields.substring(0, fidx);
            	CSV_FIELD = fields.substring(fidx + 1);          	
            }
            else {
            	Logger.err("������������� ���� ������ � ��������. �������� ���: <DBF:CSV>.");
        		return;
            }          

            String line = null;
            line = in.readLine();
            if (line == null) {
            	Logger.err("CSV ���� ������.");
            	return;
            }
            else if (isBlankOrEmpty(line)) {
            	Logger.err("��������� ������� (������ ������) CSV ����� �� ������.");
            	return;
            }
            
            int column = 0;
            boolean found = false;
            
            String[] headers = line.split(";");
            
            for (String header : headers) {            
            	if (isBlankOrEmpty(header)) {
            		Logger.err("��������� � ������� " + column + " ������");
                	return;
            	}
            	          	
            	int idx = INDEXES.indexOf(new IndexString(header));
            	
            	Logger.log("	~ " + header + ":" + idx);
            	
            	if (idx > -1) {
            		Index index = INDEXES.get(idx);
            		index.column = column;
            		Logger.log("	~! " + index.CSVName + ":" + index.column);
            		
            	}
            	column++;
            	
            	if (!found && header.compareTo(CSV_FIELD) == 0) {
            		found = true;
            	}            
            }
            
            if (!found) {
            	Logger.err("������� � ������ " + CSV_FIELD + " �� ������� � �����");
            	return;
            }
            
            column--;
            Logger.log("����� ������� CSV (" + CSV_FIELD + ") � �������� ����������: " + column);
            
            int rowsUpdated = 0;
            int rowsFailed = 0;

            while ((line = in.readLine()) != null) {
            	Logger.log("> " + line);
            	String[] values = line.split(";");
            	
            	for (String value : values) {              
                    Logger.log(">+ " + value);
                }
            	
            	final String AND_OPERATOR = " AND "; 
            	String sql = "UPDATE sclad_mlabel SET " + DBF_FIELD + "='" + values[column] + "' WHERE ";
            	for (Index index : INDEXES) {    		
            		Logger.log("	>* " + index.CSVName + ":" + index.DBFName + ":" + index.column);
            		sql += index.DBFName + "='" + values[index.column] + "'" + AND_OPERATOR;            		
            	}     	
            	sql = sql.substring(0, sql.lastIndexOf(AND_OPERATOR)) + ";";
            	
            	Logger.log("SQL: " + sql);
            	
            	if (connection.createStatement().executeUpdate(sql) == 0) {
            		Logger.err("������ �� ����������!");
            		rowsFailed++;
            	}
            	else {
            		rowsUpdated++;
            	}            	            
            }          
            Logger.log("TOTAL ROWS: " + (rowsUpdated + rowsFailed) + ", updated: " + rowsUpdated + " failed: " + rowsFailed); */
	        connection.Close();
	        Logger.log("�����������.");
        }
		catch (IOException e) {
			Logger.err(e.getMessage());
			e.printStackTrace();
		}
       /* catch (SQLException e) {
        	Logger.err(e.getMessage());
            e.printStackTrace();
            
        }*/
	}
	
	public static boolean isBlankOrEmpty (String str) {
		return str == null || "".equals(str.trim());
	}
	
	public static boolean isNotLast (String str, int idx) {
		return idx > -1 && idx < str.length() - 1;
	}
	
	public static void printRS(Recordset rs)
	  {
	    Fields fs = rs.getFields();

	    for (int i=0;i<fs.getCount();i++)
	    {
	      System.out.print(fs.getItem(i).getName() + " ");
	    }
	    System.out.println("");

	    rs.MoveFirst();
	    while (!rs.getEOF())
	    {
	      for(int i=0;i<fs.getCount();i++)
	      {
	        Field f = fs.getItem(i);
	        Variant v = f.getValue();
	        System.out.print(v + " ");
	      }
	      System.out.println("");	    	     
	      rs.MoveNext();
	    }
	  }
	
}
