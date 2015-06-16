package festival;

/**
 * <p>
 * An immutable class representing an event at a music festival.
 * </p>
 * 
 * <p>
 * An event takes place at a venue during one specified session. There is one
 * act associated with an event. (Sessions are denoted simply by positive
 * integers.)
 * </p>
 */
public class Event implements Comparable<Event> {
	
	private Venue venue;
	private int session;
	private String act;
	
	/**
	 * Creates a new event for the given venue, session and act.
	 * 
	 * @param venue
	 *            the venue of the event
	 * @param session
	 *            the session number of the event
	 * @param act
	 *            the act that will be on at this event
	 * @throws NullPointerException
	 *             if either parameter venue or act is null
	 * @throws InvalidSessionException
	 *             if session is not a positive integer
	 */
	public Event(Venue venue, int session, String act) {
		if (venue == null){
			throw new NullPointerException("Venue cannot be null");
		}
		if (act == null){
			throw new NullPointerException("Act cannot be null");
		}
		if (session <= 0){
			throw new InvalidSessionException("Session must be positive");
		}
		
		this.venue = venue;
		this.session = session;
		this.act = act;
	}

	/**
	 * Returns the venue of the event.
	 * 
	 * @return the venue of the event
	 */
	public Venue getVenue() {
		return venue; 
	}

	/**
	 * Returns the session when the event will take place.
	 * 
	 * @return the session number of the event
	 */
	public int getSession() {
		return session; 
	}

	/**
	 * Returns the act that will play at this event.
	 * 
	 * @return the act of the event.
	 */
	public String getAct() {
		return act;
	}

	/**
	 * Returns a string of the form:
	 * 
	 * "ACT: session SESSION at VENUE"
	 * 
	 * where ACT is the act of the event; SESSION is the session number of the
	 * event; and VENUE is the name of the event's venue.
	 */
	@Override
	public String toString() {
		return (act + ": session " + session + " at " + venue);
	}

	/**
	 * <p>
	 * Returns true if and only if the given object is an Event with the same
	 * act, venue and session number as this one.
	 * </p>
	 * 
	 * <p>
	 * (Any two venues are considered to be the same when they are equal
	 * according to their equals method. The same applies for acts: two acts are
	 * the same only if the objects associated with those acts are equal
	 * according to their equals methods.)
	 * </p>
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Event)) {
			return false;
		}
		Event event = (Event) object; // Event to compare.
		if (this.venue.equals(event.venue) && this.act.equals(event.act) && this.session == event.session){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns the hash code of this event; any reasonable implementation of the
	 * hashCode() function is acceptable.
	 * 
	 * @return the hash code of this event
	 */
	@Override
	public int hashCode() {
		int result = 17;
        result = 37 * result + venue.hashCode();
        result = 37 * result + act.hashCode();
        result = 37 * result + session;
        return result;
	}

	/**
	 * Events are ordered primarily by the (lexicographical ordering of their)
	 * venue names, and then (for events at equal venues) by their session
	 * number (in ascending order), and then (for events at the same venue and
	 * session) by the (lexicographical ordering of their) act.
	 */
	@Override
	public int compareTo(Event event) {
		int result = venue.getName().compareTo(event.venue.getName());
		if (result == 0){
			if (this.session > event.session){
				result = 1;
			}
			else if (this.session < event.session){
				result = -1;
			}
			else{
				result = act.compareTo(event.act);
			}
		}
		return result;
	}

	/**
	 * Determines whether this Event is internally consistent (i.e. it satisfies
	 * its class invariant).
	 * 
	 * @return true if this Event is internally consistent, and false otherwise.
	 */
	public boolean checkInvariant() {
		if (venue != null && act != null && session > 0){
			return true;
		}
		else{
			return false;
		}
	}
}
