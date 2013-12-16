package com.premaservices.tools.transform;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import com.premaservices.tools.transform.WordCommand.Format;

public class WordCommandFormatConverter implements IStringConverter<WordCommand.Format> {

	@Override
	public Format convert(String value) {
		try {
			return WordCommand.Format.valueOf(value);
		}
		catch (IllegalArgumentException e) {
			throw new ParameterException("Uncorrect destination format");
		}
	}

}
