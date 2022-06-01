package com.revature.developercorner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Blog {

    // Data members:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    private String title;
    private String content;
    private Integer upVotes;
    private Integer downVotes;
    private Timestamp created_at;
    private Timestamp updated_at;

    //Constructor without ID:
    public Blog(String title, String content, Integer upVotes, Integer downVotes, Timestamp created_at, Timestamp updated_at, Long userId) {
        this.title = title;
        this.content = content;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.userId = userId;
    }


}


