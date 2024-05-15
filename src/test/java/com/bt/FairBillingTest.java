package com.bt;

import com.bt.exceptions.FairBillingException;
import com.bt.models.Session;
import com.bt.records.UserBilling;
import com.bt.utils.FairBillingUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * This class contains test cases for the FairBilling application.
 * It tests the functionality of the FairBillingUtils class methods.
 */
class FairBillingTest {

    String filePath = "src/test/java/com/bt/sessions.log";

    /**
     * This method is executed before each test. It currently does not perform any setup operations.
     */
    @BeforeEach
    void setUp() {
    }

    /**
     * This method is executed after each test. It currently does not perform any teardown operations.
     */
    @AfterEach
    void tearDown() {
    }

    /**
     * This test case verifies that the readLogFile method of FairBillingUtils class correctly reads a log file and lists the sessions.
     * It asserts that the returned list of sessions is not null and its size is 11.
     */
    @Test
    void readLogFileAndListSessionsTest() {
        List<Session> sessions = FairBillingUtils.readLogFile(filePath);
        assertNotNull(sessions);
        assertEquals(11, sessions.size());
    }

    /**
     * This test case verifies that the readLogFile method of FairBillingUtils class correctly handles an empty log file.
     * It asserts that the returned list of sessions is not null and its size is 0.
     */
    @Test
    void readLogFileWithNoSessionsTest() {
        List<Session> sessions = FairBillingUtils.readLogFile("src/test/java/com/bt/emptySessions.log");
        assertNotNull(sessions);
        assertEquals(0, sessions.size());
    }

    /**
     * This test case verifies that the readLogFile method of FairBillingUtils class correctly handles a log file with invalid sessions.
     * It asserts that the returned list of sessions is not null and its size is 9, with 2 invalid sessions ignored.
     */
    @Test
    void readLogFileWithInvalidSessionsTest() {
        List<Session> sessions = FairBillingUtils.readLogFile("src/test/java/com/bt/invalidSessions.log");
        assertNotNull(sessions);
        assertEquals(4, sessions.size());
    }

    /**
     * This test case verifies that the generateUserBills method of FairBillingUtils class correctly generates user bills from a list of sessions.
     * It asserts that the returned list of user bills is not null and its size is 2.
     */
    @Test
    void generateUserBillsTest() {
        List<Session> sessions = FairBillingUtils.readLogFile(filePath);
        List<UserBilling> userBills = FairBillingUtils.generateUserBills(sessions);
        assertNotNull(userBills);
        assertEquals(2, userBills.size());
    }

    /**
     * This test case verifies that the main method of FairBilling class correctly handles an invalid log file path.
     * It asserts that an IOException is thrown.
     */
    @Test
    public void testFairBillingWithInvalidLogFilePath() {
        String invalidLogFilePath = "invalid/path/to/log/file.log";
        assertThrows(FairBillingException.class, () -> FairBilling.main(new String[]{invalidLogFilePath}));
    }

    /**
     * This test case verifies that the generateUserBills method of FairBillingUtils class correctly handles an empty session list.
     * It asserts that the returned list of user bills is empty.
     */
    @Test
    public void testFairBillingWithEmptySessionList() {
        List<Session> emptySessionList = new ArrayList<>();
        List<UserBilling> userBills = FairBillingUtils.generateUserBills(emptySessionList);
        assertTrue(userBills.isEmpty());
    }

    /**
     * This test case verifies that the generateUserBills method of FairBillingUtils class correctly generates a user bill from a single session.
     * It asserts that the returned list of user bills has a size of 1 and its first element matches the expected user bill.
     */
    @Test
    public void testFairBillingWithSingleSession() throws ParseException {
        Session session1 = new Session("user1", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2021-07-01T00:00:00"), true);
        Session session2 = new Session("user1", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2021-07-01T00:05:00"), false);
        List<Session> singleSessionList = new ArrayList<>(List.of(session1, session2));
        UserBilling expectedBill = new UserBilling("user1", 1, 300);
        List<UserBilling> userBills = FairBillingUtils.generateUserBills(singleSessionList);
        assertEquals(1, userBills.size());
        assertEquals(expectedBill, userBills.get(0));
    }
}