package com.touchableheroes.drafts.calendar.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.touchableheroes.drafts.calendar.Start;
import com.touchableheroes.drafts.calendar.dao.EventsDAO;
import com.touchableheroes.drafts.calendar.dao.EventId;

import android.content.Context;
import android.content.Intent;

import android.test.ActivityUnitTestCase;

/**
 * Test-case to test creation of calendar-events
 * 
 * @author asiebert
 */
public class CreateEventsTestCase extends ActivityUnitTestCase<Start>{

	public CreateEventsTestCase() {
		super(Start.class);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
        final Intent startActivity = new Intent(getInstrumentation().getTargetContext(), Start.class);
		startActivity(startActivity, null, null);
	}
	
	/**
	 * creates a simple event with start and end-date.
	 * date is in millis.
	 * 
	 * @throws Exception
	 */
	public void testSimpleEvent() throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
		final Date begin = sdf.parse( "2015-01-20 12:00" );
		final Date end = sdf.parse( "2015-01-22 10:00" );
		
		final Context ctx = getInstrumentation().getContext();
		final EventsDAO dao = new EventsDAO( getActivity() );
		
		final EventId id = dao.createEvent(begin.getTime(), end.getTime(), "Calendar-Drafts-Simple-Event", "A simple event, created by calendar.drafts" );
		
		System.out.println("id === " + id.getId());
		
		final boolean exists = dao.exists(id);
		assertTrue("event created", exists);
		
		final boolean deleted = dao.delete(id);
		assertTrue( "event deleted", deleted );
		
		assertFalse("event not exists", dao.exists(id));	
	}
	
	
	/**
	 * creates a simple event with start and end-date.
	 * date is in millis.
	 * 
	 * @throws Exception
	 */
	public void testDeleteEventByHeader() throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
		final Date begin = sdf.parse( "2015-01-23 12:00" );
		final Date end = sdf.parse( "2015-01-24 10:00" );
		
		final Context ctx = getInstrumentation().getContext();
		final EventsDAO dao = new EventsDAO( getActivity() );
		
		final String title = "Test:Calendar-Drafts-Simple-Event";
		final EventId id = dao.createEvent(begin.getTime(), end.getTime(), title, "A simple event, created by calendar.drafts" );
		System.out.println("id === " + id.getId());
	
		final boolean exists = dao.exists(id);
		assertTrue("event created", exists);
		

		dao.deleteByHeader(title, begin.getTime(), end.getTime());

//		final boolean deleted = dao.delete(id);
//		assertTrue( "event deleted", deleted );
		final boolean notExistsAfterDelete = dao.exists(id);
		
		assertFalse("event not exists", notExistsAfterDelete);	
	}
	

}
