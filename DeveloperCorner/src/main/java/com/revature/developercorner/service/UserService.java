package com.revature.developercorner.service;

import com.revature.developercorner.data.UserRepository;
import com.revature.developercorner.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

// User Service Class
// This class will handle all the business logic for the User objects in the application.
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // AddUser method
    // User that is passed from the request will have their password encoded into BCrypt before being
    //  sent to the UserRepository object to save the User to the database:
    public User addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    // GetById method
    // Given the provided userId, this is passed into the UserRepository to query to find that user and
    //  return that user:
    public User getById(Long userId){
        return userRepository.findById(userId).get();
    }

    // GetAllUsers method
    // Calls the UserRepository to query for all Users in the database and return that List object:
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // UpdateUser method
    // This method will update the user in the database with the provided information:
    public void updateUser(Long userId, User user) {
        // Get User from the database:
        User userDB = userRepository.findById(userId).get();

        // Set the database User's attributes with information provided from the supplied User object:
        userDB.setUsername(user.getUsername());
        userDB.setPassword(passwordEncoder.encode(user.getPassword()));
        userDB.setEMail(user.getEMail());
        userDB.setRole(user.getRole());
        userDB.setTimeAvailable(user.getTimeAvailable());

        // Save the newly updated database User object:
        userRepository.save(userDB);
    }

    // DeleteUser method
    // This will pass the provided userId into the UserRepository to delete the specified user:
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    // LoadUserByUserName method
    // This is the overridden method from the UserDetailsService that is used to retrieve the
    //  User object from the database by querying with the supplied username:
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
