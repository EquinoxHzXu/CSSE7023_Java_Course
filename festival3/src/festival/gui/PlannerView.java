package festival.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The view for the Festival Planner.
 */
@SuppressWarnings("serial")
public class PlannerView extends JFrame {

	// the model of the Festival Planner
	private PlannerModel model;

	// The button to open LineUp file
	private JButton loadEvents;
	// The button to open timetable file
	private JButton loadServices;
	// The button to add event
	private JButton addEvents;
	// The button to remove event the user have selected
	private JButton removeEvents;
	// The button to generate a day plan by the evetns the user selected
	private JButton generateDayPlan;

	// The JList to show all events from the file that has opened
	private JList<Event> allEventsJList;
	// The JList to show the events that has been selected
	private JList<Event> selectedEventsJList;
	// The JList to show the day plan
	private JList<Event> dayPlanJList;

	// The data of allEventsJList
	private DefaultListModel allEventsDefaultList;
	// The data of selectedEventsJList
	private DefaultListModel selectedEventsDefaultList;
	// The data of dayPlanJList
	private DefaultListModel dayPlanDefaultList;

	/**
	 * Creates a new Festival Planner window.
	 */
	public PlannerView(PlannerModel model) {
		this.model = model;
		setTitle("Festival Planner");
		setBounds(800, 400, 500, 650);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container container = getContentPane();
		setLayout(new FlowLayout());
		addLoadingButtons(container);
		addEventsLabel(container);
		addLists(container);
		addEventsModifiedButtons(container);
		addPlanLabel(container);
		addPlanDisplay(container);
	}

	/**
	 * Creates a set of buttons for loading files and adds to the Festival
	 * Planner window.
	 * 
	 * @param container
	 *            The container to put all elements.
	 */
	public void addLoadingButtons(Container container) {
		JPanel loadingButtonsPanel = new JPanel();
		loadEvents(loadingButtonsPanel);
		loadServices(loadingButtonsPanel);
		container.add(loadingButtonsPanel, "North");
	}

	/**
	 * Creates a button to load LineUp file.
	 * 
	 * @param panel
	 *            A panel to pack some elements together
	 */
	public void loadEvents(JPanel panel) {
		loadEvents = new JButton("Open LineUp File");
		panel.add(loadEvents);
	}

	/**
	 * Creates a button to load timetable file.
	 * 
	 * @param panel
	 *            A panel to pack some elements together
	 */
	public void loadServices(JPanel panel) {
		loadServices = new JButton("Open Timetable File");
		panel.add(loadServices);
	}

	/**
	 * Creates labels to mark JLists.
	 * 
	 * @param container
	 *            The container to put all elements.
	 */
	public void addEventsLabel(Container container) {
		JPanel panel = new JPanel();
		JLabel allEventsLabel = new JLabel("All Events:");
		allEventsLabel.setPreferredSize(new Dimension(120, 20));
		JLabel selectedEventsLabel = new JLabel("Selected Events:");
		selectedEventsLabel.setPreferredSize(new Dimension(120, 20));

		panel.add(allEventsLabel, "West");
		panel.add(selectedEventsLabel, "West");
		container.add(panel);
	}

	/**
	 * Packs the JLists of all events and selected events and adds to the
	 * Festival Planner window.
	 * 
	 * @param container
	 *            The container to put all elements.
	 */
	public void addLists(Container container) {
		JPanel eventPanel = new JPanel();
		addAllEventsList(eventPanel);
		addSelectedEventsList(eventPanel);
		container.add(eventPanel);
	}

	/**
	 * Creates a JList to show all events that has been loaded.
	 * 
	 * @param panel
	 *            A panel to pack some elements together
	 */
	public void addAllEventsList(JPanel panel) {
		allEventsDefaultList = new DefaultListModel<>();
		allEventsJList = new JList<>(allEventsDefaultList);
		// Set the data of allEventsJList
		allEventsJList
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		allEventsJList.setLayoutOrientation(JList.VERTICAL);
		allEventsJList.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(allEventsJList);
		listScroller.setAlignmentX(LEFT_ALIGNMENT);
		listScroller.setPreferredSize(new Dimension(200, 200));

		panel.add(listScroller);
	}

