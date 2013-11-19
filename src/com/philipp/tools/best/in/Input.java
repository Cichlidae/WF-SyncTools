package com.philipp.tools.best.in;

import java.util.ArrayList;
import java.util.List;

public abstract class Input<T> {		
	
	private List<InputListener<T>> listeners = new ArrayList<InputListener<T>>(1);
	
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
