package com.bt.models;

import java.util.Date;

/**
 * The Session class represents a session with a username, timestamp, a boolean indicating if it's a start of a session, and a validity flag.
 * Each Session object has a username, a timestamp, a start flag, and a valid flag.
 * The username is a string that identifies the user.
 * The timestamp is a Date object that represents the time of the session.
 * The start flag is a boolean that indicates whether the session is a start session.
 * The valid flag is a boolean that indicates whether the session is valid.
 */
public class Session {
    public String username;
    public Date timestamp;
    public boolean start;

    /**
     * Constructs a Session object with a specified username, timestamp, start flag
     *
     * @param username  The username of the user.
     * @param timestamp The timestamp of the session.
     * @param start     The start flag of the session.
     */
    public Session(String username, Date timestamp, boolean start) {
        this.username = username;
        this.timestamp = timestamp;
        this.start = start;
    }
}