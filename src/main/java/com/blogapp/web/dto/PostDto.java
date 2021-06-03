package com.blogapp.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PostDto {

    @NotEmpty(message = "Title cannot be null")
    private String title;

    @NotEmpty(message = "Content cannot be null")
    private String content;

    private MultipartFile imageFile;
}
