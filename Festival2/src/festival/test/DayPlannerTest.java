package festival.test;

import org.junit.*;
import java.util.*;
import festival.*;
import java.io.*;

/**
 * Basic tests for the {@link DayPlanner} implementation class.
 * 
 * CSSE7023: Write your own tests for the class here: I have only added two
 * basic ones to get you started. You don't have to use these tests in your own
 * test suite.
 * 
 * A much more extensive test suite will be performed for assessment of your
 * code, but this should get you started writing your own unit tests.
 */
public class DayPlannerTest {

	// Events for use in testing
	private Event[] events = { new Event(new Venue("v1"), 2, "act_a"),
			new Event(new Venue("v2"), 3, "act_d"),
			new Event(new Venue("v3"), 3, "act_e"),
			new Event(new Venue("v3"), 4, "act_i"),
			new Event(new Venue("v4"), 5, "act_f"), };

	/**
	 * Test that the method works correctly for a compatible day plan with three
	 * events.
	 */
	@Test
	public void testCompatiblePlan() throws IOException, FormatException {
		// the planner to test with
		ShuttleTimetable timetable = ScheduleReader.read("timetable_01.txt");
		DayPlanner planner = new DayPlanner(timetable);
		// the day plan to test
		List<Event> plan = new ArrayList<>();
		plan.add(events[0]);
		plan.add(events[1]);
		plan.add(events[4]);

		Assert.assertTrue(planner.compatible(plan));

	}

	/**
	 * Test that the method work correctly for a incompatible plan that contains
	 * two events at the same session.
	 */
	@Test
	public void testIncompatiblePlan() throws IOException, FormatException {
		// the planner to test with
		ShuttleTimetable timetable = ScheduleReader.read("timetable_01.txt");
		DayPlanner planner = new DayPlanner(timetable);
		// the day plan to test
		List<Event> plan = new ArrayList<>();
		plan.clear();
		plan.add(events[0]);
		plan.add(events[1]);
		plan.add(events[2]);
		plan.add(events[3]);

		Assert.assertFalse(planner.compatible(plan));
	}

	/**
	 * Test that the method work correctly if there are duplicate events.
	 */
	@Test
	public void testDuplicateEvents() throws IOException, FormatException {
		// the planner to test with
		ShuttleTimetable timetable = ScheduleReader.read("timetable_01.txt");
		DayPlanner planner = new DayPlanner(timetable);
		// the day plan to test
		List<Event> plan = new ArrayList<>();
		plan.clear();
		plan.add(events[0]);
		plan.add(events[1]);
		plan.add(events[3]);
		plan.add(events[0]); // Duplicate event

		Assert.assertFalse(planner.compatible(plan));
	}

	/**
	 * Test that the method work correctly if there are no services between some
	 * two events.
	 */
	@Test
	public void testNoServicesAvailableForTwoEvents() throws IOException,
			FormatException {
		// the planner to test with
		ShuttleTimetable timetable = ScheduleReader.read("timetable_01.txt");
		DayPlanner planner = new DayPlanner(timetable);
		// the day plan to test
		List<Event> plan = new ArrayList<>();
		plan.clear();
		plan.add(new Event(new Venue("v4"), 1, "act_g"));
		// an event that cannot reach
		plan.add(events[3]);

		Assert.assertFalse(planner.compatible(plan));
	}

	/**
	 * Test that the method work correctly if there all events are held at the
	 * same venue.
	 */
	@Test
	public void testEventsAtTheSameVenue() throws IOException, FormatException {
		// the planner to test with
		ShuttleTimetable timetable = ScheduleReader.read("timetable_01.txt");
		DayPlanner planner = new DayPlanner(timetable);
		// the day plan to test
		List<Event> plan = new ArrayList<>();
		plan.clear();
		plan.add(events[0]);
		plan.add(new Event(new Venue("v1"), 3, "act_h"));
		plan.add(new Event(new Venue("v1"), 5, "act_i"));
		// events that at the same venue but not at the same session

		Assert.assertTrue(planner.compatible(plan));
	}

	/**
	 * Test that the method works correctly if there are no services to "v3" but
	 * there is an event at "v3".
	 */
	@Test
	public void testNoServiceTov3() throws IOException, FormatException {
		// the planner to test with
		ShuttleTimetable timetable = ScheduleReader.read("timetable_02.txt");
		DayPlanner planner = new DayPlanner(timetable);
		// the day plan to test
		List<Event> plan = new ArrayList<>();
		plan.add(events[0]);
		plan.add(events[1]);
		plan.add(events[3]);

		Assert.assertFalse(planner.compatible(plan));

	}

	/**
	 * Test that the method works correctly if there are no services or events
	 * related to "v3'.
	 */
	@Test
	public void testSkipv3() throws IOException, FormatException {
		// the planner to test with
		ShuttleTimetable timetable = ScheduleReader.read("timetable_02.txt");
		DayPlanner planner = new DayPlanner(timetable);
		// the day plan to test
		List<Event> plan = new ArrayList<>();
		plan.add(events[0]);
		plan.add(events[1]);
		plan.add(events[4]);

		Assert.assertTrue(planner.compatible(plan));

	}
}
