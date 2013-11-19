package com.philipp.tools.check;

import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.philipp.tools.common.log.Logger;

public class MD5CheckSum {
	
	private static JCommander commander;
	
	@Parameter(description="Full qualified filenames to compare md5", required = true) 
	private List<String> filenames;

	@Parameter(names = {"-help", "-?"}, help = true, hidden = true) 
	private boolean help; 
			
	private MD5CheckSum (String[] args) throws ParameterException {	
		commander = new JCommander(this, args);				
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
										
		try {
			MD5CheckSum manager = new MD5CheckSum(args); 
						
			if (manager.help) {
				commander.usage();
				System.exit(0);
				return;
			}
			
			if (manager.filenames.size() <= 1) {
				Logger.err("Needs two or more files to compare.");
				System.exit(1);
				return;
			}
			
			String[] fs = new String[manager.filenames.size()];
			if (!equalFiles(manager.filenames.toArray(fs))) {
				System.exit(1);
			}
			else {
				System.exit(0);
			}						
		}
		catch (Exception e) {
			Logger.err(e.getMessage());
			System.exit(1);
			return;
		}
		
	}
	
	public static boolean equalFiles (String[] filenames) throws Exception {
		
		boolean result = true;
		byte[] etalon = createFileChecksum(filenames[0]);
		Logger.log("Etalon:\t" + getFileMD5Checksum(etalon) + "\t" + filenames[0]);
		
		for (int i = 1; i < filenames.length; i ++) {
			byte[] md5 = createFileChecksum(filenames[i]);
			if (!Arrays.equals(etalon, md5)) {
				Logger.log("FAIL:\t" + getFileMD5Checksum(md5) + "\t" + filenames[i]);
				result = false;
			}	
			else {
				Logger.log("OK:  \t" + getFileMD5Checksum(md5) + "\t" + filenames[i]);
			}
		}		
		return result;				
	}
	
	public static byte[] createFileChecksum (String filename) throws Exception {
		
		InputStream fis =  new FileInputStream(filename);
		
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;
		
		do {
		    numRead = fis.read(buffer);
		    if (numRead > 0) {
		        complete.update(buffer, 0, numRead);
		    }
		} while (numRead != -1);
		
		fis.close();
		return complete.digest();
    }

	public static String getFileMD5Checksum (String filename) throws Exception {	
		return getFileMD5Checksum(createFileChecksum(filename));
    }
	
	public static String getFileMD5Checksum (byte[] md5) throws Exception {
			
		String result = "";
	
		for (int i=0; i < md5.length; i++) {
			result += Integer.toString( ( md5[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
    }
		
}
