package com.touchableheroes.drafts.calendar.cursor;

import java.util.List;

import com.touchableheroes.drafts.calendar.dao.Event;
import com.touchableheroes.drafts.calendar.dao.EventId;

import android.database.Cursor;
import android.net.Uri;

/**
 * 
 * @author A. Siebert, ask@touchableheroes.com
 */
public class GetValueFacade {

	private final Cursor cursor;
	private final List<Event> buffer;
	private Event current;

	public GetValueFacade(Cursor cursor, final List<Event> buffer) {
		this.cursor = cursor;

		if (buffer != null)
			this.buffer = buffer;
		else
			this.buffer = null;

		this.current = new Event();
	}

	/**
	 * pushs current event to buffer and creates new one for the next push()
	 * call. if buffer is not assigned, this method will skip the push()
	 * handling.
	 * 
	 * @return current is a current event
	 */
	public Event push() {
		if (this.buffer == null)
			return current;

		this.buffer.add(current);
		final Event rval = current;
		current = new Event();

		return rval;

	}

	public String string(final String key, final String cursorKey) {
		final int idx = this.cursor.getColumnIndex(cursorKey);
		final String val = this.cursor.getString(idx);
		current.setString(key, val);

		return null;
	}

	public long longVal(final String key, final String cursorKey) {
		final int idx = this.cursor.getColumnIndex(cursorKey);
		final long val = this.cursor.getLong(idx);
		current.setLong(key, val);

		return val;
	}

	/**
	 * assign id to current event.
	 * 
	 * @param id internal event id, used by calendar-content-provider (@see Events._ID)
	 * @return instance of EventId or null.
	 */
	public EventId id(final String cursorKey) {
		final int idx = this.cursor.getColumnIndex(cursorKey);
		final long id = this.cursor.getLong(idx);
		
		return current.setId( id );
	}

}
