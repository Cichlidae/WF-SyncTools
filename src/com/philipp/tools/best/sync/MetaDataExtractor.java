package com.philipp.tools.best.sync;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import com.philipp.tools.best.db.VFPDriverConnector;
import com.philipp.tools.best.log.Logger;

public class MetaDataExtractor {

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
			getDBFMeta(db);
		}
		else {
			System.err.println("�� ������ ����������� ���������: <���� � ���� ������>");
		}
	}
	
	private static void getDBFMeta (File db) {
		try {
			Connection connection = VFPDriverConnector.getODBCConnection(db.getAbsolutePath());
	        Logger.log("����������� � �� �������.");
	        Logger.log("=========================");
	        VFPDriverConnector.getDatabaseMetaData(connection);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
