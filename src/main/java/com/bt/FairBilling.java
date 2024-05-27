package com.bt;

import com.bt.models.Session;
import com.bt.records.UserBilling;
import com.bt.utils.FairBillingUtils;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class reads a log file containing user sessions and calculates the total billable time for each user.
 * The log file should contain lines in the following format:
 * <p>
 * HH:mm:ss username Start
 * HH:mm:ss username End
 * <p>
 * where HH:mm:ss is the timestamp, username is the user's ID, and Start/End indicates the start or end of a session.
 * The program calculates the total billable time for each user by summing the durations of all their sessions.
 * The billable time for a session is the time between the Start and End events.
 * If a session does not have a corresponding Start or End event, the program uses the first and last session times as the start and end times.
 * The program prints the user ID, the number of sessions, and the total billable time in seconds for each user.
 * <p>
 * Example input:
 * 09:00:00 user1 Start
 * 09:05:00 user1 End
 * 09:10:00 user2 Start
 * 09:15:00 user2 End
 * <p>
 * Example output:
 * user1 1 300
 * user2 1 300
 * <p>
 */
public class FairBilling {
    private static final Logger LOGGER = LogManager.getLogger(FairBilling.class);
    public static void main(String[] args) {
        // This block of code checks if the user has provided exactly one argument when running the program.
        // The argument should be the path to the log file that the program will process.
        // If the user does not provide exactly one argument, the program will print an error message and terminate.
        if (args.length != 1) {
            LOGGER.info("Please provide the path to the log file as an argument.");
            return;
        }

        String logFilePath = args[0];
        List<Session> userSessions = FairBillingUtils.readLogFile(logFilePath);
        List<UserBilling> userBills = FairBillingUtils.generateUserBills(userSessions);
        FairBillingUtils.printBills(userBills);
    }
}