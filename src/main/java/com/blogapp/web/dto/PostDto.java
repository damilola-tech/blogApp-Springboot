package com.blogapp.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;

@Data
public class PostDto {

    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Content cannot be null")
    private String content;

    private MultipartFile imageFile;
}
