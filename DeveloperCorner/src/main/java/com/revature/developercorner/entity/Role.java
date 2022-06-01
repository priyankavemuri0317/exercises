package com.revature.developercorner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "roles")
public class Role {

    // Data members:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role_name;

    // Constructor without ID:
    public Role(String role_name) {
        this.role_name = role_name;
    }
}