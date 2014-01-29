package com.touchableheroes.drafts.calendar.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import android.content.ContentValues;
import android.provider.CalendarContract.Events;

/**
 * Simple Event-implementation.
 * 
 * @author A. Siebert, ask@touchableheroes.com
 */
public class Event {

	private final Map<String, Object> map = new HashMap<String, Object>();
	private EventId id;

	public void setString(final String key, final String val) {
		map.put(key, val);
	}

	public void setLong(final String key, final long val) {
		map.put(key, val);
	}

	public EventId setId(final long id) {
		this.id = EventId.create(id);
		return this.id;
	}

	public EventId getId() {
		return this.id;
	}

	/**
	 * Factory-method to build content-values for update, based on this
	 * instance. At this moment supports only START-END-events.
	 * 
	 * @return never NULL
	 */
	public ContentValues newUpdateContentValues() {
		final ContentValues values = new ContentValues();

		addContentValueLong(values, "calendarID", Events.CALENDAR_ID);
		addContentValueLong(values, "start", Events.DTSTART);
		addContentValueLong(values, "end", Events.DTEND);

		addContentValue(values, "title", Events.TITLE);
		addContentValue(values, "description", Events.DESCRIPTION);

		// append some default values:
		final TimeZone zone = TimeZone.getDefault();
		values.put(Events.EVENT_TIMEZONE, zone.getID());
		values.put(Events.ALL_DAY, 0);
		values.remove(Events._ID);

		return values;
	}

	/**
	 * Factory-method to build content-values for update, based on new values.
	 * 
	 * @return never NULL
	 */
	public ContentValues newUpdateContentValues(final String newTitle,
			final long newBegin, final long newEnd,
			final String newDescription, final String newLocation) {
		final ContentValues values = new ContentValues();
		
		// addContentValueLong(values, "calendarID", Events.CALENDAR_ID);
		
		if( newBegin > 0 )
		  values.put(Events.DTSTART, newBegin);
		  
		if( newEnd > 0)
			values.put(Events.DTEND, newEnd);

		if( newTitle != null )
			values.put(Events.TITLE, newTitle);
		
		if( newDescription != null )
			values.put(Events.DESCRIPTION, newDescription);
		
		// append some default values:
		final TimeZone zone = TimeZone.getDefault();
		values.put(Events.EVENT_TIMEZONE, zone.getID());
		values.put(Events.ALL_DAY, 0);
		values.remove(Events._ID);

		return values;
	}

	public void addContentValue(final ContentValues values, final String key,
			final String cursorKey) {
		final String val = string(key);

		if (val != null)
			values.put(cursorKey, val);
	}

	public void addContentValueLong(final ContentValues values,
			final String key, final String cursorKey) {
		final Long val = (Long) this.map.get(key);

		if (val != null)
			values.put(cursorKey, val);
	}

	public void addContentValueInt(final ContentValues values,
			final String key, final String cursorKey) {
		final Integer val = (Integer) this.map.get(key);

		if (val != null)
			values.put(cursorKey, val);
	}

	public String string(final String key) {
		Object val = this.map.get(key);

		if (val == null)
			return null;

		return String.valueOf(val);
	}

	public Long longVal(final String key) {
		Object val = this.map.get(key);

		if (val == null)
			return null;

		return (Long) val;
	}

	public Integer intVal(final String key) {
		Object val = this.map.get(key);

		if (val == null)
			return null;

		return (Integer) val;
	}
}
