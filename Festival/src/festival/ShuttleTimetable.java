package festival;

import java.util.*;

/**
 * <p>
 * A mutable representation of the shuttle services between venues at a
 * festival.
 * </p>
 * 
 * <p>
 * A shuttle timetable does not contain duplicate services (no two services run
 * from a source venue to a destination venue at the same time).
 * </p>
 */
public class ShuttleTimetable implements Iterable<Service> {

	private List<Service> timetable;
	
	
	/**
	 * Constructs a new shuttle timetable without any services.
	 **/
	public ShuttleTimetable() {
		this.timetable = new ArrayList<Service>();
	}

	/**
	 * Unless the shuttle timetable already contains an equivalent service, this
	 * method adds the given service to the shuttle timetable. If the timetable
	 * already contains an equivalent service, then this method should not
	 * change the shuttle timetable.
	 * 
	 * (Equivalence of services is judged using the equals method in the Service
	 * class.)
	 * 
	 * @param service
	 *            the service to be added to the shuttle timetable.
	 * @throws NullPointerException
	 *             if service is null
	 */
	public void addService(Service service) {
		if (service == null){
			throw new NullPointerException("Service cannot be null");
		}
		if (!timetable.contains(service)) 
			timetable.add(service);
	}

	/**
	 * If the shuttle timetable contains a service that is equivalent to this
	 * one, then it is removed from the timetable. If there is no equivalent
	 * service, then the timetable is unchanged by the operation.
	 * 
	 * @param service
	 *            the service to be removed from the timetable.
	 */
	public void removeService(Service service) {
		if (hasService(service)){
			timetable.remove(service);
		}
		
	}

	/**
	 * Returns true if the timetable contains a shuttle service equivalent to
	 * the parameter service, and false otherwise.
	 * 
	 * @param service
	 *            the service to be searched for
	 * @return true iff the timetable contains a shuttle service equivalent to
	 *         the given parameter.
	 */
	public boolean hasService(Service service) {
		return timetable.contains(service); 
	}

	/**
	 * Returns the set of venues that you can get to by catching an available
	 * shuttle service from the source venue at the end of the given session.
	 * 
	 * @param source
	 *            the source venue
	 * @param session
	 *            the session number
	 * @return A set of venues that can be reached by catching a single shuttle
	 *         service from the source venue at the end of the given session.
	 * 
	 * @throws NullPointerException
	 *             if source is null
	 * @throws InvalidSessionException
	 *             if the session number is not positive
	 */
	public Set<Venue> getDestinations(Venue source, int session) {
		
		if (source == null){
			throw new NullPointerException("Service cannot be null");
		}
		if (session <= 0){
			throw new InvalidSessionException("Session must be positive");
		}
		Set<Venue> set = new HashSet<Venue>();
		for (Service tt: timetable){
			if (tt.getSource().equals(source) && tt.getSession() == session){
				set.add(tt.getDestination());
			}
		}
		return set;
	}

	/**
	 * Returns an iterator over the services in the shuttle timetable.
	 */
	@Override
	public Iterator<Service> iterator() {
		return timetable.iterator();
	}

	/**
	 * Returns any meaningful implementation of the toString method for this
	 * class.
	 */
	@Override
	public String toString() {
		String s = "";
		for (Service tt: timetable){
			s += ("Departs " + tt.getSource().getName() + " after session " + tt.getSession() + " for " + tt.getDestination().getName() + "\n");
		}
		return s;
	}
	

	/**
	 * Determines whether this ShuttleTimetable is internally consistent (i.e.
	 * it satisfies its class invariant).
	 * 
	 * @return true if this ShuttleTimetable is internally consistent, and false
	 *         otherwise.
	 */
	public boolean checkInvariant() {
		int i = 0;
		for (Service tt: timetable){
			if (tt.getSource() != null && tt.getDestination() != null && tt.getSession() > 0){
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
