package com.revature.developercorner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Question {

    // Data members:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    private String language;
    private String question;
    private Timestamp created_at;
    private Timestamp updated_at;

    //Constructor without ID:
    public Question(Long userId, String language, String question, Timestamp created_at, Timestamp updated_at) {
        this.userId = userId;
        this.language = language;
        this.question = question;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
