package com.bt.records;

/**
 * The UserBilling class represents a user's billing information with a username, the total number of sessions, and the total billable time in seconds.
 * Each UserBilling object has a username, the total number of sessions, and the total billable time in seconds.
 * @param username The username of the user.
 * @param totalSessions  The total number of sessions.
 * @param totalBillableSeconds The total billable time in seconds.
 */
public record UserBilling(String username, int totalSessions, int totalBillableSeconds) {}