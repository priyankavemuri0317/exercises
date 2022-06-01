package com.revature.developercorner.dto;

// ReponseDto Class
// This class will handle the Data Transfer between objects in the application.
// This class is used for getting and setting the session id for the HTTP Response for users that try to log
//  into the application through the front-end.
public class ResponseDto {

    // Declare String for session id:
    private String session_id;

    // GetSessionId method
    // This will return the session id:
    public String getSession_id() {
        return session_id;
    }

    // SetSessionId method
    // This will set the session id with the supplied session id:
    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}

