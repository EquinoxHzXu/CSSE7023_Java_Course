package festival;

import java.util.*;

/**
 * A class with functionality for helping a festival-goer to plan their day at a
 * festival.
 */
public class DayPlanner {

	// the timetable of the festival
	private ShuttleTimetable timetable;

	/**
	 * @require timetable!=null
	 * @ensure Creates a new day planner for a festival with a copy of the given
	 *         shuttle timetable (so that changes to the parameter timetable
	 *         from outside of this class won't affect the timetable of the
	 *         day-planner.)
	 */
	public DayPlanner(ShuttleTimetable timetable) {
		this.timetable = new ShuttleTimetable();
		for (Service service : timetable) {
			this.timetable.addService(service);
		}
	}

	/**
	 * @require plan!=null && !plan.contains(null) && the events in the plan are
	 *          ordered (smallest to largest) by session number.
	 * @ensure Returns true if and only if the events in the plan are compatible
	 *         (as per assignment hand-out). That is, (i) no event appears more
	 *         than once in the plan and no two different events in the plan are
	 *         scheduled for the same session, and (ii) for each event in the
	 *         plan, it is possible to go to that event and then (using the
	 *         available shuttle services in the day-planner's timetable if
	 *         necessary), get to the next event in the plan (on time), if there
	 *         is one.
	 * 
	 *         The timetable of the day-planner must not be modified in any way
	 *         by this method.
	 * 
	 *         See the assignment hand-out for details.
	 */
	public boolean compatible(List<Event> plan) {
		boolean isCompatible = true; // A variable to mark true of false, which
										// is used to return.

		// Set an original object (plan.get(i)),
		// then choose objects (plan.get(j)) in a reverse order to compare
		for (int i = 0; i < plan.size(); i++) {
			for (int j = plan.size() - 1; j > i; j--) {
				if (plan.get(j).equals(plan.get(i))) {
					isCompatible = false;
					break;
					// To check if there are duplicate events.
				}
				if (plan.get(i).getSession() == plan.get(j).getSession()) {
					isCompatible = false;
					break;
					// To check if there are different events in the plan
					// scheduled for the same session.
				}
			}
		}

		// Set an original object (plan.get(i)),
		// then choose objects (plan.get(j)) in a reverse order to compare
		if (isCompatible) {
			for (int i = 0; i < plan.size(); i++) {
				for (int j = plan.size() - 1; j > i; j--) {
					if (!canReach(plan.get(i).getVenue(), plan.get(i)
							.getSession(), plan.get(j).getVenue(), plan.get(j)
							.getSession())) {
						isCompatible = false; // If it is impossible to go to
												// event j from event i by
												// various services, the list of
												// event is false
					}
				}
			}
		}
		return isCompatible;
	}

	/**
	 * @param sourceVenue
	 *            the venue that current event is held
	 * @param sourceSession
	 *            the session when current event is held
	 * @param destinationVenue
	 *            the venue that the next event you want to go
	 * @param destinationSession
	 *            the session when the next event you want to go start
	 * @return a boolean value, which means that if you can arrive at the @destinationVenue
	 *         before the event there starts(@destinationSession) by services in
	 *         the timetable from @sourceVenue at @sourceSession
	 */
	private boolean canReach(Venue sourceVenue, int sourceSession,
			Venue destinationVenue, int destinationSession) {
		boolean can = false;
		// A variable to mark true of false, which is used to return.
		if (!sourceVenue.equals(destinationVenue)
				&& sourceSession < destinationSession) {
			for (Service service : timetable) {
				if (service.getSource().equals(sourceVenue)
						&& service.getSession() >= sourceSession
						&& service.getSession() < destinationSession) {

					sourceVenue = service.getDestination();
					sourceSession = service.getSession() + 1;
					can = canReach(sourceVenue, sourceSession,
							destinationVenue, destinationSession);
					// case 5
					// A recursion refers to the assignment hand-out

				}

			}

		}
		if (sourceVenue.equals(destinationVenue)
				&& sourceSession <= destinationSession) {
			can = true; // case 3 & 4
		}
		return can;
	}
}
