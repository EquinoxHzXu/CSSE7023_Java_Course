package festival.test;

import org.junit.*;
import festival.*;
import java.io.*;

/**
 * Basic tests for the {@link ScheduleReader} implementation class.
 * 
 * A much more extensive test suite will be performed for assessment of your
 * code, but this should get you started writing your own unit tests.
 */
public class ScheduleReaderTest {

	/**
	 * Test reading in a correctly formatted shuttle timetable with many
	 * descriptions of venues and their services.
	 */
	@Test
	public void testCorrectlyFormattedManyVenuesServices()
			throws FormatException, IOException {
		ShuttleTimetable expectedTimetable = new ShuttleTimetable();
		Service[] services =
				{ new Service(new Venue("v1"), new Venue("v2"), 1),
						new Service(new Venue("v1"), new Venue("v3"), 1),
						new Service(new Venue("v1"), new Venue("v4"), 2),
						new Service(new Venue("v2"), new Venue("v1"), 2),
						new Service(new Venue("v2"), new Venue("v4"), 2) };
		for (Service service : services) {
			expectedTimetable.addService(service);
		}

		ShuttleTimetable actualTimetable =
				ScheduleReader.read("read_01_correctlyFormatted.txt");
		checkTimetables(expectedTimetable, actualTimetable);
	}

	/**
	 * Test reading in a correctly formatted shuttle timetable with one
	 * description of a venue and its services. (All other venues presumedly
	 * don't have any services.)
	 */
	@Test
	public void testCorrectlyFormattedOneVenuesWithServices()
			throws FormatException, IOException {
		ShuttleTimetable expectedTimetable = new ShuttleTimetable();
		Service[] services =
				{ new Service(new Venue("v1"), new Venue("v2"), 1),
						new Service(new Venue("v1"), new Venue("v3"), 1),
						new Service(new Venue("v1"), new Venue("v4"), 2) };
		for (Service service : services) {
			expectedTimetable.addService(service);
		}

		ShuttleTimetable actualTimetable =
				ScheduleReader.read("read_02_correctlyFormatted.txt");
		checkTimetables(expectedTimetable, actualTimetable);
	}

	/**
	 * Test reading in a correctly formatted shuttle timetable with no
	 * descriptions of venues and services.
	 */
	@Test
	public void testCorrectlyFormattedNoVenueServices() throws FormatException,
			IOException {
		ShuttleTimetable expectedTimetable = new ShuttleTimetable();

		ShuttleTimetable actualTimetable =
				ScheduleReader.read("read_03_correctlyFormatted.txt");
		checkTimetables(expectedTimetable, actualTimetable);
	}

	/**
	 * Test that reading a timetable with a service from a venue to itself
	 * throws a FormatException
	 **/
	@Test(expected = FormatException.class)
	public void testIncorrectlyFormattedSelfService() throws FormatException,
			IOException {
		ScheduleReader.read("read_04_incorrectlyFormatted.txt");
	}

	/**
	 * Test that reading a timetable with a duplicate service throws a
	 * FormatException
	 **/
	@Test(expected = FormatException.class)
	public void testIncorrectlyFormattedDuplicateService()
			throws FormatException, IOException {
		ScheduleReader.read("read_05_incorrectlyFormatted.txt");
	}

	/**
	 * Test that reading a timetable with a missing session-number row throws a
	 * FormatException.
	 **/
	@Test(expected = FormatException.class)
	public void testIncorrectlyFormattedMissingSessionRow()
			throws FormatException, IOException {
		ScheduleReader.read("read_06_incorrectlyFormatted.txt");
	}

	/**
	 * Test that reading a timetable with a venue with more than one description
	 * of itself and its services throws a FormatException.
	 **/
	@Test(expected = FormatException.class)
	public void testIncorrectlyFormattedDuplicateVenueDescription()
			throws FormatException, IOException {
		ScheduleReader.read("read_07_incorrectlyFormatted.txt");
	}

	/**
	 * Test that reading a timetable with an invalid number of sessions throws a
	 * FormatException.
	 **/
	@Test(expected = FormatException.class)
	public void testIncorrectlyFormattedNumberOfSessions()
			throws FormatException, IOException {
		ScheduleReader.read("read_08_incorrectlyFormatted.txt");
	}

	// Helper methods

	/**
	 * Checks that expected and actual denote the same timetable.
	 */
	private void checkTimetables(ShuttleTimetable expected,
			ShuttleTimetable actual) {
		Assert.assertEquals(expected.size(), actual.size());
		for (Service expectedService : expected) {
			Assert.assertTrue(actual.hasService(expectedService));
		}
	}

}
