package festival.test;

import org.junit.Assert;
import org.junit.Test;
import festival.*;

/**
 * Basic tests for the {@link Service} implementation class.
 * 
 * A more extensive test suite will be performed for assessment of your code,
 * but this should get you started writing your own unit tests.
 */
public class ServiceTest {

	/** Test construction of a typical service. */
	@Test
	public void testTypicalService() {
		Venue source = new Venue("v1");
		Venue destination = new Venue("v2");
		int session = 2;

		Service service = new Service(source, destination, session);
		Assert.assertEquals(source, service.getSource());
		Assert.assertEquals(destination, service.getDestination());
		Assert.assertEquals(session, service.getSession());
		Assert.assertTrue("Invariant incorrect", service.checkInvariant());
		Assert.assertEquals("Departs v1 after session 2 for v2",
				service.toString());
	}

	/**
	 * Check that the appropriate exception is thrown if a service is created
	 * with an invalid session number.
	 */
	@Test(expected = InvalidSessionException.class)
	public void testInvalidSessionException() {
		Venue source = new Venue("v1");
		Venue destination = new Venue("v2");
		int session = -2;

		Service service = new Service(source, destination, session);
	}

	/**
	 * Test that the appropriate exception is thrown when a service is created
	 * with equal source and destination stations.
	 */
	@Test(expected = InvalidServiceException.class)
	public void testSameSourceDestination() {
		Service s1 = new Service(new Venue("v1"), new Venue("v1"), 2);
	}

}
