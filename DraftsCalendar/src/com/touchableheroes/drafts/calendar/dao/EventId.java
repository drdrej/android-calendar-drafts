package com.touchableheroes.drafts.calendar.dao;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.CalendarContract.Events;
import android.util.Log;

/**
 * Basic class to work with calendar-event-ids.
 * 
 * @author A.Siebert, ask@touchableheroes.com
 */
public class EventId {

	private Long id;
	private final Uri uri;

	public EventId(final Uri uri) {
		this.uri = uri;
		
		try {
			final String idStr = uri.getLastPathSegment();
			this.id = Long.parseLong(idStr);
		} catch (final Throwable x) {
			Log.e( "calendar-drafts", "couldn't parse uri. Not found id. uri = " + uri );
			this.id = null;
		}
	}
	
	
	private EventId(final long id) {
		this.uri = createUri(id);
		this.id = id;
	}


	public boolean isValid() {
		return this.id != null;
	}
	
	public Long getId() {
		return id;
	}
	
	public Uri getUri() {
		return uri;
	}


	@TargetApi(14)
	private static Uri createUri_14(long id) {
		return ContentUris.withAppendedId(Events.CONTENT_URI, id);
	}



	public static Uri createUri(long id) {
		return createUri_14(id);
	}

	/**
	 * Factory-method to create an EventId.
	 * 
	 * @param id positive number. internal db id.
	 * @return instance or null.
	 */
	public static EventId create(long id) {
		if( id < 1 )
			return null;
		
		return new EventId(id);
	}
	
}
