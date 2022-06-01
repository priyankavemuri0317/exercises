package com.revature.developercorner.data;

import com.revature.developercorner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// UserRepository Interface Class
// This interface class will handle the data logic of the User objects in the database for the application.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // FindByUsername method
    // This method will return a User object from the database:
    public User findByUsername(String username);
}
