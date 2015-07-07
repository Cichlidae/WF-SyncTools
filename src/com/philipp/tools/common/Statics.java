package com.philipp.tools.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.philipp.tools.common.log.Logger;

public final class Statics {
	
	private Statics () {}
	
	public static final String VERSION = "1.3.RC1";
	
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	public static final ODBCDateFormat ODBC_DATE_FORMATTER = new ODBCDateFormat();
	
	public static enum DateFormat {
		ISO8601, ODBC
	}
	
	public static enum HeaderFlag {
		ON, OFF, FIRST
	}
	
	public static enum CSVFormat {
		EXCEL, TDF, MYSQL
	}
	
	public static boolean isJvmArch64 () {	
		String jvmArch = System.getProperty("sun.arch.data.model");
		if (jvmArch.contains("64")) {
			Logger.err("You have JVM 64-bit installed by default, but it needs JVM 32-bit for correct work.");
			return true;
		}	
		return false;		
	}
	
	public static boolean isDateFormatted (String str) {
		
		try {
			DATE_FORMATTER.parse(str);
			return true;
		}
		catch (ParseException e) {
			return false;
		}	
	}
	
	public static Date getJavaDate (String str) throws ParseException {		
		return DATE_FORMATTER.parse(str);		
	}

}
