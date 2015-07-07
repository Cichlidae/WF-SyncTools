package com.philipp.tools.jcconverters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class DelimiterConverter implements IStringConverter<Character> {

	@Override
	public Character convert(String value) {
		if (value.length() == 1) return Character.valueOf(value.charAt(0));
		else throw new ParameterException("Uncorrect delimiter, need to be a single sign");
	}

}
