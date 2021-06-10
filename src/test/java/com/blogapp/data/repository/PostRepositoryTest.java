package com.blogapp.data.repository;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest     // To start the test configuration context.
@Sql(scripts = {"classpath:db/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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

        postRepository.save(blogPost);
        log.info("Created a blog post --> {}", blogPost);
        assertThat(blogPost.getId()).isNotNull();
    }

    @Test
    void throwExceptionWhenSavingPostWithDuplicateTitle() {

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting");

        postRepository.save(blogPost);
        log.info("Created a blog post --> {}", blogPost);
        assertThat(blogPost.getId()).isNotNull();


        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech?");
        blogPost2.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting");

        assertThrows(DataIntegrityViolationException.class,
                ()-> postRepository.save(blogPost2));
        log.info("Created a blog post2 --> {}", blogPost2);
    }

    @Test
    void whenPostIsSaved_thenSaveAuthor() {

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting");

        Author author = new Author();
        author.setFirstname("John");
        author.setLastname("Wick");
        author.setEmail("john@mail.com");
        author.setPhoneNumber("09079969734");

        //map relationships(bidirectional)
        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);
        log.info("Blog post After saving --> {}", blogPost);

        Post savedPost = postRepository.findByTitle("What is Fintech?");
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getAuthor()).isNotNull();
    }

    @Test
    void findAllPosts() {

        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(5);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void deletePostById() {

        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        log.info("Post fetched from the database --> {}", savedPost);
        // delete post
        postRepository.deleteById(savedPost.getId());

        Post deletedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(deletedPost).isNull();
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void updatePostAuthor() {

        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getAuthor()).isNull();
        log.info("Post fetched from the database --> {}", savedPost);

        // update post
        Author author = new Author();
        author.setFirstname("Dami");
        author.setLastname("Lola");
        author.setEmail("dami@gmail.com");
        author.setPhoneNumber("08165797387");

        // map relationships
        savedPost.setAuthor(author);
        author.addPost(savedPost);

        postRepository.save(savedPost);
        log.info("Blog post After saving --> {}", savedPost);

        Post updatedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getAuthor()).isNotNull();
        assertThat(updatedPost.getAuthor()).isEqualTo(author);
    }


    @Test
    void updateSavedPostTitleTest() {

        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo("Title post 1");
        log.info("Post fetched from the database --> {}", savedPost);

        // update post title
        savedPost.setTitle("Pentax Post Title");
        postRepository.save(savedPost);

        Post updatePost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(updatePost).isNotNull();
        assertThat(updatePost.getTitle()).isEqualTo("Pentax Post Title");
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void addCommentToExistingPost() {
        // Fetch the post from the db
        Post savedPost = postRepository.findById(43).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getComments()).isEmpty();
        log.info("Post fetched from the database --> {}", savedPost);

        // create a comment object
        Comment comment1 = new Comment("Billy", "Really insightful!");
        Comment comment2 = new Comment("Peter", "Nice one!");

        //map the post and comment
        savedPost.addComments(comment1, comment2);

        // save the post
        postRepository.save(savedPost);

        Post updatedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getComments()).isNotNull();
        assertThat(updatedPost.getComments()).hasSize(2);

        log.info("Updated Post from the database --> {}", updatedPost);
    }


    @Test
    @Rollback(value = false)
    void findAllPostInDescendingOrderTest() {

        List<Post> allPosts = postRepository.findByOrderByDateCreatedDesc();

        assertThat(allPosts).isNotEmpty();
        log.info("All posts --> {}", allPosts);

        assertTrue(allPosts.get(0).getDateCreated().isAfter(allPosts.get(1).getDateCreated()));

        allPosts.forEach(post -> {
            log.info("Post Date {}", post.getDateCreated());
        });
    }
}

