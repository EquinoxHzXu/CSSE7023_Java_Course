package festival;

import java.util.*;

/**
 * <p>
 * A mutable class representing the line-up of a festival.
 * </p>
 * 
 * <p>
 * The line-up of a festival keeps track of the events that are scheduled to
 * take place. Time during the festival is broken up into a number of
 * consecutive sessions, and each event is scheduled to take place at a given
 * venue in a particular session. (Sessions are denoted simply by positive
 * integers.)
 * 
 * The session times are the same for all venues, so that events taking place in
 * the same session at different venues, are actually taking place at the same
 * time.
 * </p>
 * 
 * <p>
 * At most one event can be scheduled for a venue in a particular session,
 * although there is no requirement that there is an event scheduled at a venue
 * for every session.
 * </p>
 */
public class LineUp implements Iterable<Event> {

	private List<Event> lineUp;

	/**
	 * Creates a new line-up with no events scheduled.
	 */
	public LineUp() {
		this.lineUp = new ArrayList<Event>();
	}

	/**
	 * Adds a new event to the line-up.
	 * 
	 * @param event
	 *            the event to be added to the line-up
	 * @throws NullPointerException
	 *             if event is null
	 * @throws InvalidLineUpException
	 *             if there is already an event scheduled for the same venue and
	 *             session as the given event
	 */
	public void addEvent(Event event) {
		if (event == null){
			throw new NullPointerException("Event cannot be null");
		}
		for (Event e: lineUp){
			if (e.getSession() == event.getSession() && e.getVenue() == event.getVenue()){
				throw new InvalidLineUpException("Events are in conflict");
			}				
		}
		lineUp.add(event);
	}

	/**
	 * If the line-up contains an event that is equivalent to this one, then it
	 * is removed from the line-up. If there is no equivalent event, then the
	 * line-up is unchanged by the operation.
	 *
	 * @param event
	 *            the event to be removed from the line-up.
	 */
	public void removeEvent(Event event) {
		if (lineUp.contains(event)){
			lineUp.remove(event);
		}
	}

	/**
	 * Returns a list of the events scheduled for the given venue. The list of
	 * events should be ordered by session number (in ascending order).
	 * 
	 * @param venue
	 *            the venue for which the events will be retrieved
	 * @return a list of the events scheduled for the given venue, ordered by
	 *         session number
	 * @throws NullPointerException
	 *             if the given venue is null
	 */
	public List<Event> getEvents(Venue venue) {
		if (venue == null){
			throw new NullPointerException("Venue cannot be null");
		}
		List<Event> eventList = new ArrayList<Event>();
		for (Event ev: lineUp){
			if (ev.getVenue().equals(venue)){
				eventList.add(ev);
			}
		}
		ComparatorEventSession comparator = new ComparatorEventSession();
		Collections.sort(eventList, comparator);
		return eventList;
	}
	
	/**
	 * 
	 * This private class is used to sort the list to make it in order to support
	 * the getEvents(Venue venue) method.
	 * 
	 * In getEvents(Venue venue), the list of the events must be ordered by
	 * session number. The list contains objects of events, and sessions are
	 * the properties of these events.
	 *
	 */

	private class ComparatorEventSession implements Comparator<Event>{
		
		/**
		 * 
		 * @param event1
		 * 
		 * @param event2
		 * 			two events to compare
		 * 
		 * @return a positive number if event1's session is greater than event2's session;
		 * 		   a negative number if event2's session is greater than event1's session;
		 * 		   zero if two events have the same session number. 
		 * 
		 */
		
		@Override
		public int compare(Event event1, Event event2) {
			Event e1 = (Event) event1;
			Event e2 = (Event) event2;
			if (e1.getSession() > e2.getSession()){
				return 1;
			}
			else if (e1.getSession() < e2.getSession()){
				return -1;
			}
			else{
				return 0;
			}
		}
	}
	
	
	/**
	 * Returns a list of the events scheduled for the given session time (across
	 * all venues). The list should be ordered by venue name (in ascending
	 * order).
	 * 
	 * @param session
	 *            the session to retrieve the events for
	 * @return A list of the events scheduled for the given session time.
	 * @throws InvalidSessionException
	 *             if session <= 0
	 */
	public List<Event> getEvents(int session) {
		if (session <= 0){
			throw new InvalidSessionException("Session must be positive");
		}
		List<Event> eventList = new ArrayList<Event>();
		for (Event ev: lineUp){
			if (ev.getSession() == session){
				eventList.add(ev);
			}
		}
		ComparatorVenueName comparator = new ComparatorVenueName();
		Collections.sort(eventList, comparator);
		return eventList;
	}

	/**
	 * 
	 * This private class is used to sort the list to make it in order to support
	 * the getEvents(int Session) method.
	 * 
	 * In getEvents(int Session), the list of the events must be ordered by
	 * Venue name(in ascending order). The list contains objects of events,
	 * and venues are the properties of these events.
	 *
	 */

	private class ComparatorVenueName implements Comparator<Event>{
		
