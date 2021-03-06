/** 
 * @author premaservices.com, 2013-11-07
 * @version 1.3.0.1 
 * 
 * */

package com.philipp.tools.common;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;
import com.philipp.tools.common.log.Logger;
import com.philipp.tools.jcconverters.GuidFormatConverter;
import com.premaservices.tools.jcvalidators.PositiveNonZeroInteger;

public class GuidGenerator {
	
	public static final String VERSION = "1.3.0.1";
	public static final String DESCRIPTION = "GuidGenerator by premaservices.com, version " + VERSION;
	
	//UTF-8 source char array (check your code page)
	private static final char[] CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	private static final char[] CHARS_RU = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ".toCharArray(); 	
	
	private static JCommander commander;
	
	public enum Format {
		GENERAL, MICROSOFT, BASE64, CUSTOM
	}
	
	@Parameter(names = {"-f", "-format"}, converter = GuidFormatConverter.class, description = "The format of GUID: GENERAL, MICROSOFT, BASE64, CUSTOM", required = true) 
	private Format format = Format.GENERAL;
	
	@Parameter(names = {"-l", "-len"}, validateWith = PositiveInteger.class, description = "The length of CUSTOM GUID (>0)") 
	private int length = 0;
	
	@Parameter(names = {"-s", "-seed"}, validateWith = PositiveInteger.class, description = "The seed for random generator (only for CUSTOM)")
	private Long seed;
	
	@Parameter(names = { "-ru"}, description = "Use russian symbols in GUID") 
	private boolean ru = false;
	
	@Parameter(names = {"-c"}, description = "Use only capitals in GUID") 
	private boolean capital = false;
	
	@Parameter(names = {"-n"}, validateWith = PositiveNonZeroInteger.class, description = "Count (N) of GUIDs in a sequence") 
	private int n = 1;
	
	@Parameter(names = {"-help", "-?"}, help = true) 
	private boolean help; 

	private GuidGenerator (String args[]) {			
		commander = new JCommander(this, args);
	}
	
	/**
	 * Console procedure to get different GUIDs.
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			GuidGenerator manager = new GuidGenerator(args);
			
			if (manager.help) {
				Logger.log(DESCRIPTION);
				commander.usage();
				System.exit(0);
				return;
			}					
			
			ArrayList<String> guids = new ArrayList<String>(manager.n);
			
			switch (manager.format) {
				case GENERAL: for (int i = 0; i < manager.n; i++) guids.add(getStandardGuid()); break;				
				case MICROSOFT: for (int i = 0; i < manager.n; i++) guids.add(getMicrosoftGuid()); break;
				case BASE64: for (int i = 0; i < manager.n; i++) guids.add(getBase64Guid()); break;
				case CUSTOM: {					
					if (manager.length > 0 && manager.seed != null) {
						getCustomGuidsWithSeed(manager.length, manager.ru, manager.seed, manager.n, guids);					
					}					
					else if (manager.length > 0) {
						for (int i = 0; i < manager.n; i++) guids.add(getCustomGuid(manager.length, manager.ru));	
					}	
					else if (manager.seed != null) {
						getCustomGuidsWithSeed(manager.seed, manager.n, guids);												
					}
					else {
						for (int i = 0; i < manager.n; i++) guids.add(getCustomGuid());					
					}					
					break;
				}
			}		
			
			for (String guid : guids) {
				if (manager.capital)
					guid = guid.toUpperCase();
				Logger.log(guid);
			}					
		}
		catch (Exception e) {			
			Logger.err(e);
			Logger.err("Try it with option -help.");
			System.exit(1);
			return;
		}									
		System.exit(0);
	}	

	/**
	 * Generate a standard RFC4122, version 4 ID by Java generator. 
	 * Example: "92329D39-6F5C-4520-ABFC-AAB64544E172"
	 * 
	 * @return a standard RFC4122 string GUID representation (36 chars)
	 */
	public static String getStandardGuid () {
		return UUID.randomUUID().toString();				
	}
	
	/**
	 * Generate a standard RFC4122, version 4 ID by Java generator in Microsoft manner. 
	 * Example: "{92329D39-6F5C-4520-ABFC-AAB64544E172}"
	 * 
	 * @return a standard RFC4122 string GUID representation (38 chars)
	 */
	public static String getMicrosoftGuid () {
		return "{" + getStandardGuid() + "}";
	}
	
	/**
	 * Generate a standard Java GUID encoded by base64.
	 * Example: "NA59hTjNTFybmYkaYxQ3yA"
	 * 
	 * @return string GUID representation (22 chars).
	 */
	public static String getBase64Guid () {

		UUID uuid = UUID.randomUUID();	

		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		byte[] ba = bb.array();
		return new String(Base64.encodeBase64(ba));
	}
	
