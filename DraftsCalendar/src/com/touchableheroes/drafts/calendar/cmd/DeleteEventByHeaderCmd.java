package com.touchableheroes.drafts.calendar.cmd;

import java.util.ArrayList;

import com.touchableheroes.drafts.calendar.dao.EventId;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Events;
import android.util.Log;

/**
 * @author asiebert
 */
@TargetApi(14)
public class DeleteEventByHeaderCmd extends DeleteEventCmd {

	public DeleteEventByHeaderCmd(Context ctx) {
		super(ctx);
	}

	public boolean exec(final String title, final long begin, final long end) {
		final ContentResolver cr = getContentResolver();

		final String where = Events.TITLE + " = ? ";
		final String[] projection = { Events.TITLE, Events._ID };
		final String[] values = new String[] { title };

		final Cursor cursor = cr.query(Events.CONTENT_URI, projection, where,
				values, null);
		
		final int count = cursor.getCount();

		final ArrayList<Uri> ids = new ArrayList<Uri>();
		while (cursor.moveToNext()) {
			final int idIdx = cursor.getColumnIndex(Events._ID);
			final int titleIdx = cursor.getColumnIndex(Events.TITLE);
		   
			Log.d("calendar-drafts", "-- ## title : " + cursor.getString(titleIdx));
			final long id = cursor.getLong(idIdx);
			final Uri idUri = EventId.createUri(id);
			
			ids.add(idUri);
		}
		
		cursor.close();
		
		int deletedSum = 0;
		for( final Uri idUri : ids ) {
			final int deleted = cr.delete(idUri, null, null);
			deletedSum += deleted;
		}
		
		return (count == deletedSum);
	}

}
