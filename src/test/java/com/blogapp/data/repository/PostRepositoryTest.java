package com.blogapp.data.repository;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@Sql(scripts = {"classpath:db/insert.sql"})
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void savePostToDBTest() {

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting");

        log.info("Created a blog post --> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();
    }

    @Test
    void throwExceptionWhenSavingPostWithDuplicateTitle() {

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting");
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();
        log.info("Created a blog post --> {}", blogPost);


        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech?");
        blogPost2.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting");
        log.info("Created a blog post --> {}", blogPost2);
        assertThrows(DataIntegrityViolationException.class, ()-> postRepository.save(blogPost2));
    }

    @Test
    void whenPostIsSaved_thenSaveAuthor() {
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting");
        log.info("Created a blog post --> {}", blogPost);

        Author author = new Author();
        author.setFirstname("John");
        author.setLastname("Wick");
        author.setEmail("john@mail.com");
        author.setPhoneNumber("09079969734");

        //map relationships
        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);
        log.info("Blog post After saving --> {}", blogPost);
    }

    @Test
    void findAllPostInTheDbTest() {

        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(5);
    }
}
