package com.blogset.blog.services;

import java.util.List;

import com.blogset.blog.entities.Post;
import com.blogset.blog.payloads.PostDto;
import com.blogset.blog.payloads.PostResponse;

public interface PostService {
	
//	create post
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
//	update
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
//	delete
	
	void deletePost(Integer postId);
	
//	get all post
	
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
//	getPostById
	
	PostDto getPostById(Integer postId);
	
//	get all post by category
	
	PostResponse getPostsByCategory(Integer categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);
	
//	get all post by user 
	
	public PostResponse getPostsByUser(Integer userId, int pageNumber, int pageSize, String sortBy, String sortDir);
	
//	search posts
	
	List<PostDto> searchPost(String keyword);

}
