package com.philipp.tools.best.in;

import java.util.ArrayList;
import java.util.List;

import com.philipp.tools.best.args.FormatArgs;

public abstract class Input<T> {		
	
	private List<InputListener<T>> listeners = new ArrayList<InputListener<T>>(1);
	protected FormatArgs inArgs = new FormatArgs();
	
	protected Input () {		
	}
	
	protected Input (FormatArgs inArgs) {	
		if (inArgs != null) 
			this.inArgs = inArgs;
	}
	
	public void addListener (InputListener<T> l) {
		listeners.add(l);
	}
	
	public void removeListener (InputListener<T> l) {
		listeners.remove(l);
	}
	
	public void notifyListeners (T[] arg) throws Exception {
		for (InputListener<T> l : listeners) {
			l.doEvent(arg);
		}
	}
		
	public abstract void convert (String id, StdinCommand c) throws Exception;

}
