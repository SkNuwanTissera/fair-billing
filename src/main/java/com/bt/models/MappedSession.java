package com.bt.models;

import java.util.Date;

/**
 * The MappedSession class represents a session with a start and end time.
 * Each MappedSession object has a username, a start time, and an end time.
 * The username is a string that identifies the user.
 * The start time and end time are Date objects that represent the start and end of the session, respectively.
 */
public class MappedSession {
    private String username;
    private Date startTime;
    private Date endTime;

    /**
     * Constructs a MappedSession object with a specified username, start time, and end time.
     *
     * @param username  The username of the user.
     * @param startTime The start time of the session.
     * @param endTime   The end time of the session.
     */
    public MappedSession(String username, Date startTime, Date endTime) {
        this.username = username;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructs a MappedSession object with a specified username.
     * The start time and end time are not specified (i.e., they are null).
     *
     * @param username The username of the user.
     */
    public MappedSession(String username) {
        this.username = username;
    }

    /**
     * Returns the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the start time of the session.
     *
     * @return The start time of the session.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the session.
     *
     * @param startTime The start time of the session.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time of the session.
     *
     * @return The end time of the session.
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the session.
     *
     * @param endTime The end time of the session.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns a string representation of the MappedSession object.
     * The string representation includes the username, start time, and end time.
     *
     * @return A string representation of the MappedSession object.
     */
    @Override
    public String toString() {
        return "MappedSessions{" +
                "username='" + username + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}