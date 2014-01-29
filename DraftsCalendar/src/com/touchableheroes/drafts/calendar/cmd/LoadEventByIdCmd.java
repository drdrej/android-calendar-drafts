package com.touchableheroes.drafts.calendar.cmd;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Events;

import com.touchableheroes.drafts.calendar.cursor.GetValueFacade;
import com.touchableheroes.drafts.calendar.dao.Event;

/**
 * @author A. Siebert, ask@touchableheroes.com
 */
public class LoadEventByIdCmd extends ContextCmd {

	public LoadEventByIdCmd(Context ctx) {
		super(ctx);
	}

	
	public Event exec( final Uri id) {
		final ContentResolver cr = getContentResolver();
		final String[] projection = { Events.TITLE, Events._ID, Events.DTSTART, Events.DTEND, Events.DESCRIPTION };
		
		final Cursor cursor = cr.query(id, projection, null,
				null, null);
		
		final GetValueFacade get = new GetValueFacade(cursor, null);
		
		Event rval = null;
		if (cursor.moveToNext()) {
			get.id(Events._ID);
			
			get.string( "title", Events.TITLE );
			get.longVal( "start", Events.DTSTART );
			get.longVal( "end", Events.DTEND );

			get.string( "description", Events.DESCRIPTION );
			
			rval = get.push();
		}
		
		cursor.close();
		
		return rval;
	}
}
