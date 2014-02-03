package com.touchableheroes.drafts.calendar.dao;

import com.touchableheroes.drafts.calendar.cmd.DeleteEventByHeaderCmd;
import com.touchableheroes.drafts.calendar.cmd.DeleteEventCmd;
import com.touchableheroes.drafts.calendar.cmd.ExistsEventCmd;
import com.touchableheroes.drafts.calendar.cmd.InsertDaylyRepeatableEventCmd;
import com.touchableheroes.drafts.calendar.cmd.InsertEventCmd;
import com.touchableheroes.drafts.calendar.cmd.LoadEventByIdCmd;
import com.touchableheroes.drafts.calendar.cmd.UpdateEventByHeaderCmd;

import android.app.Activity;
import android.net.Uri;


/**
 * Simple dao to access calendar-events.
 * 
 * @author asiebert
 */
public class EventsDAO {

	private final Activity activity;
	
	private final InsertEventCmd insert;
	private final ExistsEventCmd exists;
	private final DeleteEventCmd delete;
	private final DeleteEventByHeaderCmd deleteByHeader;
	private final UpdateEventByHeaderCmd updateByHeader;
	private final LoadEventByIdCmd loadOneById;

	public EventsDAO(final Activity activity) {
		this.activity = activity;
		this.insert = new InsertEventCmd(activity);
		this.exists = new ExistsEventCmd(activity);
		this.delete = new DeleteEventCmd(activity);
		this.deleteByHeader = new DeleteEventByHeaderCmd(activity);
		this.updateByHeader = new UpdateEventByHeaderCmd(activity);
		this.loadOneById = new LoadEventByIdCmd(activity); 
	}

	/**
	 * method to create one simple event.
	 * 
	 * @param beginDate start-date
	 * @param endDate end-date
	 * @param title - title of the event
	 * @param description - description
	 * 
	 * @return generated EventId
	 */
	public EventId createEvent(final long beginDate, final long endDate,
			final String title, final String description) {
		final Uri result = insert.exec(1, beginDate, endDate, title, description);
		return new EventId(result);
	}
	
	/**
	 * method to create a repeated dayly event.
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param title
	 * @param description
	 * 
	 * @return
	 */
	public EventId createDaylyEvent(final long beginDate, final long endDate,
			final String title, final String description, final long until) {
		final InsertDaylyRepeatableEventCmd repeatable = new InsertDaylyRepeatableEventCmd( this.activity );
		
		// writes repeatable events until in a given cycle:
		final Uri idUri = repeatable.exec(1, beginDate, endDate, title, description, "");
		return new EventId(idUri);
	}

	public boolean createRepeatableEvent(final long beginDate,
			final String duration, final String title, final String description) {
		// todo: impl. for repeatable tasks.
		return false;
	}

	public boolean exists(final EventId id) {
		final Uri idUri = id.getUri();
		return exists.exec(idUri);
	}
	
	public Event load(final EventId id) {
		final Uri idUri = id.getUri();
		
		return loadOneById.exec(idUri);
	}

	public boolean delete(final EventId id) {
		return this.delete.exec(id.getUri());
	}
	
	/**
	 * identify by title, start-date & end-date.
	 */
	public boolean deleteByHeader(final String title, final long begin, final long end) {
		return deleteByHeader.exec(title, begin, end);
	}
	
	/**
	 * identify by title, start-date & end-date.
	 */
	public boolean updateByHeader(final String title, final long begin, final long end, final String newTitle, final long newBegin, final long newEnd,
			final String newDescription, final String newLocation) {
		return updateByHeader.exec(title, begin, end, newTitle, newBegin, newEnd,
				newDescription, newLocation);
	}
	
}