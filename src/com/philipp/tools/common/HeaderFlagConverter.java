package com.philipp.tools.common;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class HeaderFlagConverter implements IStringConverter<Statics.HeaderFlag> {

	@Override
	public Statics.HeaderFlag convert(String value) {
		try {
			if (value.trim().compareTo("1") == 0) value = "first";			
			return Statics.HeaderFlag.valueOf(value.toUpperCase());
		}
		catch (IllegalArgumentException e) {
			throw new ParameterException("Uncorrect date format");
		}
	}

}
