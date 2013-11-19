package com.philipp.tools.best.in;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

@Parameters(commandDescription = "xBase database connection")
public class XBaseCommand implements StdinCommand {
	
	public static final String NAME = "xbase";
	
	@Parameter(description = "Full qualified xbase catalog where *.dbf files are", required = true) 
	private List<String> dbf;
	
	@Parameter(names= {"-enc", "-encoding"}, description = "Database codepage")
	private String encoding = "cp1251";
	
	@ParametersDelegate
	private MetaArgs meta = new MetaArgs();

	public List<String> getDbf() {
		return dbf;
	} 	
	
	public String getOnlyDbf () {
		return getDbf().get(0);
	}

	@Override
	public MetaArgs getMeta() {	
		return meta;
	}

	@Override
	public String getMetaFacade() {
		return "XBase";
	}

	public String getEncoding() {
		return encoding;
	}

}
