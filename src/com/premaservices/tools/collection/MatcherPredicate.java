package com.premaservices.tools.collection;

import org.apache.commons.collections4.Predicate;

public class MatcherPredicate implements Predicate<String> {

	private String regex;
	
	public MatcherPredicate (String regex) {
		this.regex = regex;
	}
	
	@Override
	public boolean evaluate(String object) {
		
		if (object.matches(regex)) return true;						
		return false;
	}

}
