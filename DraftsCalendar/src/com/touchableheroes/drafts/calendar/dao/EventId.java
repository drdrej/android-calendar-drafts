package com.touchableheroes.drafts.calendar.dao;

import android.net.Uri;
import android.util.Log;

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
	
	
	public boolean isValid() {
		return this.id != null;
	}
	
	public Long getId() {
		return id;
	}
	
	public Uri getUri() {
		return uri;
	}
	
}
