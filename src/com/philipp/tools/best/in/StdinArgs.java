package com.philipp.tools.best.in;

import java.io.File;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.beust.jcommander.converters.FileConverter;
import com.philipp.tools.best.out.StdoutArgs;

public class StdinArgs {
	
	@Parameter(names = { "-v", "-verbose"}, description = "STD.ERR logging on/off") 
	public boolean verbose = false;

	@Parameter(names = "-xlsx", converter = FileConverter.class, description = "Excel file to output (needs excel-io-plugin.jar); input excel file if '-input' flag presents")
	public File excel;

	@Parameter(names = "-rewrite", description = "Flag if excel output (always create new file; used only with '-xlsx')") 
	public boolean rewrite = false;

	@Parameter(names = "-input", description = "Flag marked excel input (used only with '-xlsx')", hidden = true) 
	public boolean input = false;

	@Parameter(names = {"-help", "-?"}, description = "Help", help = true) 
	public boolean help; 

	@Parameter(names = "-version", description = "Product version") 
	public boolean version;
	
	@ParametersDelegate
	public StdoutArgs outArgs = new StdoutArgs(); 
	
}
