package com.revature.developercorner.service;

import com.revature.developercorner.data.BlogRepository;
import com.revature.developercorner.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// BlogService Class
// This class will handle the business logic for the Blog objects for the application.
@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository;

    // AddPost method
    // This method will call the BlogRepository to insert the supplied Blog object into the database:
    public Blog addPost(Blog blog){
        blogRepository.save(blog);
        return blog;
    }

    // GetPostById method
    // This method will retrieve a Blog object from the database by the specified id:
    public Blog getPostById(Long id) {
        return blogRepository.findById(id).get();
    }

    // GetAllPosts method
    // This method will retrieve a List of Blog objects from the database:
    public List<Blog> getAllPosts(){
        return blogRepository.findAll();
    }

    // GetAllPostsByUser method
    // This method will return a List of Blog objects for a specific user from the database with the
    //  supplied user id:
    public List<Blog> getAllPostsByUser(Long user_id) {
        return blogRepository.findByUserId(user_id);
    }

    // UpdatePost method
    // This method will update a Blog record in the database:
    public Blog updatePost(Blog blog, Long id) {
        // Get the database's Blog object from the database:
        Blog blogDB = blogRepository.findById(id).get();

        //Set the Blog's attributes to the supplied Blog object's attributes:
        blogDB.setTitle(blog.getTitle());
        blogDB.setContent(blog.getContent());
        blogDB.setUpVotes(blog.getUpVotes());
        blogDB.setDownVotes(blog.getDownVotes());
        blogDB.setUpdated_at(blog.getUpdated_at());
        blogDB.setUserId(blog.getUserId());

        // Call the BlogRepository to update the record in the database with the newly updated
        // database Blog object:
        blogRepository.save(blogDB);
        return blogDB;
    }

    // DeletePost method
    // This method will delete a record from the database that matches the specified id:
    public void deletePost(Long id){
        blogRepository.deleteById(id);
    }
}


