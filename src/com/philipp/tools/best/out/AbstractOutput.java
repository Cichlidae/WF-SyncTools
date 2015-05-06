package com.philipp.tools.best.out;

abstract class AbstractOutput implements Output {

	protected StdoutArgs outArgs = new StdoutArgs();
	
	protected AbstractOutput () {		
	}
	
	protected AbstractOutput (StdoutArgs outArgs) {	
		this.outArgs = outArgs;
	}
	
}
