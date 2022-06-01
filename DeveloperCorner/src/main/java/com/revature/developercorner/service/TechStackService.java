package com.revature.developercorner.service;

import com.revature.developercorner.data.TechStackRepository;
import com.revature.developercorner.entity.TechStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TechStackService class
// This class will handle the business logic for the TechStack objects in the application.
@Service
public class TechStackService {

    @Autowired
    TechStackRepository techStackRepository;

    // AddTechStack method
    // This method will take the provided TechStack object and call the TechStackRepository to save the
    //  object into the database:
    public TechStack addTechStack(TechStack techStack){
        techStackRepository.save(techStack);
        return techStack;
    }

    // GetTechStackById method
    // This method will call the TechStackRepository to retrieve the specified TechStack by the provided id:
    public TechStack getTechStackById(Long id) { return techStackRepository.findById(id).get(); };

    // GetAllTechStacks method
    // This method will call the TechStackRepository to retrieve the List of all TechStack objects in the database:
    public List<TechStack> getAllTechStacks(){
        return techStackRepository.findAll();
    }

    // UpdateTechStack method
    // This method will update the specified TechStack record in the database:
    public TechStack updateTechStack(TechStack techStack, Long id) {
        // Get the database's TechStack by the passed id and assign that to a new TechStack object:
        TechStack techStackDB = techStackRepository.findById(id).get();

        // Set the database's Stack name with the provided TechStack object's Stack name:
        techStackDB.setStack(techStack.getStack());
        techStackDB.setUserId(techStack.getUserId());

        // Call the TechStackRepository to save the newly updated database TechStack object into the same record:
        techStackRepository.save(techStackDB);
        return techStackDB;
    }

    // DeleteTechStack method
    // This method will delete the specified TechStack record from the database with the supplied id:
    public void deleteTechStack(Long id) {
        techStackRepository.deleteById(id);
    }
}

