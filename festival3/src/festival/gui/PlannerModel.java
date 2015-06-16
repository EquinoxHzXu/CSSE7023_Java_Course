package festival.gui;

import java.io.IOException;
import java.util.*;

import festival.DayPlanner;
import festival.Event;
import festival.FormatException;
import festival.LineUp;
import festival.LineUpReader;
import festival.ScheduleReader;
import festival.ShuttleTimetable;

/**
 * The model for the Festival Planner.
 */
public class PlannerModel {

	// The LineUp to get the values from LineUpReader.read()
	private LineUp lineUp;
	// The timetable to get the values from ScheduleUpReader.read()
	private ShuttleTimetable timetable;
	// The DayPlanner to check compatibility
	private DayPlanner planner;

	// The list to save the events that has been added
	private List<Event> dayPlan;
	// The list to show the events loaded from the file
	private List<Event> allEvents;
	// The session numbers of the events that has been added
	private List<Integer> selectedSessions;

	/**
	 * Initialises the data model
	 */
	public PlannerModel() {
		lineUp = new LineUp();
		timetable = new ShuttleTimetable();
		dayPlan = new ArrayList<>();
		allEvents = new ArrayList<>();
		selectedSessions = new ArrayList<>();
	}

	/**
	 * Read the LineUp from the file
	 * 
	 * @param fileName
	 *            The filename of the ".txt" file
	 */
	public void loadEvents(String fileName) throws IOException, FormatException {
		lineUp = LineUpReader.read(fileName);
	}

	/**
	 * Read the timetable from the file
	 * 
	 * @param fileName
	 *            The filename of the ".txt" file
	 */
	public void loadServices(String fileName) throws IOException,
			FormatException {
		timetable = ScheduleReader.read(fileName);
		planner = new DayPlanner(timetable);
	}

	/**
	 * @return the DayPlanner to check compatibility
	 */
	public DayPlanner getPlanner() {
		return planner;
	}

	/**
	 * A helper class to make sure the events are ordered by session number
	 */
	private static class EventComparator implements Comparator<Event> {
		public int compare(Event e1, Event e2) {
			return e1.getSession() - e2.getSession();
		}
	};

	/**
	 * Add the event to the dayPlan list
	 * 
	 * @param event
	 *            The event to add
	 */
	public void addEvents(Event event) {
		dayPlan.add(event);
		EventComparator comparator = new EventComparator();
		Collections.sort(dayPlan, comparator);
	}

	/**
	 * Remove the event from the dayPlan list
	 * 
	 * @param event
	 *            The event to remove
	 */
	public void removeEvent(Event event) {
		dayPlan.remove(event);
	}

	/**
	 * @return the list of the day plan
	 */
	public List<Event> getDayPlan() {
		return dayPlan;
	}

	/**
	 * @return the list of all events loaded
	 */
	public List<Event> getEvents() {
		Iterator<Event> iterator = lineUp.iterator();
		while (iterator.hasNext()) {
			allEvents.add(iterator.next());
		}
		return allEvents;
	}

	/**
	 * @return the session numbers of added events
	 */
	public List<Integer> getSelectedSessions() {
		return selectedSessions;
	}

	/**
	 * Add the session of the event to the selectedSessions list
	 * 
	 * @param session
	 *            The session number to add
	 */
	public void addSelectedSession(int session) {
		selectedSessions.add(session);
	}

	/**
	 * Remove the session of the event from the selectedSessions list
	 * 
	 * @param session
	 *            The session number to remove
	 */
	public void removeSelectedSession(int session) {
		selectedSessions.remove(selectedSessions.indexOf(session));
	}
}
