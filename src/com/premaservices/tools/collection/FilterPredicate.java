package com.premaservices.tools.collection;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang.StringUtils;

public class FilterPredicate implements Predicate<String> {

	private String[] filters;
	private boolean restrict;
	
	public FilterPredicate (String filterStr) {
		this(filterStr, false);
	}
	
	public FilterPredicate (String filterStr, boolean restrict) {
		
		this.filters = StringUtils.split(filterStr);
		this.restrict = restrict;
		
	}
	
	@Override
	public boolean evaluate(String object) {
		
		if (restrict) {
			for (String filter : filters) {
				if (object.compareTo(filter) == 0) return true;
			}									
		}
		else {
			for (String filter : filters) {
				MatcherPredicate matcher = new MatcherPredicate(filter);
				if (matcher.evaluate(object)) return true;
			}			
		}						
		return false;
	}

}
