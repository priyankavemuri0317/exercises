package com.revature.developercorner.security;

import com.revature.developercorner.entity.User;
import com.revature.developercorner.service.UserService;
import com.revature.developercorner.session.SessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// SessionFilter class
// This class is used to filter HTTP requests to determine if the session for the user is valid and active.
// This class extends the OncePerRequestFilter, which means that the filter will only execute once for
//  each request made.
@Component
public class SessionFilter extends OncePerRequestFilter {

    // Declare the SessionRegistry and UserService data members:
    final private SessionRegistry sessionRegistry;
    final private UserService userService;

    // Constructor for SessionFilter class to instantiate the data members:
    @Autowired
    public SessionFilter(final SessionRegistry sessionRegistry, final UserService userService) {
        this.sessionRegistry = sessionRegistry;
        this.userService = userService;
    }

    // doFilterInternal method
    // This method will take the request to authenticate the current session, then pass the request on
    //  if it is authenticated:
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Get the session id by retrieving the AUTHORIZATION attribute from the request header:
        final String session_id = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check to see if the session id does not exist or if the length is 0.
        // If so, then return without authorizing the request:
        if(session_id == null || session_id.length() == 0) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get the username from the in-memory SessionRegistry object:
        final String username = sessionRegistry.getUsernameForSession(session_id);

        // Check if username is null.
        // If so, then return without authorizing the request:
        if(username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get the User object by calling the UserService object and calling the 'loadUserByUsername' method:
        final User user = userService.loadUserByUsername(username);

        // Set the authentication token by creating a new one and supplying the User object and the user's authorities:
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );

        // Set the WebAuthenticationDetails of the token by converting the HTTP request into a new instance of one:
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Set the token into the helper class SecurityContextHolder.
        // This will supply the particular security details for the application.
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Pass the request onto the next filter (or resource if no additional filters are implemented):
        filterChain.doFilter(request, response);
    }
}

