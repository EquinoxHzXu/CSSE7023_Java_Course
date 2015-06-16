package festival.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import festival.Event;
import festival.FormatException;
import festival.Venue;

/**
 * The controller for the Festival Planner.
 */
public class PlannerController {

	// the model that is being controlled
	private PlannerModel model;
	// the view that is being controlled
	private PlannerView view;

	/**
	 * Initialises the Controller for the Festival Planner.
	 */
	public PlannerController(PlannerModel model, PlannerView view) {
		this.model = model;
		this.view = view;
		// Initialises ActionListeners.
		view.addLoadEventsListener(new loadEventsListener());
		view.addLoadServicesListener(new loadServicesListener());
		view.addAddEventsListener(new addEventsListener());
		view.addRemoveEventsListener(new removeEventsListener());
		view.addGenerateDayPlanListener(new generateDayPlanListener());
	}

	/**
	 * The ActionListener to monitor the open LineUp file button pressed.
	 */
	private class loadEventsListener implements ActionListener {
		/**
		 * To describe the action
		 * 
		 * @param e
		 *            The action
		 */
		public void actionPerformed(ActionEvent e) {
			// Allow to choose the LineUp file from the file system
			JFileChooser eventChooser = new JFileChooser();
			eventChooser.setDialogTitle("Open LineUp File");
			if (eventChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
				String filename = eventChooser.getSelectedFile().getName();
				try {
					model.loadEvents(filename);
				} catch (IOException e1) { // File not found
					// Show the Messagebox
					JOptionPane
							.showMessageDialog(
									null,
									"Your file must be in the same folder as the program.",
									"File not found", JOptionPane.ERROR_MESSAGE);
				} catch (FormatException e1) { // Format error
					// Show the Messagebox
					JOptionPane.showMessageDialog(null,
							"Event incorrectly formatted.", "Format Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			for (Event event : model.getEvents()) {
				// Add elements from the file to the DefaultListModel so they
				// are available to show in the JList
				view.getAllEventsDefaultList().addElement(event);
			}

		}
	}

	/**
	 * The ActionListener to monitor the open timetable file button pressed.
	 */
	private class loadServicesListener implements ActionListener {
		/**
		 * To describe the action
		 * 
		 * @param e
		 *            The action
		 */
		public void actionPerformed(ActionEvent e) {
			// Allow to choose the timetable file from the file system
			JFileChooser service = new JFileChooser();
			service.setDialogTitle("Open LineUp File");
			if (service.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
				String filename = service.getSelectedFile().getName();
				try {
					model.loadServices(filename);
				} catch (IOException e1) { // File not found
					// Show the Messagebox
					JOptionPane
							.showMessageDialog(
									null,
									"Your file must be in the same folder as the program.",
									"File not found", JOptionPane.ERROR_MESSAGE);
				} catch (FormatException e1) { // Format error
					// Show the Messagebox
					JOptionPane.showMessageDialog(null,
							"Event incorrectly formatted.", "Format Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * The ActionListener to monitor the add event button pressed.
	 */
	private class addEventsListener implements ActionListener {
		/**
		 * To describe the action
		 * 
		 * @param e
		 *            The action
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				// Get the event that is selected in the JList
				Event currentSelectedEvent = model.getEvents().get(
						view.getCurrentSelectedEventIndex());
				// To judge if there are more than one event held in the same
				// session
				if (model.getSelectedSessions().contains(
						currentSelectedEvent.getSession())) {
					// To judge if there are duplicates events
					if (view.getSelectedEventsDefaultList().contains(
							currentSelectedEvent)) {
						// Show the Messagebox
						JOptionPane.showMessageDialog(null, "Duplicate event!",
								"Duplicate Event", JOptionPane.ERROR_MESSAGE);
					} else {
						// Show the Messagebox
						JOptionPane
								.showMessageDialog(
										null,
										"There is already an event held in this session!",
										"Duplicate Session",
										JOptionPane.ERROR_MESSAGE);
					}
				} else {
					model.addEvents(currentSelectedEvent);
					// Check if events are all compatible
					if (!model.getPlanner().compatible(model.getDayPlan())) {
						// A helper method to show the Messageboxes
						showCannotReachAlert(currentSelectedEvent);
						model.removeEvent(currentSelectedEvent);
					} else {
						// Add the event to the DefaultListModel to show in
						// the JList
						view.getSelectedEventsDefaultList().addElement(
								currentSelectedEvent);
						// Add the session number to a list so it is available
						// to compare to later added events.
						model.addSelectedSession(currentSelectedEvent
								.getSession());
						Collections.sort(model.getSelectedSessions());
					}
				}
			} catch (ArrayIndexOutOfBoundsException e1) { // The error if there
															// is no LineUp file
															// being loaded
				JOptionPane.showMessageDialog(null,
						"You must open a LineUp file first!", "No Data",
						JOptionPane.ERROR_MESSAGE);
			} catch (java.lang.NullPointerException e1) { // The error if there
															// is no timetable
															// file being loaded
				JOptionPane.showMessageDialog(null,
						"You must open a timetable file first!",
						"Null Timetable", JOptionPane.ERROR_MESSAGE);
			}
		}

		/**
		 * A helper class to show the messageboxes for why the event cannot be
		 * reached
		 */
		private void showCannotReachAlert(Event event) {
			for (Event e : model.getDayPlan()) {
				if (e.getSession() < event.getSession()) {
					if (!model.getPlanner().canReach(e, event)) {
						JOptionPane
								.showMessageDialog(
										null,
										"it is not possible to reach the new event "
												+ "from the 'previous event' in the plan.",
										"This Event Cannot Be Reached",
										JOptionPane.ERROR_MESSAGE);
					}
				}
				if (e.getSession() > event.getSession()) {
					if (!model.getPlanner().canReach(event, e)) {
						JOptionPane.showMessageDialog(null,
								"it is not possible to reach the new event "
										+ "to the 'next event' in the plan.",
								"This Event Cannot Be Reached",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	/**
	 * The ActionListener to monitor the remove event button pressed.
	 */
	private class removeEventsListener implements ActionListener {
		/**
		 * To describe the action
		 * 
		 * @param e
		 *            The action
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				// Get the event that is selected in the JList
				Event eventToRemove = (Event) view
						.getSelectedEventsDefaultList().get(
								view.getIndexToDelete());
				// Remove the event from the model
				model.removeEvent(eventToRemove);
				// Remove the session of the event to remove
				model.removeSelectedSession(eventToRemove.getSession());
				// Remove the event from the JList so it cannot be seen
				view.getSelectedEventsDefaultList().remove(
						view.getIndexToDelete());
			} catch (ArrayIndexOutOfBoundsException e1) {// The error if the
															// list of event to
															// remove is null
				JOptionPane.showMessageDialog(null,
						"You must add an event first!", "No Event",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	/**
	 * The ActionListener to monitor the remove event button pressed.
	 */
	private class generateDayPlanListener implements ActionListener {
		/**
		 * To describe the action
		 * 
		 * @param e
		 *            The action
		 */
		public void actionPerformed(ActionEvent e) {
			// To judge if no events have been selected
			if (model.getDayPlan().size() == 0) {
				JOptionPane.showMessageDialog(null,
						"You must add an event first!", "No Event",
						JOptionPane.ERROR_MESSAGE);
			} else {
				// Remove the day plan if there has been a day plan
				view.getDayPlanDefaultList().removeAllElements();
				// Add new day plan to the DefaultModelList to show in the JList
				for (Event event : model.getDayPlan()) {
					view.getDayPlanDefaultList().addElement(event);
				}
			}
		}
	}
}
