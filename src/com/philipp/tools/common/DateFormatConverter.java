package com.philipp.tools.common;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class DateFormatConverter implements IStringConverter<Statics.DateFormat> {

	@Override
	public Statics.DateFormat convert(String value) {
		try {
			return Statics.DateFormat.valueOf(value.toUpperCase());
		}
		catch (IllegalArgumentException e) {
			throw new ParameterException("Uncorrect date format");
		}
	}

}
