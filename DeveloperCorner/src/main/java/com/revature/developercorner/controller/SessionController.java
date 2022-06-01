package com.revature.developercorner.controller;

import com.revature.developercorner.entity.User;
import com.revature.developercorner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    UserService userService;

    @GetMapping("/{username}")
    public User getSessionUser(@PathVariable("username") String username) {
        return userService.loadUserByUsername(username);
    }
}
