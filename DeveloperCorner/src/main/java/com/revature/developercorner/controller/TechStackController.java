package com.revature.developercorner.controller;

import com.revature.developercorner.entity.TechStack;
import com.revature.developercorner.service.TechStackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// TechStackController Class
// This class will handle the HTTP Requests for the API/resource paths associated with the TechStack objects.
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/techstacks")
public class TechStackController {
    @Autowired
    TechStackService techStackService;

    // PostMapping to add a TechStack to the database:
    @PostMapping("")
    public void addTechStack(@RequestBody TechStack techStack) {
        techStackService.addTechStack(techStack);
    }

    // GetMapping to retrieve a specific TechStack object from the database:
    @GetMapping("/{id}")
    public TechStack getTechStackById(@PathVariable("id") Long id) {
        return techStackService.getTechStackById(id);
    }

    // GetMapping to retrieve TechStack objects from the database:
    @GetMapping("")
    public List<TechStack> getAllTechStacks() {
        return techStackService.getAllTechStacks();
    }

    // PutMapping to update a specified TechStack with the supplied JSON TechStack object in the database:
    @PutMapping("/{id}")
    public void updateTechStack(@PathVariable("id") Long id, @RequestBody TechStack techStack) {
        techStackService.updateTechStack(techStack, id);
    }

    // DeleteMapping to delete a specified TechStack record from the database:
    @DeleteMapping("/{id}")
    public void deleteTechStack(@PathVariable("id") Long id) {
        techStackService.deleteTechStack(id);
    }
}