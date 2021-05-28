package com.blogapp.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostDto {
    private String title;

    private String content;

    private MultipartFile imageFile;
    //We are not taking the dateCreated ans the modified postAutomatically stamped to the database
}
