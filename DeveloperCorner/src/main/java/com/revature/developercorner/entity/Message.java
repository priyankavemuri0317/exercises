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

public class Message {

    // Data members:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @Column(nullable = false)
    private Long sender;
    @Column(nullable = false)
    private Long recipient;

    //Constructor without ID:
    public Message(String message, Long sender, Long recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }
}
