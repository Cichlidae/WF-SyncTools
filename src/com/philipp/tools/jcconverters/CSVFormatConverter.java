package com.philipp.tools.jcconverters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import com.philipp.tools.common.Statics;
import com.philipp.tools.common.Statics.CSVFormat;

public class CSVFormatConverter implements IStringConverter<Statics.CSVFormat> {

	@Override
	public Statics.CSVFormat convert(String value) {
		try {		
			return Statics.CSVFormat.valueOf(value.toUpperCase());
		}
		catch (IllegalArgumentException e) {
			throw new ParameterException("Uncorrect csv format");
		}
	}

}
