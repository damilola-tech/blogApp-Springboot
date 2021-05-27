package com.blogapp.data.models;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "blog_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(nullable = false, length = 50)
    private String title;
    @Column(length = 500)
    private String content;
    @Column()
    private String coverImageUrl;

    @ManyToOne      //Many blog_posts extending to one author
    @JoinColumn
    private Author author;

    private LocalDate dateCreated;
    private LocalDate dateModified;

    @OneToMany       // One blog_posts to many comments...that's also why its a List
    private List<Comment> comments;

}
