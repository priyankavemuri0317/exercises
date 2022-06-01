package com.revature.developercorner.controller;

import com.revature.developercorner.entity.User;
import com.revature.developercorner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// RegistrationController Class
// This class will handle the HTTP Requests for the API/resource paths associated with registering new User objects.
@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    UserService userService;

    // PostMapping to add a User to the database:
    @PostMapping("")
    public boolean add_user(@RequestBody User user) {
        userService.addUser(user);
        User userDB = userService.loadUserByUsername(user.getUsername());
        if (userDB.getUserId() != 0 || userDB.getUserId() != null) {
            return true;
        }else {
            return false;
        }
    }
}
