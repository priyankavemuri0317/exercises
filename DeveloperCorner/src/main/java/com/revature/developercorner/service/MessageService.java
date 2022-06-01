package com.revature.developercorner.service;

import com.revature.developercorner.data.MessageRepository;
import com.revature.developercorner.entity.Blog;
import com.revature.developercorner.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// MessageService Class
// This class will handle the business logic for the Message objects in the application.
@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    // AddMessage method
    // This method will insert a new Message object into the database as a record:
    public Message addMessage(Message message){
        messageRepository.save(message);
        return message;
    }

    // GetAllMessages method
    // This method will retrieve the List of Message objects from the database:
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    // GetMessageById method
    // This method will get a specific Message object from the database with the supplied id:
    public Message getMessageById(Long id) {
        return messageRepository.findById(id).get();
    }

    // GetAllMessagesByUser method
    // This method will retrieve List of Messages from the database by calling the MessageRepository and using the
    //  findBySender method which will supply the userId to the method:
    public List<Message> getAllMessagesByUser(Long userId) {
        return messageRepository.findBySender(userId);
    }

    // UpdateMessage method
    // This method will update a record in the database by the specified id:
    public Message updateMessage(Message message, Long id) {
        // Retrieve the database Message object from the database that matches the id:
        Message messageDB = messageRepository.findById(id).get();

        // Set the database Message's attributes to the supplied Message object's attributes:
        messageDB.setMessage(message.getMessage());
        messageDB.setSender(message.getSender());
        messageDB.setRecipient(message.getRecipient());

        // Update the record in the database through the MessageRepository's save method:
        messageRepository.save(messageDB);
        return messageDB;
    }

    // DeleteMessage method
    // This method will delete a record from the database by the specified id:
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
