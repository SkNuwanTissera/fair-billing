package com.bt.records;

/**
 * The UserBilling class represents a user's billing information.
 * Each UserBilling object has a username, total number of sessions, and total billable seconds.
 * The username is a string that identifies the user.
 * The total number of sessions is an integer that represents the total number of sessions the user has had.
 * The total billable seconds is an integer that represents the total number of seconds the user has been billed for.
 */
public record UserBilling(String username, int totalSessions, int totalBillableSeconds) {}