package com.blogapp.data.models;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(nullable = false, length = 50, unique = true)
    private String title;
    @Column(length = 500)
    private String content;
    @Column()
    private String coverImageUrl;

    @ManyToOne(cascade = CascadeType.PERSIST)     //Many blog_posts extending to one author
    @JoinColumn
    private Author author;

    @CreationTimestamp
    private LocalDate dateCreated;
    @UpdateTimestamp
    private LocalDate dateModified;

    @OneToMany       // One blog_posts to many comments...that's also why its a List
    private List<Comment> comments;
}
