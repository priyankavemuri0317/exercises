package com.revature.developercorner.session;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

// SessionRegistry class
// This class details the methods used to handle the Sessions in the application.
@Component
public class SessionRegistry {

    // Declare a new HashMap of SESSIONS:
    private static final HashMap<String, String> SESSIONS = new HashMap<>();

    // RegisterSession method.
    // This method will set the session by generating the session id and add it into memory:
    public String registerSession(final String username) {

        // Check if passed username is null.
        // If so, throw a new RunTime exception:
        if (username == null) {
            throw new RuntimeException("Username needs to be provided");
        }

        // Declare and set a session id variable by calling the 'generateSessionId' method:
        final String session_id = generateSessionId();

        // Set the session id with the username into memory:
        SESSIONS.putIfAbsent(session_id, username);

        // Return the session id created:
        return session_id;
    }

    // GetUsernameForSession Method.
    // This method will get the username assigned to the particular HashMap session id in memory:
    public String getUsernameForSession(final String session_id) {
        return SESSIONS.get(session_id);
    }

    // GenerateSessionId method.
    // This method will create and return a new String of a Base64 object.
    // Base64 (Radix64) is the term for a group of binary-to-text encoding schemes that represent binary data
    //  in an ASCII String format by translating it into a radix-64 representation. This will ensure that the
    //  data remains unchanged and intact when transmitted between devices/systems. The method will generate
    //  it from the UTF_8 Charset converted into bytes, as a String literal, and passed into the encoder of
    //  the Base64 object.
    public String generateSessionId() {
        return new String(
                Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8))
        );
    }

    // RemoveSession Method.
    // This method will take the sessionId that is supplied and remove it from memory:
    public void removeSession(String sessionId) {
        if(sessionId != null) {
            SESSIONS.remove(sessionId);
        }
    }
}
