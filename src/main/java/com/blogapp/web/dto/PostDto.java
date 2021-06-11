package com.blogapp.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDto {

    @NotEmpty(message = "Title cannot be null")
    private String title;

    @NotEmpty(message = "Content cannot be null")
    private String content;

    private MultipartFile imageFile;

//  Doing postDto manually
//  post.setTitle(postDto.getTitle());
//  MultipartFile: is how you handle media files e.g image, video, etc
}
