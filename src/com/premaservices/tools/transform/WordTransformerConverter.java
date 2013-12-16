package com.premaservices.tools.transform;

import java.io.File;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class WordTransformerConverter implements IStringConverter<WordTransformer> {

	@Override
	public WordTransformer convert(String value) {
		
		WordTransformer transformer = null;
		try {
			transformer = new WordTransformer(new File(value));
		}
		catch (Exception e) {
			throw new ParameterException(e);						
		}	
		return transformer;
	}

}
