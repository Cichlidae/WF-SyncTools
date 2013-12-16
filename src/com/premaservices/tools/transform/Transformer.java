package com.premaservices.tools.transform;

import java.io.File;
import java.io.FileOutputStream;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.philipp.tools.common.log.Logger;

public class Transformer {
	
	public static final String VERSION = "1.0.RC0";
	public static final String DESCRIPTION = "FORMAT TRANSFORMER v" + VERSION;
	
	private static JCommander commander;
	
	private static WordCommand wordCommand = new WordCommand();
	
	@Parameter(names = { "-v", "-verbose"}, description = "STD.ERR logging on/off") 
	public boolean verbose = false;
	
	@Parameter(names = {"-help", "-?"}, description = "Help", help = true) 
	public boolean help; 

	@Parameter(names = "-version", description = "Product version") 
	public boolean version;
	
	private Transformer (String[] args) {
		commander = new JCommander(this);
		commander.addCommand(WordCommand.NAME, wordCommand);
		commander.parse(args);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {				
		
		try {						
			Transformer transformer = new Transformer(args);
			
			if (transformer.verbose) {
				Logger.DEBUG_ON = true;
			}
			
			if (transformer.help) {
				commander.usage();
				System.exit(0);
				return;
			}
			
			if (transformer.version) {
				Logger.log(DESCRIPTION);
				System.exit(0);
				return;
			}
			
			if (commander.getParsedCommand().compareTo(WordCommand.NAME) == 0) {	
				WordCommand.Format format = wordCommand.getFormat();
				WordTransformer wt = wordCommand.getWord();
				String filename = createFilename(wt.getWord(), wordCommand.getDir(), format.toString());
				
				Logger.debug(filename);
				
				FileOutputStream out = new FileOutputStream(filename);				
				wt.transform(format, out);																
			}						
			
		}		
		catch (Exception e) {
			Logger.err(e.toString());
			Logger.err(e);
			System.exit(1);
			return;
		}
		
	}
	
	private static String createFilename (File source, File dir, String extention) {
		
		String filename = source.getName();
		String filedir = source.getParent();
		String newName = filename; 
		
		int idx = filename.lastIndexOf(".");
		if (idx > -1) {
			newName = filename.substring(0, idx);
		}	
		newName += "." + extention.toLowerCase();
		
		return dir != null ? dir.getAbsolutePath() + File.separator + newName : filedir + File.separator + newName;									
	}

}
