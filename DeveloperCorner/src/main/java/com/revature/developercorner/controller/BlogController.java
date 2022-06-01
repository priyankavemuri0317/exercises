package com.revature.developercorner.controller;

import com.revature.developercorner.entity.Blog;
import com.revature.developercorner.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// BlogController Class
// This class will handle the HTTP Requests for the API/resource paths associated with the User objects.
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/blogs")
public class BlogController {

    @Autowired
    BlogService blogService;

    // PostMapping to add a Blog to the database:
    @PostMapping("")
    public void addPost(@RequestBody Blog blog) {
        blogService.addPost(blog);
    }

    // GetMapping to retrieve a specific Blog object from the database:
    @GetMapping("{id}")
    public Blog getPostById(@PathVariable("id") Long id) {
        return blogService.getPostById(id);
    }

    // GetMapping to retrieve Blog objects for a specified User from the database:
    @GetMapping("/user/{userId}")
    public List<Blog> getAllPostsByUser(@PathVariable("userId") Long userId) {
        return blogService.getAllPostsByUser(userId);
    }

    // GetMapping to retrieve Blog objects from the database:
    @GetMapping("")
    public List<Blog> getAllPosts() {
        return blogService.getAllPosts();
    }

    // PutMapping to update a specified Blog record with the supplied JSON Blog object in the database:
    @PutMapping("/{id}")
    public void updatePost(@PathVariable("id") Long id, @RequestBody Blog post) {
        blogService.updatePost(post, id);
    }

    // DeleteMapping to delete a specified Blog record from the database:
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        blogService.deletePost(id);
    }
}