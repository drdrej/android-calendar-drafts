package com.touchableheroes.drafts.calendar.cmd;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * deletes an event by id/uri.
 *  
 * @author asiebert
 */
public class DeleteEventCmd extends ContextCmd {

	public DeleteEventCmd(final Context ctx) {
		super(ctx);
	}
	
	public boolean exec(final Uri idUri) {
		final ContentResolver cr = getContentResolver();
		final ContentValues values = new ContentValues();
		
		final int deleted = cr.delete(idUri, null, null);	
		
		return (deleted > 0);
	}


}
