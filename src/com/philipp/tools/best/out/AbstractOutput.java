package com.philipp.tools.best.out;

import com.philipp.tools.best.args.FormatArgs;

abstract class AbstractOutput implements Output {

	protected FormatArgs outArgs = new FormatArgs();
	
	protected AbstractOutput () {		
	}
	
	protected AbstractOutput (FormatArgs outArgs) {	
		if (outArgs != null) 
			this.outArgs = outArgs;
	}
	
}
