package com.revature.developercorner.data;

import com.revature.developercorner.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// QuestionRepository Interface Class
// This interface class will handle the data logic of the Question objects in the database for the application.
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
