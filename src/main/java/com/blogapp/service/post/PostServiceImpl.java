package com.blogapp.service.post;

import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import com.blogapp.data.repository.PostRepository;
import com.blogapp.service.cloud.CloudStorageService;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CloudStorageService cloudStorageService;

    @Override
    public Post savePost(PostDto postDto) throws PostObjectIsNullException {

        if (postDto == null) {
            throw new PostObjectIsNullException("Post cannot be null");
        }

        log.info("Post Dto --> {}", postDto);

        Post post = new Post();

        if (postDto.getImageFile() != null && !postDto.getImageFile().isEmpty()) {
            try {
                Map<?, ?> uploadResult =
                        cloudStorageService.uploadImage(postDto.getImageFile(), ObjectUtils.asMap(
                                "public_id", "blogapp/" + postDto.getImageFile().getName(),
                                "overwrite", true
                        ));

                log.info("Upload Response --> {}", uploadResult);

                post.setCoverImageUrl((String) uploadResult.get("url"));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(postDto, post);

        log.info("Post object after mapping --> {}", post);

//        try {
            return postRepository.save(post);
//        } catch (DataIntegrityViolationException e) {
//            log.info("Exception occurred --> {}", e.getMessage());
//            throw e;
//        }
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(PostDto postDto) {
        return null;
    }

    @Override
    public Post findById(Integer Id) {
        return null;
    }

    @Override
    public void deletePostById(Integer Id) {

    }

    @Override
    public Post addCommentToPost(Integer id, Comment comment) {
        return null;
    }
}
