package com.philipp.tools.common.log;

import java.io.UnsupportedEncodingException;

public class Logger {
	
	public static final String DB_ENCODING = "cp1251";
	public static String CONSOLE_ENCODING = System.getProperty("console.encoding","utf-8");
	public static String FILE_ENCODING = System.getProperty("file.encoding","utf-8");
	public static boolean DEBUG_ON = false;
			
	private Logger () {
	}
	
	public static void log (String message) {	
		try {
			if (CONSOLE_ENCODING.toLowerCase().compareTo(DB_ENCODING) != 0) {			
				System.out.println(new String(message.getBytes(CONSOLE_ENCODING)));
				return;
			}
		}
		catch (UnsupportedEncodingException e) {			
		}
		System.out.println(message);	
	}
	
	public static void log (int message) {	
		System.out.println(message);
	}
	
	public static void log (double message) {	
		System.out.println(message);
	}
	
	public static void err (String message) {		
		try {
			if (CONSOLE_ENCODING.toLowerCase().compareTo(DB_ENCODING) != 0) {	
				System.err.println(new String(message.getBytes(CONSOLE_ENCODING)));
				return;
			}
		}
		catch (UnsupportedEncodingException e) {			
		}
		System.err.println(message);		
	}
	
	public static void err (int message) {
		System.err.println(message);
	}
	
	public static void err (double message) {
		System.err.println(message);
	}
	
	public static void err (Exception e) {		
		e.printStackTrace();
	}
	
	public static void debug (String message) {
		if (!DEBUG_ON) return;
		Logger.err(message);
	}
	
	public static void debug (int message) {
		if (!DEBUG_ON) return;
		Logger.err(message);
	}
	
	public static void debug (double message) {
		if (!DEBUG_ON) return;
		Logger.err(message);
	}

}
