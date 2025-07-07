package com.blogset.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogset.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{
	

}