	/**
	 * Creates a JList to show the events that has been selected.
	 * 
	 * @param panel
	 *            A panel to pack some elements together
	 */
	public void addSelectedEventsList(JPanel panel) {
		selectedEventsDefaultList = new DefaultListModel<>();
		selectedEventsJList = new JList<>(selectedEventsDefaultList);
		// Set the data of selectedEventsJList
		selectedEventsJList
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		selectedEventsJList.setLayoutOrientation(JList.VERTICAL);
		selectedEventsJList.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(selectedEventsJList);
		listScroller.setPreferredSize(new Dimension(200, 200));

		panel.add(listScroller);
	}

	/**
	 * @return the data of the JList: allEventsJList.
	 */
	public DefaultListModel getAllEventsDefaultList() {
		return allEventsDefaultList;
	}

	/**
	 * @return the index of the JList: allEventsJList that is selected.
	 */
	public int getCurrentSelectedEventIndex() {
		return allEventsJList.getSelectedIndex();
	}

	/**
	 * @return the data of the JList: selectedEventsJList.
	 */
	public DefaultListModel getSelectedEventsDefaultList() {
		return selectedEventsDefaultList;
	}

	/**
	 * @return the index of the JList: selectedEventsJList that is selected.
	 */
	public int getIndexToDelete() {
		return selectedEventsJList.getSelectedIndex();
	}

	/**
	 * Creates a set of buttons to add and remove events, and generate the day
	 * plan that includes the events that has been selected.
	 * 
	 * @param container
	 *            The container to put all elements.
	 */
	public void addEventsModifiedButtons(Container container) {
		JPanel addButtonsPanel = new JPanel();
		addEvents(addButtonsPanel);
		removeEvents(addButtonsPanel);
		generateDayPlan(addButtonsPanel);
		container.add(addButtonsPanel);
	}

	/**
	 * Creates a button to add events.
	 * 
	 * @param panel
	 *            A panel to pack some elements together
	 */
	public void addEvents(JPanel panel) {
		addEvents = new JButton("Add");
		panel.add(addEvents);
	}

	/**
	 * Creates a button to remove the events that has been selected.
	 * 
	 * @param panel
	 *            A panel to pack some elements together
	 */
	public void removeEvents(JPanel panel) {
		removeEvents = new JButton("Remove");
		panel.add(removeEvents);
	}

	/**
	 * Creates a button to generate the day plan.
	 * 
	 * @param panel
	 *            A panel to pack some elements together
	 */
	public void generateDayPlan(JPanel panel) {
		generateDayPlan = new JButton("Generate Day Plan");
		panel.add(generateDayPlan);
	}

	/**
	 * Creates a label to mark the day plan JLists.
	 * 
	 * @param container
	 *            The container to put all elements.
	 */
	public void addPlanLabel(Container container) {
		JPanel panel = new JPanel();
		JLabel planLabel = new JLabel("Day Plan:");
		planLabel.setPreferredSize(new Dimension(400, 20));
		panel.add(planLabel, "West");
		container.add(panel);
	}

	/**
	 * Creates a JList to show the day plan.
	 * 
	 * @param container
	 *            The container to put all elements.
	 */
	public void addPlanDisplay(Container container) {
		JPanel planPanel = new JPanel();
		dayPlanDefaultList = new DefaultListModel<>();
		dayPlanJList = new JList(dayPlanDefaultList);
		// Set the data of dayPlanJList
		dayPlanJList
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		dayPlanJList.setLayoutOrientation(JList.VERTICAL);
		dayPlanJList.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(dayPlanJList);
		listScroller.setPreferredSize(new Dimension(410, 200));
		planPanel.add(listScroller);
		container.add(planPanel);
	}

	/**
	 * @return the data of the JList: dayPlanJList.
	 */
	public DefaultListModel getDayPlanDefaultList() {
		return dayPlanDefaultList;
	}

	/**
	 * Add ActionListeners.
	 */
	public void addLoadEventsListener(ActionListener al) {
		loadEvents.addActionListener(al);
	}

	public void addLoadServicesListener(ActionListener al) {
		loadServices.addActionListener(al);
	}

	public void addAddEventsListener(ActionListener al) {
		addEvents.addActionListener(al);
	}

	public void addRemoveEventsListener(ActionListener al) {
		removeEvents.addActionListener(al);
	}

	public void addGenerateDayPlanListener(ActionListener al) {
		generateDayPlan.addActionListener(al);
	}
}
