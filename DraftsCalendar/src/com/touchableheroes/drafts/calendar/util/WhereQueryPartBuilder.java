package com.touchableheroes.drafts.calendar.util;

import java.util.ArrayList;

import android.provider.CalendarContract.Events;

/**
 * simplifies the query-building for events and calendar-content-provider.
 * 
 * @author A. Siebert, ask@touchableheroes.com
 */
public class WhereQueryPartBuilder {

	public static WherePart createForEvents(final String title,
			final long begin, final long end, final String location,
			final String description) {
		final StringBuilder builder = new StringBuilder();
		final ArrayList<String> values = new ArrayList<String>(5);

		appendWhere(builder, Events.TITLE, title, values);
		appendWhereLong(builder, Events.DTSTART, begin, values);
		appendWhereLong(builder, Events.DTEND, end, values);
		appendWhere(builder, Events.DESCRIPTION, description, values);

		// TODO: add some other features later!
		// appendWhere(builder, Events.LOCATION, location, values);

		return new WherePart(builder.toString(), values);
	}

	private static void appendWhereLong(StringBuilder builder, String key,
			long value, ArrayList<String> values) {
		final String valStr = String.valueOf(value);
		
		appendWhere(builder, key, valStr, values);
	}

	private static void appendWhere(final StringBuilder builder,
			final String key, final String value, final ArrayList<String> values) {
		if (value == null)
			return;

		if (builder.length() > 0)
			builder.append(" AND ");

		builder.append(key);
		builder.append(" = ? ");

		values.add(value);
	}

}