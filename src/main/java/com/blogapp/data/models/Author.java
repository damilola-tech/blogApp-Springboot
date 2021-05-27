package com.blogapp.data.models;

import javax.persistence.*;

@Entity
@Data
public class Author {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String lastname;

    private String firstname;

    private String profession;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn
    private Author author;
}
