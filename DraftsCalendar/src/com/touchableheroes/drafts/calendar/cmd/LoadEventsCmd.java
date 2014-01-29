package com.touchableheroes.drafts.calendar.cmd;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract.Events;

import com.touchableheroes.drafts.calendar.cursor.GetValueFacade;
import com.touchableheroes.drafts.calendar.dao.Event;
import com.touchableheroes.drafts.calendar.util.WherePart;
import com.touchableheroes.drafts.calendar.util.WhereQueryPartBuilder;

/**
 * Loads some events based on passed arguments.
 * If an argument is null, it will be ignored.
 * 
 * @author A. Siebert, ask@touchableheroes.com
 */
@TargetApi(14)
public class LoadEventsCmd extends ContextCmd {

	public LoadEventsCmd(final Context ctx) {
		super(ctx);
	}
	
	/**
	 * 
	 * @param title if null - ignored
	 * @param begin start-date (as millis) of an event. -1 means this value will be ignored in the query.
	 * @param end end-date (as millis) of an event. -1 means this value will be ignored in the query.
	 * @param location if null - ignored
	 * @param description if null - ignored
	 * @return
	 */
	public List<Event> exec(final String title, final long begin, final long end, final String location, final String description) {
		final ContentResolver cr = getContentResolver();

		final WherePart where = WhereQueryPartBuilder.createForEvents(title, begin, end, location, description);
		final String[] projection = { Events.TITLE, Events._ID, Events.DTSTART, Events.DTEND, Events.DESCRIPTION };
		
		final Cursor cursor = cr.query(Events.CONTENT_URI, projection, where.pattern(),
				where.values(), null);
		
		final int count = cursor.getCount();

		final ArrayList<Event> events = new ArrayList<Event>(count);
		final GetValueFacade get = new GetValueFacade(cursor, events);
		
		while (cursor.moveToNext()) {
			get.string( "title", Events.TITLE );
			get.longVal( "start", Events.DTSTART );
			get.id(Events._ID);
			
			get.longVal( "end", Events.DTEND );
			get.string( "description", Events.DESCRIPTION );
			
			get.push();
		}
		
		cursor.close();
		
		return events;
	}

}
