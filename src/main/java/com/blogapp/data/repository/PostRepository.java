package com.blogapp.data.repository;

import com.blogapp.data.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findByTitle(String title);

//    @Query("select p from Post p where p.title = ?1")
//    Post findByPostTitle(String title);

    @Query("select p from Post as p where p.title = :title")
    Post findPostTitle(@Param("title") String title);
}
