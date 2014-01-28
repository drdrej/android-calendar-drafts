package com.touchableheroes.drafts.calendar.cmd;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class ExistsEventCmd extends ContextCmd {

	public ExistsEventCmd(final Activity activity) {
		super(activity);
	}

	public boolean exec(final Uri idUri) {
		final ContentResolver cr = getContentResolver();
		final ContentValues values = new ContentValues();
		
		final Cursor query = cr.query(idUri, null, null, null, null);
		final int count = query.getCount();
		query.close();
		
		return (count > 0);
	}

}
