android-calendar-drafts
=======================

A simple android calendar library, based on calendar extensions of android sdk.

		version: 0.2.0
		state: unstable
		license: Apache License
		copyright: 2013, Andreas Siebert, ask@touchableheroes.com
		
		
## Usage

1. Calendar-Drafts needs some read/write-permissions. So you need to extend your 
AndroidManifest.xml in this way:

    <uses-permission android:name="android.permission.READ_CALENDAR" />
    <uses-permission android:name="android.permission.WRITE_CALENDAR" />

I've used a DAO-pattern to hide android-specific-logic behind some kind of abstraction.
So to access events you need a dao-instance:
```java
final EventsDAO dao = new EventsDAO( activity );
```
We need some example-values to describe a use-case.

```java
final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

final Date begin = sdf.parse( "2015-01-23 12:00" );
final Date end = sdf.parse( "2015-01-24 10:00" );

final String title = "My Event";
final String description = "bla bla bla";
```
	
2. Put the jar into /lib directory and add this jar to the java-lib-path of your eclipse-project.
You will find the latest jar in the deploy/ folder inside this repo.

3. Add Event
```java
final EventId id = dao.createEvent(begin.getTime(), end.getTime(), title, "A simple event, created by calendar.drafts" );
System.out.println("id === " + id.getId() + " ::: " + id.getUri() + " ::: exists? " + dao.exists(id));
```

4. Delete Event
		
```java	
dao.deleteByHeader(title, begin.getTime(), end.getTime());	
System.out.println("id === " + id.getId() + " ::: " + id.getUri() + " ::: exists? " + dao.exists(id));
```


## License
	Apache License
	Version 2.0, January 2004
    http://www.apache.org/licenses/

	You will find terms and conditions for use, reproduction and distribution in LICENSE file in this repo.
   
... write code and have fun!
... ... Andreas Siebert, 
... ... ask@touchableheroes.com

