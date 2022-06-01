package com.revature.developercorner.controller;

import com.revature.developercorner.entity.Message;
import com.revature.developercorner.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// MessageController Class
// This class will handle the HTTP Requests for the API/resource paths associated with the Message objects.
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/messages")
public class MessageController {
    @Autowired
    MessageService messageService;

    // PostMapping to add a Message to the database:
    @PostMapping("")
    public void addMessage(@RequestBody Message message) {
        messageService.addMessage(message);
    }

    // GetMapping to retrieve a specific Message object from the database:
    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable("id") Long id) {
        return messageService.getMessageById(id);
    }

    // GetMapping to retrieve Message objects for a specified User from the database:
    @GetMapping("/user/{userId}")
    public List<Message> getAllMessagesByUser(@PathVariable("userId") Long userId) {
        return messageService.getAllMessagesByUser(userId);
    }

    // GetMapping to retrieve Message objects from the database:
    @GetMapping("")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    // PutMapping to update a specified Message record with the supplied JSON Message object in the database:
    @PutMapping("/{id}")
    public void updateMessage(@PathVariable("id") Long id, @RequestBody Message message) {
        messageService.updateMessage(message, id);
    }

    // DeleteMapping to delete a specified Message record from the database:
    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable("id") Long id) {
        messageService.deleteMessage(id);
    }

}