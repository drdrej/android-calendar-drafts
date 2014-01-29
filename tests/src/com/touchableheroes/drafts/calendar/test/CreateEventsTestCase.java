package com.touchableheroes.drafts.calendar.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.touchableheroes.drafts.calendar.Start;
import com.touchableheroes.drafts.calendar.dao.Event;
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
		
		final boolean notExistsAfterDelete = dao.exists(id);
		assertFalse("event not exists", notExistsAfterDelete);	
	}
	
	

	public void testModifyEventByHeader() throws Exception {
		// prepare some values :::
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
		final Date begin = sdf.parse( "2016-01-23 12:00" );
		final Date end = sdf.parse( "2016-01-24 10:00" );
		
		final Context ctx = getInstrumentation().getContext();
		final EventsDAO dao = new EventsDAO( getActivity() );
		
		final String title = "Test:Calendar-Drafts-Simple-Event";
		
		// step 1.:
		final EventId id = dao.createEvent(begin.getTime(), end.getTime(), title, "A simple event, created by calendar.drafts" );
		System.out.println("id === " + id.getId());
	
		String newTitle = "Test:Calendar-Drafts-Simple-Event:Changed";
		long newBegin = begin.getTime() + 1000;
		long newEnd = end.getTime() + 1000;
		
		String newDescription = "some new description";
		String newLocation = "";
		
		// step 2.:
		final boolean updated = dao.updateByHeader(title, begin.getTime(), end.getTime(), newTitle, newBegin, newEnd, newDescription, newLocation);
		assertTrue("update is successful", updated);
		
		// step 3: check existance
		final boolean exists = dao.exists(id);
		assertTrue("event created", exists);
		
		// step 4: check load by Id
		final Event loaded = dao.load(id);
		assertEquals( "loaded event has new title", newTitle, loaded.string( "title" ) );
		assertEquals( "loaded event has new description", newDescription, loaded.string( "description" ) );
		assertEquals( "loaded event has new start", newBegin, (long) loaded.longVal("start" ) );
		assertEquals( "loaded event has new end", newEnd,  (long) loaded.longVal( "end" ) );
		
		// cleanup db:
		boolean deleted = dao.delete(id);
		assertTrue("delete event after test", deleted);
	}
	

}
