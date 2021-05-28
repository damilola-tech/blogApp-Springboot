package com.blogapp.service.post;

import com.blogapp.data.models.Post;
import com.blogapp.data.repository.PostRepository;
import com.blogapp.web.dto.PostDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;


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
    void whenTheServiceMethodIsCalled_thenRepositoryIsCalledOnceTest() {
        when(postServiceImpl.savePost(new PostDto())).thenReturn(testPost);
        postServiceImpl.savePost(new PostDto());

        verify(postRepository.save(testPost), times(1));
    }
}

