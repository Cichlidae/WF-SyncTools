package com.philipp.tools.best.in;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Excel file connection")
public class ExcelCommand implements StdinCommand {
	
	public static final String NAME = "excel";
	
	@Parameter(description="Full qualified excel file name (*.xlsx, *.xls)", required = true) 
	private List<String> xlsx;

	public List<String> getXlsx() {
		return xlsx;
	}
	
	public String getOnlyXlsx() {
		return getXlsx().get(0);
	}

	@Override
	public MetaArgs getMeta() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMetaFacade() {
		// TODO Auto-generated method stub
		return null;
	}

}
