package com.philipp.tools.best.args;

import java.io.File;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.beust.jcommander.converters.FileConverter;

public class StdinArgs {
	
	@Parameter(names = { "-v", "-verbose"}, description = "STD.ERR logging on/off") 
	public boolean verbose = false;

	@Parameter(names = "-xlsx", converter = FileConverter.class, description = "Excel file to output (needs excel-io-plugin.jar); input excel file if '-input' flag presents")
	public File excel;
	
	@Parameter(names = "-csv", converter = FileConverter.class, description = "CSV file to output; input csv file if '-input' flag presents")
	public File csv;

	@Parameter(names = "-rewrite", description = "Flag if excel/csv output (always create new file; used only with '-xlsx/-csv')") 
	public boolean rewrite = false;

	@Parameter(names = "-input", description = "Flag marked excel/csv input (used only with '-xlsx/-csv')", hidden = true) 
	public boolean input = false;

	@Parameter(names = {"-help", "-?"}, description = "Help", help = true) 
	public boolean help; 

	@Parameter(names = "-version", description = "Product version") 
	public boolean version;
	
	@ParametersDelegate
	public FormatArgs frmArgs = new FormatArgs(); 
	
}
