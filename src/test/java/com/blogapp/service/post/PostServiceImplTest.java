package com.blogapp.service.post;

import com.blogapp.data.models.Post;
import com.blogapp.data.repository.PostRepository;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


class PostServiceImplTest {

    @Mock
    PostRepository postRepository;

    // Injecting into the service ourselves
    @InjectMocks
    PostService postServiceImpl = new PostServiceImpl();

    Post testPost;

    @BeforeEach
    void setUp() {
        testPost = new Post();
        MockitoAnnotations.openMocks(this);     // Means open the mocks in this class

    }

    @Test
    void whenTheServiceMethodIsCalled_thenRepositoryIsCalledOnceTest() throws PostObjectIsNullException {
        when(postServiceImpl.savePost(new PostDto())).thenReturn(testPost);
        postServiceImpl.savePost(new PostDto());

        verify(postRepository, times(1)).save(testPost);
    }

    @Test
    void whenTheFindAllMethodIsCalled_thenReturnAListOfPost() {
        List<Post> postList = new ArrayList<>();
        when(postServiceImpl.findAllPosts()).thenReturn(postList);
        postServiceImpl.findAllPosts();

        verify(postRepository, times(1)).findAll();
    }
}

