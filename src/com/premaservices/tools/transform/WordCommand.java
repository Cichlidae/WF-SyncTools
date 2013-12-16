package com.premaservices.tools.transform;

import java.io.File;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;

@Parameters(commandDescription = "Word file transformation")
public class WordCommand {

	public static final String NAME = "word";
	
	public enum Format {
		HTML, FO
	}

	@Parameter(names = {"-src", "-source"}, converter = WordTransformerConverter.class, description = "Word file name", required = true) 
	private WordTransformer word;

	@Parameter(names = {"-dst", "-destination"}, converter = FileConverter.class, description = "Desctination dir") 
	private File dir;

	@Parameter(names = {"-to", "-format"}, converter = WordCommandFormatConverter.class, description = "Destination format", required = true) 
	private Format format;

	public WordTransformer getWord() {
		return word;
	}

	public File getDir() {
		return dir;
	}

	public Format getFormat() {
		return format;
	}		

}
