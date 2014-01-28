package com.touchableheroes.drafts.calendar.cmd;

import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract.Events;

/**
 * @author A. Siebert / ask@touchableheroes.com
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class InsertEventCmd extends ContextCmd {

	public InsertEventCmd(final Activity ctx) {
		super(ctx);
	}
	

	@SuppressLint("InlinedApi")
	public Uri exec(final int calenderId, final long start, final long end, final String title, final String description) {
		final ContentResolver cr = getContentResolver();
		
		final ContentValues values = new ContentValues();
		
		values.put(Events.CALENDAR_ID, calenderId);
		
		values.put(Events.DTSTART, start);
		values.put(Events.DTEND, end);
		
		values.put(Events.ALL_DAY, 0);
		
		values.put(Events.TITLE, title);
		values.put(Events.DESCRIPTION, description);
		
		final TimeZone zone = TimeZone.getDefault();
		values.put(Events.EVENT_TIMEZONE, zone.getID());
		
		return cr.insert(Events.CONTENT_URI, values);
	}
}
