package com.philipp.tools.best.sync;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import com.philipp.tools.best.db.VFPDriverConnector;
import com.philipp.tools.best.log.Logger;

public class VFPQueryManager {

	private final static int IN_PROCESS = 0;
	private final static int FAILED = 1;
	private final static int TERMINATED = 2;
	
	private static int STATUS = IN_PROCESS;
		
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
			String jvmArch = System.getProperty("sun.arch.data.model");
			if (jvmArch.contains("64")) {
				Logger.log("� ��� ����������� JVM 64-bit, �� ���������� �������� ������ �� JVM 32-bit.");
				return;
			}	
			process(db);						
		}
		else {
			System.err.println("�� ������ ����������� ���������: url � ���� ������!");
		}
	}
	
	private static void process (File db) {
		
		try {
			Connection connection = VFPDriverConnector.getODBCConnection(db.getAbsolutePath());
	        Logger.log("����������� � �� �������.");
	        
	        while (STATUS == IN_PROCESS) {	       
		        Scanner scan = new Scanner(System.in);
		        String s = scan.nextLine();
		        if (s.toLowerCase().compareTo("exit") == 0) {
		        	STATUS = TERMINATED;
		        	continue;
		        }
		        try {
		        	doQuery(connection, s);
		        }
		        catch (SQLException e) {
		        	STATUS = FAILED;
		        	Logger.log("��������� ��������� �� �������� " + FAILED);
		        	throw e;
		        }
	        }
	        Logger.log("��������� ��������� �� �������� " + STATUS);
		}
        catch (SQLException e) {        	
        	Logger.err(e.getMessage());
            e.printStackTrace();
        }						
	}
	
	private static void doQuery (Connection connection, String sql) throws SQLException {
		sql = sql.trim();
		Logger.log("\n" + sql + "\n");
		if (!sql.toUpperCase().startsWith("SELECT")) {
			connection.createStatement().executeUpdate(sql);
		}
		else {
			ResultSet result = connection.createStatement().executeQuery(sql);
			while (result.next()) {
				String stroke = "";
				ResultSetMetaData meta = result.getMetaData();				
				for (int i = 0; i < meta.getColumnCount(); i++) {					
					stroke += result.getRow() + ": " + result.getObject(i+1) + "  ||  ";					
				}
				Logger.log(stroke);
			}
		}					
	}
	
}
