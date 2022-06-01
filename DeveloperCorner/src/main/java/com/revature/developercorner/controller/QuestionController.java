package com.revature.developercorner.controller;

import com.revature.developercorner.entity.Question;
import com.revature.developercorner.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// QuestionController Class
// This class will handle the HTTP Requests for the API/resource paths associated with the Question objects.
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/questions")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    // PostMapping to add a Question to the database:
    @PostMapping("")
    public void addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
    }

    // GetMapping to retrieve a specific Question object from the database:
    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable("id") Long id) {
        return questionService.getQuestionById(id);
    }

    // GetMapping to retrieve Question objects from the database:
    @GetMapping("")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // PutMapping to update a specified Question with the supplied JSON Question object in the database:
    @PutMapping("/{id}")
    public void updateQuestion(@PathVariable("id") Long id, @RequestBody Question question) {
        questionService.updateQuestion(question, id);
    }

    // DeleteMapping to delete a specified Question record from the database:
    @DeleteMapping("/questions{id}")
    public void deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
    }


}