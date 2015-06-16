package festival.test;

import org.junit.Assert;
import org.junit.Test;

import festival.*;

/**
 * Basic tests for the {@link Event} implementation class.
 * 
 * Write your own tests for the class here.
 */
public class EventTest {

	/** Test construction of a typical service. */
	@Test
	public void testTypicalEvent() {
		Venue venue = new Venue("v1");
		String act= "a1";
		int session = 2;

		Event event = new Event(venue, session, act);
		Assert.assertEquals(venue, event.getVenue());
		Assert.assertEquals(session, event.getSession());
		Assert.assertEquals(act, event.getAct());
		Assert.assertTrue("Invariant incorrect", event.checkInvariant());
		Assert.assertEquals("a1: session 2 at v1",
				event.toString());
	}
	
	/**
	 * Check that the appropriate exception is thrown if a service is created
	 * with an invalid session number.
	 */
	@Test(expected = InvalidSessionException.class)
	public void testInvalidSessionException(){
		Venue venue = new Venue("v1");
		String act = "a1";
		int session = -2;

		Event event = new Event(venue, session, act);
	}
	
	/**
	 * Check that the appropriate exception is thrown if a service is created
	 * with a null venue or a null act.
	 */
	@Test(expected = NullPointerException.class)
	public void testNullPointerException(){
		Venue venue1 = new Venue("v1");
		String act1 = null;
		int session1 = 2;
		
		Venue venue2 = new Venue(null);
		String act2 = "a2";
		int session2 = 4;
		
		Event e1 = new Event(venue1, session1, act1);
		Event e2 = new Event(venue2, session2, act2);
	}
	
	
}
