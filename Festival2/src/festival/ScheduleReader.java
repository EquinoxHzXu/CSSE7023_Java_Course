package festival;

import java.io.*;
import java.util.*;

//import java.util.regex.Pattern;

/**
 * Provides a method to read a shuttle timetable from a file.
 */
public class ScheduleReader {

	/**
	 * <p>
	 * Reads a text file called fileName that describes the shuttle services
	 * available for a festival, and returns the shuttle timetable containing
	 * each of the services in the file.
	 * </p>
	 * 
	 * <p>
	 * The first line of the file contains a single positive integer, denoting
	 * the number of sessions in the festival. The rest of the file contains
	 * zero or more descriptions of venues and their services for the available
	 * sessions.
	 * </p>
	 * 
	 * <p>
	 * There may be leading or trailing whitespace on the first line of the file
	 * that contains the single positive integer denoting the number of sessions
	 * in the festival.
	 * </p>
	 * 
	 * <p>
	 * A description of a venue and its services consists of (1) a venue name on
	 * a line of its own, followed by (2) one line for each session in the
	 * festival that describes the services that depart the venue at the end of
	 * that session, followed by (3) an empty line. <br>
	 * <br>
	 * (For the purpose of this method) a venue name is simply an unformatted
	 * non-empty string that doesn't contain any whitespace characters. There
	 * may be leading and trailing whitespace on the line containing the venue
	 * name but no other information. <br>
	 * <br>
	 * For (2) the lines for each session in the festival should be ordered by
	 * session number: starting at 1 and ending at the number of sessions in the
	 * festival. Each such line should consist of the session number, followed
	 * by zero or more venue names separated by white spaces. There may be
	 * leading or trailing whitespace on each such line.
	 * 
	 * A venue may not have a shuttle service to itself, and there can be no
	 * duplicate services.
	 * </p>
	 * 
	 * <p>
	 * A venue shouldn't have more than one description of itself and its
	 * services, but a venue doesn't have to have a description of itself and
	 * its services if it doesn't have any.
	 * </p>
	 * 
	 * @param fileName
	 *            the file to read from.
	 * @return the shuttle timetable that was read from the file.
	 * @throws IOException
	 *             if there is an error reading from the input file.
	 * @throws FormatException
	 *             if there is an error with the input format (e.g. a venue has
	 *             more than one description of itself and its services, or the
	 *             file format is not as specified above in any other way.) The
	 *             FormatExceptions thrown should have a meaningful message that
	 *             accurately describes the problem with the input file format.
	 */
	public static ShuttleTimetable read(String fileName) throws IOException,
			FormatException {

		FileReader fileReader = new FileReader(fileName);
		Scanner scanner = new Scanner(fileReader);
		// Create a new filereader and a new scanner to read the file.

		ShuttleTimetable timetable = new ShuttleTimetable();
		int session = 0;
		Venue source = null;
		Venue destination = null;
		List<String> lineArray = new ArrayList<String>();
		List<List<String>> fileArray = new ArrayList<List<String>>();
		Service service = null;
		// Initialize some parameters and objects.
		String numberOfSessions = scanner.nextLine();
		// Read the first line and it is the number of sessions.
		while (scanner.hasNextLine()) { // Scan every line
			String line = scanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			/*
			 * Create a new lineScanner to scan the elements in every single
			 * line.
			 */
			while (lineScanner.hasNext()) {
				if (lineScanner.hasNext()) {
					lineArray.add(lineScanner.next());
					// Add every element in a line to an ArrayList
				}

			}
			fileArray.add(lineArray);
			/*
			 * Add the ArrayList which represents a line to a new ArrayList. It
			 * will include some ArrayLists and every element represents a line.
			 */
			lineArray = new ArrayList<String>();
			// Reset the lineArray for next loop
			lineScanner.close();
		}
		scanner.close();

		if (Integer.parseInt(numberOfSessions) <= 0) {
			throw new FormatException(
					"Number of sessions must be greater than 0.");
			// To check if the number of sessions greater than 0.
		}

		for (int i = 0; i < fileArray.size(); i++) {

			if (fileArray.get(i).size() == 0) {
				if ((i + 1) % (Integer.parseInt(numberOfSessions) + 2) != 0) {
					throw new FormatException("Incorrect number of sessions.");
					// To check if the timetable match the number of sessions.
				}
			}
			if (fileArray.get(i).size() == 1
					&& !Character.isDigit(fileArray.get(i).get(0).charAt(0))) {
				source = new Venue(fileArray.get(i).get(0));
				/*
				 * If the line has only one word and the first character of a
				 * line is not a digit, then it will be the source venue of a
				 * service.
				 */
			}
			if (fileArray.get(i).size() == 1
					&& Character.isDigit(fileArray.get(i).get(0).charAt(0))) {
				continue;
				/*
				 * If the line has only one word and the first character of a
				 * line is a digit, then it will be a line with a session number
				 * and there are no services departs from the source venue in
				 * that session
				 */
			}
			if (fileArray.get(i).size() > 1) {
				session = Integer.parseInt(fileArray.get(i).get(0));
				/*
				 * If a line has many elements, then there are services. The
				 * first element is the session number.
				 */
				for (int j = 1; j < fileArray.get(i).size(); j++) {
					destination = new Venue(fileArray.get(i).get(j));
					// The other elements are destination venues.
					try {
						service = new Service(source, destination, session);
						// Create a new service.
						if (timetable.hasService(service)) {
							throw new FormatException("Duplicate service.");
							// To check if it is a duplicate service.
						} else {
							timetable.addService(service);
							// If it is not a duplicate service, add to the
							// timetable.
						}
					} catch (InvalidServiceException | InvalidSessionException invalid) {
						throw new FormatException("Invalid service.");
						/*
						 * If the format of service is not correct, throw a new
						 * FormatException.
						 */
					}
				}
			}

		}

		return timetable;
	}

}