	/**
     * Generate a RFC4122, version 4 ID. Example:
     * "92329D39-6F5C-4520-ABFC-AAB64544E172"
     * @author http://hashcode.ru
     */
	public static String getCustomGuid () {
		char[] uuid = new char[36];
        int r;

        //RFC4122 requires these characters
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';

        //Fill in random data. At i==19 set the high bits of clock sequence as per RFC4122, sec. 4.1.5
        for (int i = 0; i < 36; i++) {
            if (uuid[i] == 0) {
                r = (int) (Math.random()*16);
                uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf];
            }
        }
        return new String(uuid);
	}
	
	/**
     * Generate a RFC4122, version 4 ID. Example:
     * "92329D39-6F5C-4520-ABFC-AAB64544E172"
     * @author http://hashcode.ru
     */
	public static String getCustomGuidWithSeed (long seed) {
		char[] uuid = new char[36];
        int r;

        //RFC4122 requires these characters
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';
        
        Random random = new Random(seed);

        //Fill in random data. At i==19 set the high bits of clock sequence as per RFC4122, sec. 4.1.5
        for (int i = 0; i < 36; i++) {
            if (uuid[i] == 0) {
                r = random.nextInt();
                uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf];
            }
        }
        return new String(uuid);
	}
	
	public static String[] getCustomGuidsWithSeed (long seed, int n, Collection<String> c) {
		
		String[] guids = new String[n];
		
		Random random = new Random(seed);		                        
        for (int k = 0 ; k < n; k++) {
        	
        	char[] uuid = new char[36];
            int r;

            //RFC4122 requires these characters
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';
        	
	        //Fill in random data. At i==19 set the high bits of clock sequence as per RFC4122, sec. 4.1.5
	        for (int i = 0; i < 36; i++) {
	            if (uuid[i] == 0) {
	                r = random.nextInt();
	                uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf];
	            }
	        }
	        guids[k] = new String(uuid);
	        if (c != null) c.add(guids[k]);    
        }
        return guids;
	}
	
	/**
     * Generate a random uuid of the specified length. Example: uuid(15) returns "VcydxgltxrVZSTV"
     * @author http://hashcode.ru
     * 
     * @param len
     *            the desired number of characters
     */
	public static String getCustomGuid (int len) {
		return getCustomGuid(len, false);		
	}
	
	public static String getCustomGuid (int len, boolean ru) {
		return getCustomGuid(len, ru ? CHARS_RU.length : CHARS.length, ru);		
	}
	
	public static String[] getCustomGuidsWithSeed (int len, boolean ru, long seed, int n) {
		return getCustomGuidsWithSeed(len, ru ? CHARS_RU.length : CHARS.length, ru, seed, n, null);		
	}
	
	public static String[] getCustomGuidsWithSeed (int len, boolean ru, long seed, int n, Collection<String> c) {
		return getCustomGuidsWithSeed(len, ru ? CHARS_RU.length : CHARS.length, ru, seed, n, c);		
	}
	
	/**
     * Generate a random uuid of the specified length, and radix. Examples:
     * <ul>
     * <li>uuid(8, 2) returns "01001010" (8 character ID, base=2)
     * <li>uuid(8, 10) returns "47473046" (8 character ID, base=10)
     * <li>uuid(8, 16) returns "098F4D35" (8 character ID, base=16)
     * </ul>
     * @author http://hashcode.ru
     * 
     * @param len
     *            the desired number of characters
     * @param radix
     *            the number of allowable values for each character (must be <=62)
     */
	public static String getCustomGuid (int len, int radix, boolean ru) {
		
		int charsLength = ru ? CHARS_RU.length : CHARS.length;
		char[] charsArray = ru ? CHARS_RU : CHARS;
		
		if (radix > charsLength) {
            throw new IllegalArgumentException();
        }
        char[] uuid = new char[len];
        //Compact form
        for (int i = 0; i < len; i++) {
            uuid[i] = charsArray[(int)(Math.random()*radix)];
        }
        return new String(uuid);		
	}
	
	public static String getCustomGuidWithSeed (int len, int radix, boolean ru, long seed) {
		
		int charsLength = ru ? CHARS_RU.length : CHARS.length;
		char[] charsArray = ru ? CHARS_RU : CHARS;
		
		if (radix > charsLength) {
            throw new IllegalArgumentException();
        }
        char[] uuid = new char[len];        
        Random random = new Random(seed);
                
        //Compact form
        for (int i = 0; i < len; i++) {
        	double d = random.nextGaussian();  
        	d = d < 0 ? -d : d;
        	d = d >= 1 ? d - Math.round(d) : d;
        	d = d < 0 ? -d : d;        	
            uuid[i] = charsArray[(int)(d*radix)];
        }                
        return new String(uuid);		
	}
	
	public static String[] getCustomGuidsWithSeed (int len, int radix, boolean ru, long seed, int n, Collection<String> c) {
		
		Random random = new Random(seed);
		String[] guids = new String[n];
				
		int charsLength = ru ? CHARS_RU.length : CHARS.length;
		char[] charsArray = ru ? CHARS_RU : CHARS;
		
		if (radix > charsLength) {
            throw new IllegalArgumentException();
        }
		
		for (int k = 0 ; k < n; k++) {	
	        char[] uuid = new char[len];        
	        	                
	        //Compact form
	        for (int i = 0; i < len; i++) {
	        	double d = random.nextGaussian();  
	        	d = d < 0 ? -d : d;
	        	d = d >= 1 ? d - Math.round(d) : d;
	        	d = d < 0 ? -d : d;        	
	            uuid[i] = charsArray[(int)(d*radix)];
	        } 
	        guids[k] = new String(uuid);
	        if (c != null) c.add(guids[k]);	        
		}
        return guids;		
	}
	
}
