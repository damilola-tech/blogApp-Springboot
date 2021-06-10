package com.blogapp.data.models;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@Table(name = "blog_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(length = 500)
    private String content;

    private String coverImageUrl;

    @ManyToOne(cascade = CascadeType.PERSIST)     // Many blog_posts extending to one author.
    @JoinColumn
    private Author author;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime dateModified;

    @OneToMany(cascade = CascadeType.ALL)       // One blog_posts to many comments...that's also why its a List.
    private List<Comment> comments;
                                // Line 44: variable-length argument; is the same as passing in an array of that type, in this case Comment
    public void addComments(Comment... comment) {   // Comment... means you can pass in as many comments as possible
        if(comments == null) {      // Line means if the List of comments is not instantiated or created
            comments = new ArrayList<>();   // Creating a new List(here, as its implementation which is the ArrayList)
        }
        comments.addAll(Arrays.asList(comment));    // We're converting the Array back to a list and saving all
    }
}
