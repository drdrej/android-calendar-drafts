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

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class InsertRepeatableEventCmd extends ContextCmd {

	public InsertRepeatableEventCmd(final Activity ctx) {
		super(ctx);
	}

	/**
	 * @param calenderId
	 * @param start
	 * @param end
	 * @param title
	 * @param description
	 * @param rrule
	 * @param duration
	 * 
	 * @return
	 */
	@SuppressLint("InlinedApi")
	public Uri exec(final int calenderId, final long start, final long end,
			final String title, final String description, final String rrule,
			final String duration) {
		final ContentResolver cr = getContentResolver();

		final ContentValues values = new ContentValues();

		values.put(Events.CALENDAR_ID, calenderId);

		values.put(Events.DTSTART, start);
		values.put(Events.DTEND, end);

		if (!(rrule == null || rrule.length() < 1)) {
			values.put(Events.RRULE, rrule);
			values.put(Events.DURATION, duration);
		}

		values.put(Events.ALL_DAY, 0);

		values.put(Events.TITLE, title);
		values.put(Events.DESCRIPTION, description);

		final TimeZone zone = TimeZone.getDefault();
		values.put(Events.EVENT_TIMEZONE, zone.getID());

		return cr.insert(Events.CONTENT_URI, values);
	}
}

// intent.putExtra("allDay", true);
// intent.putExtra("rrule", "FREQ=YEARLY");
//
// values.put(Events.RRULE,
// 17
// "FREQ=DAILY;COUNT=20;BYDAY=MO,TU,WE,TH,FR;WKST=MO");
//
// }
