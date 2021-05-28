package com.blogapp.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private UUID id;

    private String commentatorName;

    @Column(nullable = false, length = 150)
    private String content;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    public Comment(String commentatorName, String content) {
        this.commentatorName = commentatorName;
        this.content = content;
    }
}