		/**
		 * 
		 * @param event1
		 * 
		 * @param event2
		 * 			two events to compare
		 * 
		 * @return a positive number if event1's venue name is prior in alphabet than event2's venue name;
		 * 		   a negative number if event2's venue name is prior in alphabet than event1's venue name;
		 * 		   zero if two events have the same venue name. 
		 * 
		 */
		
		@Override
		public int compare(Event event1, Event event2) {
			Event e1 = (Event) event1;
			Event e2 = (Event) event2;
			int flag = e1.getVenue().getName().compareTo(e2.getVenue().getName());
			if (flag > 0) {
				return 1;
			}
			else if (flag < 0){
				return -1;
			}
			else{
				return 0;
			}

		}
	}
	
	/**
	 * Returns a set of all the venues where at least one event from the line-up
	 * takes place.
	 * 
	 * @return The venues where events from the line-up will take place.
	 */
	public Set<Venue> getVenues() {
		Set<Venue> venueSet = new HashSet<Venue>();
		for (Event ev: lineUp){
			if (!venueSet.contains(ev)){
				venueSet.add(ev.getVenue());
			}
		}
		return venueSet;
	}

	/**
	 * If there is at least one event scheduled, then this method returns the
	 * number of the first session where there is an event scheduled. Otherwise
	 * it returns 0.
	 * 
	 * @return If there is at least one event scheduled, then the first session
	 *         number that an event is scheduled for, and 0 otherwise.
	 */
	public int getFirstUsedSession() {

		if (!lineUp.isEmpty()) {
			ComparatorEventSession comparator = new ComparatorEventSession();
			Collections.sort(lineUp, comparator);
			return lineUp.get(0).getSession();
		}
		else{			
			return 0;
		}	
	}
		
	

	/**
	 * If there is at least one event scheduled, then this method returns the
	 * number of the last session where there is an event scheduled. Otherwise
	 * it returns 0.
	 * 
	 * @return If there is at least one event scheduled, then the last session
	 *         number that an event is scheduled for, and 0 otherwise.
	 */
	public int getLastUsedSession() {
		int se = 0;
		if (!lineUp.isEmpty()){
			for (Event ev: lineUp){
				if (ev.getSession() > se){
					se = ev.getSession();
				}
			}
			return se; 
		}else{
			return 0;
		}
		
	}

	/**
	 * Returns an iterator over the events in the line-up.
	 */
	@Override
	public Iterator<Event> iterator() {
		return lineUp.iterator();
	}

	/**
	 * The string representation of a line-up contains a line-separated
	 * concatenation of the string representations of the events in the line up.
	 * The events in the line-up should be ordered using their natural ordering
	 * (i.e. using the compareTo method defined in the Event class).
	 * 
	 * The line separator string used to separate the events should be retrieved
	 * in a machine-independent way by calling the function
	 * System.getProperty("line.separator").
	 */
	@Override
	public String toString() {
		String s = "";
		int i = 0;
		ComparatorNaturalOrdering comparator = new ComparatorNaturalOrdering();
		Collections.sort(lineUp, comparator);
		for (Event ev: lineUp){
			i += 1;
			if (i < lineUp.size()){
				s += (ev.getAct() + ": session " + ev.getSession() + " at " + ev.getVenue() + "\n");
			}
			else{
				s += (ev.getAct() + ": session " + ev.getSession() + " at " + ev.getVenue());
			}
			
		}
		return s;
	}

	/**
	 * 
	 * This private class is used to sort the list to make it in order to support
	 * the toString() method.
	 * 
	 */

	private class ComparatorNaturalOrdering implements Comparator<Event>{
		
		/**
		 * 
		 * Events are ordered primarily by the (lexicographical ordering of their)
		 * venue names, and then (for events at equal venues) by their session
		 * number (in ascending order), and then (for events at the same venue and
		 * session) by the (lexicographical ordering of their) act.
		 * 
		 * @return a positive number if event1 is prior to event2;
		 * 		   a negative number if event2 is prior to event1;
		 * 		   zero if two events are the same.
		 * 
		 */
		
		
		@Override
		public int compare(Event event1, Event event2) {
			Event e1 = (Event) event1;
			Event e2 = (Event) event2;
			int result = e1.getVenue().getName().compareTo(e2.getVenue().getName());
			if (result == 0){
				if (e1.getSession() > e2.getSession()){
					result = 1;
				}
				else if (e1.getSession() < e2.getSession()){
					result = -1;
				}
				else{
					result = e1.getAct().compareTo(e2.getAct());
				}
			}
			return result;
		}

	}
	
	/**
	 * Determines whether this LineUp is internally consistent (i.e. it
	 * satisfies its class invariant).
	 * 
	 * @return true if this LineUp is internally consistent, and false
	 *         otherwise.
	 */
	public boolean checkInvariant() {
		int i = 0;
		for (Event ev: lineUp){
			if (ev.getAct() != null && ev.getVenue() != null && ev.getSession() > 0){
				continue;
			}else{
				i = -1;
				break;
			}
		}
			
		if (i == -1){
			return false;
		}else{
			return true;
		}
	}

}

