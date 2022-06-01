package com.revature.developercorner.data;

import com.revature.developercorner.entity.Blog;
import com.revature.developercorner.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// MessageRepository Interface Class
// This interface class will handle the data logic of the Message objects in the database for the application.
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    public List<Message> findBySender(Long user_id);
}
