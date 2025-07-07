package com.blogset.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blogset.blog.entities.Category;
import com.blogset.blog.entities.Post;
import com.blogset.blog.entities.User;

public interface PostRepo extends  JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining (String title);
	
	Page<Post> findByCategory(Category category, Pageable pageable);
	
	Page<Post> findByUser(User user, Pageable pageable);

}
