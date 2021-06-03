package com.blogapp.service.cloud;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
class CloudinaryCloudStorageServiceImplTest {

    @Autowired
    @Qualifier("cloudinary")
    CloudStorageService cloudStorageServiceImpl;

    @BeforeEach
    void setUp() {
    }

    @Test
    void uploadImageTest() {

        //define a file
        File file = new File("C:\\Users\\DELL\\IdeaProjects\\blogapp\\blogapp\\src\\main\\resources\\static\\asset\\images\\author-image1.jpg");

        assertThat(file.exists()).isTrue();

        Map<Object, Object> params = new HashMap<>();
        params.put("folder", "blogapp");
        params.put("overwrite", "true");

        try {
            cloudStorageServiceImpl.uploadImage(file, params);
        } catch (IOException e) {
            log.error("Error occurred --> {}", e.getMessage());
        }
    }

    @Test
    void uploadMultipartImageFileTest() {

        //define a file
        File file = new File("C:\\Users\\DELL\\IdeaProjects\\blogapp\\blogapp\\src\\main\\resources\\static\\asset\\images\\author-image1.jpg");

        assertThat(file.exists()).isTrue();

        Map<Object, Object> params = new HashMap<>();
        params.put("folder", "blogapp");
        params.put("overwrite", "true");

        try {
            Map<?, ?> response = cloudStorageServiceImpl.uploadImage(file, params);
            log.info("File Uploaded --> {}", response);
        }
        catch (IOException e) {
            log.error("Error occurred --> {}", e.getMessage());
        }
    }
}
