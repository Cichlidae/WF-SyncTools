package com.philipp.tools.jcconverters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import com.philipp.tools.common.GuidGenerator;
import com.philipp.tools.common.GuidGenerator.Format;

public class GuidFormatConverter implements IStringConverter<GuidGenerator.Format> {

	@Override
	public Format convert(String value) {
		try {
			return GuidGenerator.Format.valueOf(value.toUpperCase());
		}
		catch (IllegalArgumentException e) {
			throw new ParameterException("Uncorrect guid format");
		}
	}

}
