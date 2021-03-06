package com.touchableheroes.drafts.calendar.util;

import java.util.ArrayList;

/**
 * Represents a "where" part of a query for content-provider.
 * 
 * @author A. Siebert, ask@touchableheroes.com
 */
public class WherePart {


	private final String where;
	private final String[] values;

	public WherePart(final String where, final ArrayList<String> values) {
		this.where = where;
		this.values = values.toArray(new String[0]);
	}

	public String[] values() {
		return values;
	}
	
	public String pattern() {
		return where;
	}
}
