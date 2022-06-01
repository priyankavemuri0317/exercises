package com.revature.developercorner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class TechStack {

    // Data members:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    private String stack;

    //Constructor without ID:
    public TechStack(Long userId, String stack) {
        this.userId = userId;
        this.stack = stack;
    }
}
