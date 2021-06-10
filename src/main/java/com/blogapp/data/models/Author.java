package com.blogapp.data.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany
    @ToString.Exclude   // Exclude the toString so that List of posts will not be included wherever author is called but other instance variables here will be included
    private List<Post> posts;

    public void addPost(Post post) {
        if(posts == null) {
            posts = new ArrayList<>();
        }

        posts.add(post);
    }
}
