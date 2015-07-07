package com.philipp.tools.jcconverters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import com.philipp.tools.common.Statics;
import com.philipp.tools.common.Statics.HeaderFlag;

public class HeaderConverter implements IStringConverter<Statics.HeaderFlag> {

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
