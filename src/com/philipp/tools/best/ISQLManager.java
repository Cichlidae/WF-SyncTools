package com.philipp.tools.best;

import com.philipp.tools.best.in.Input;
import com.philipp.tools.best.in.StdinCommand;

interface ISQLManager {
	
	public int process (StdinCommand incom, Input<String> in);
	
	public int processMeta (StdinCommand incom);

}
