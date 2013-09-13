package com.philipp.tools.best.sync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import com.philipp.tools.best.log.Logger;

public class EncodingTester {
	
	private final static int IN_PROCESS = 0;
	private final static int FAILED = 1;
	private final static int TERMINATED = 2;
	
	private static int STATUS = IN_PROCESS;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
		
			System.out.println(System.getProperty("file.encoding"));
			System.out.println(System.getProperty("console.encoding"));
			
			BufferedReader scan =
					new BufferedReader(new InputStreamReader(System.in,"utf-8"));
						
			 while (STATUS == IN_PROCESS) {	       		       		        			    	       
		        String text1 = scan.readLine();
		        if (text1.toLowerCase().compareTo("exit") == 0) {
		        	STATUS = TERMINATED;
		        	break;
		        }
		        
		        byte[] ba = text1.getBytes();
				for (byte b : ba) {
					//System.out.println(b);
					System.out.printf("%02X", b);
					System.out.print(" ");
				}
				System.out.println();
				
				byte[] bu = text1.getBytes("cp1251");
				
				for (byte b : bu) {
					//System.out.println(b);		
					System.out.printf("%02X", b);
					System.out.print(" ");
				}
				System.out.println();
				
				System.out.println(text1);
				System.out.println(new String(bu));
				        		
		     }
		
		}
		catch (UnsupportedEncodingException e) {}
		catch (IOException e) {}

	}
	
/*	public static String utf8_encode (String string) {
		
        String utftext = "";
 
        for (int n = 0; n < string.length(); n++) {
 
            int c = string.codePointAt(n);
 
            if (c < 128) {
                utftext += (char)c;
            }
            else if((c > 127) && (c < 2048)) {
            	
            	System.out.println("********* " + c + " ** " + ((c >> 6) | 192));
            	
            	
                utftext += (char)((c >> 6) | 192);
                utftext += (char)((c & 63) | 128);
            }
            else {
                utftext += (char)((c >> 12) | 224);
                utftext += (char)(((c >> 6) & 63) | 128);
                utftext += (char)((c & 63) | 128);
            }
 
        }
 
        return utftext;
    }
	
	public static String utf8_decode (String utftext) {
        String string = "";
        int i = 0;
        int c = 0; int c1 = 0; int c2 = 0; int c3 = 0;
 
        while ( i < utftext.length() ) {
 
            c = utftext.codePointAt(i);
 
            if (c < 128) {
                string += (char)(c);
                i++;
            }
            else if((c > 191) && (c < 224)) {
                c2 = utftext.codePointAt(i+1);
                string += (char)(((c & 31) << 6) | (c2 & 63));
                i += 2;
            }
            else {
                c2 = utftext.codePointAt(i+1);
                c3 = utftext.codePointAt(i+2);
                string += (char)(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
 
        }
 
        return string;
    }


*/

}
