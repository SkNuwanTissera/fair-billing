package com.bt.utils;

import com.bt.exceptions.FairBillingException;
import com.bt.models.MappedSession;
import com.bt.models.Session;
import com.bt.records.UserBilling;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utility class for fair billing
 */
public class FairBillingUtils {

    private static final Logger logger = LogManager.getLogger(FairBillingUtils.class);
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9]+");



    /**
     * Reads a log file and returns a list of sessions.
     *
     * @param filePath The path to the log file.
     * @return A list of sessions.
     * @throws FairBillingException If an error occurs while reading the file.
     */
    public static List<Session> readLogFile(String filePath) {
        // Initialize a list to store the sessions.
        List<Session> sessions = new ArrayList<>();
        // Create a SimpleDateFormat object to parse the timestamp in the log file.
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        // Use a try-with-resources statement to ensure the BufferedReader is closed at the end.
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read the file line by line.
            while ((line = br.readLine()) != null) {
                // Split the line into parts.
                String[] parts = line.split(" ");

                // Check if the line has exactly three parts.
                if (parts.length == 3) {
                    try {
                        // Parse the timestamp.
                        Date timestamp = dateFormat.parse(parts[0]);

                        // Get the username and action from the line.
                        String username = parts[1];
                        String action = parts[2];

                        // Check if the action is a start.
                        boolean start = action.equals("Start");

                        // Assume the line is valid initially.
                        boolean valid = true;

                        // If the action is not a start and not an end, the line is invalid.
                        if (!start && !action.equals("End")) {
                            // Print a message and ignore the line.
                            logger.error("Ignoring line: " + line);
                            System.err.println("Invalid action: " + action);
                            valid = false;
                            continue; // Skip this entry
                        }

                        // Validate username
                        if (!isValidUsername(username)) {
                            logger.error("Ignoring line: " + line);
                            System.err.println("Invalid username: " + username);
                            valid = false;
                            continue; // Skip this entry
                        }

                        // Add the session to the list.
                        sessions.add(new Session(username, timestamp, start, valid));

                    } catch (ParseException e) {
                        // If the timestamp cannot be parsed, print a message and ignore the line.
                        logger.error("Ignoring line: " + line);
                        System.err.println("Invalid timestamp: " + parts[0]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new FairBillingException("File not found: " + filePath, e);
        } catch (IOException e) {
            throw new FairBillingException("File error " + filePath, e);
        }

        // Return the list of sessions.
        return sessions;
    }

    /**
     * Generates a list of UserBilling objects from a list of Session objects.
     * Each UserBilling object represents the total billable time and number of sessions for a user.
     *
     * @param sessions A list of Session objects.
     * @return A list of UserBilling objects.
     */
    public static List<UserBilling> generateUserBills(List<Session> sessions) {
        // If there are no sessions, return an empty list
        if (sessions == null || sessions.isEmpty()) {
            return Collections.emptyList();
        }

        // Get the first and last session times
        Date firstSessionTime = sessions.get(0).timestamp;
        Date lastSessionTime = sessions.get(sessions.size() - 1).timestamp;

        // Map the sessions by username
        Map<String, List<MappedSession>> map = mapSessions(sessions);
        List<UserBilling> results = new ArrayList<>();

        // Calculate the total billable time for each user
        for (String userid : map.keySet()) {
            int total = 0;
            int numberOfSessions = 0;
            for (MappedSession us : map.get(userid)) {
                numberOfSessions++;
                // If the session start time is null, set it to the first session time
                if (us.getStartTime() == null) {
                    us.setStartTime(firstSessionTime);
                }
                // If the session end time is null, set it to the last session time
                if (us.getEndTime() == null) {
                    us.setEndTime(lastSessionTime);
                }
                // Calculate the duration of the session in seconds
                total += (int) ((us.getEndTime().getTime() - us.getStartTime().getTime()) / 1000);
            }
            // Add a new UserBilling object to the results list
            results.add(new UserBilling(userid, numberOfSessions, total));
        }
        // Return the list of UserBilling objects
        return results;
    }

    /**
     * Maps sessions by username.
     *
     * This method takes a list of Session objects and maps them by username. For each session in the list,
     * it retrieves the list of MappedSession objects associated with the username in the session. If the list
     * does not exist, it is created. The session is then mapped to a MappedSession object using the mapSession method.
     * The resulting MappedSession object is added to the list associated with the username. The updated list is then
     * put back into the map. This process is repeated for each session in the list. The resulting map, which associates
     * each username with a list of their MappedSession objects, is returned.
     *
     * @param sessions A list of Session objects.
     * @return A map that associates each username with a list of their MappedSession objects.
     */
    private static Map<String, List<MappedSession>> mapSessions(List<Session> sessions) {
        // Initialize a map to store the sessions by username.
        Map<String, List<MappedSession>> userSessionMap = new LinkedHashMap<>();
        // Iterate over each session in the list.
        for (Session session : sessions) {
            // Get the list of MappedSession objects associated with the username in the session.
            List<MappedSession> mappedSessions = userSessionMap.get(session.username);
            // Map the session to a MappedSession object.
            mappedSessions = mapSession(mappedSessions, session);
            // Put the updated list of MappedSession objects back into the map.
            userSessionMap.put(session.username, mappedSessions);
        }
        // Return the map of sessions by username.
        return userSessionMap;
    }

    /**
     * Maps an individual session with start and end times.
     *
     * This method takes a list of MappedSession objects and a Session object. If the list of MappedSession objects is null,
     * a new list is created. If the session is a start session, a new MappedSession object is created with the username from
     * the session and the start time set to the timestamp from the session. The new MappedSession object is then added to the list.
     * If the session is an end session, the method iterates over the list of MappedSession objects to find a MappedSession object
     * with a null end time. If such a MappedSession object is found, its end time is set to the timestamp from the session.
     * If no such MappedSession object is found, a new MappedSession object is created with the username from the session and the
     * end time set to the timestamp from the session. The new MappedSession object is then added to the list. The updated list of
     * MappedSession objects is returned.
     *
     * @param userSessionList A list of MappedSession objects.
     * @param session A Session object.
     * @return The updated list of MappedSession objects.
     */
    private static List<MappedSession> mapSession(List<MappedSession> userSessionList, Session session) {
        if (userSessionList == null) {
            userSessionList = new ArrayList<>();
        }

        // If it is a start, add a new start record with null for end time
        if (session.start) {
            MappedSession mappedSession = new MappedSession(session.username);
            mappedSession.setStartTime(session.timestamp);
            userSessionList.add(mappedSession);
            return userSessionList;
        }

        // If it is an end, loop from the top to see if any starts to pair with
        for (MappedSession mappedSession : userSessionList) {
            if (mappedSession.getEndTime() == null) {
                mappedSession.setEndTime(session.timestamp);
                return userSessionList;
            }
        }

        // If no start is found, add a new end record with null for start time
        MappedSession mappedSession = new MappedSession(session.username);
        mappedSession.setEndTime(session.timestamp);
        userSessionList.add(mappedSession);
        return userSessionList;
    }

    /**
     * Prints the user bills.
     *
     * This method takes a list of UserBilling objects and prints each one to the console. For each UserBilling object in the list,
     * it prints the username, the total number of sessions, and the total billable seconds. The username, total number of sessions,
     * and total billable seconds are separated by a space.
     *
     * @param userBills A list of UserBilling objects.
     */
    public static void printBills(List<UserBilling> userBills) {
        for (UserBilling bill : userBills) {
            logger.info(bill.username() + " " + bill.totalSessions() + " " + bill.totalBillableSeconds());
        }
    }

    // Method to validate username
    private static boolean isValidUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }
}
