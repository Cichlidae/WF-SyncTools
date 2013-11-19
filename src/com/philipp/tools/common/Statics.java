package com.philipp.tools.common;

import java.text.SimpleDateFormat;

import com.philipp.tools.common.log.Logger;

public final class Statics {
	
	private Statics () {}
	
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	
	public static boolean isJvmArch64 () {	
		String jvmArch = System.getProperty("sun.arch.data.model");
		if (jvmArch.contains("64")) {
			Logger.err("You have JVM 64-bit installed by default, but it needs JVM 32-bit for correct work.");
			return true;
		}	
		return false;		
	}

}
