package com.revature.developercorner.data;

import com.revature.developercorner.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// BlogRepository Interface Class
// This interface class will handle the data logic of the Blog objects in the database for the application.
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    // FindByUserId method
    // This method will return a List of Blog objects from the database that match the user id provided:
    public List<Blog> findByUserId(Long user_id);
}