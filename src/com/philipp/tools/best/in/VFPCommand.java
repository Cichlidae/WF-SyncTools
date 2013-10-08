package com.philipp.tools.best.in;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "VFP database connection")
public class VFPCommand {
	
	@Parameter(description="Full qualified foxpro database name (*.dbc)", required = true) 
	private List<String> dbc;

	public List<String> getDbc() {
		return dbc;
	} 	
	
}
