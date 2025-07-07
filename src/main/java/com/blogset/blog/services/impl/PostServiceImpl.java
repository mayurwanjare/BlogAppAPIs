package com.blogset.blog.services.impl;

import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.blogset.blog.entities.Category;
import com.blogset.blog.entities.Post;
import com.blogset.blog.entities.User;
import com.blogset.blog.exceptions.ResourceNotFoundException;
import com.blogset.blog.payloads.PostDto;
import com.blogset.blog.payloads.PostResponse;
import com.blogset.blog.repositories.CategoryRepo;
import com.blogset.blog.repositories.PostRepo;
import com.blogset.blog.repositories.UserRepo;
import com.blogset.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired 
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "UserId", userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("defult.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
        this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();		
		List<PostDto> postDtos = allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos); 
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPage(pagePost.getTotalPages());
	    postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostsByCategory(Integer categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {

	    Category cat = this.categoryRepo.findById(categoryId)
	            .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

	    Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

	    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
	    Page<Post> pagePosts = this.postRepo.findByCategory(cat, pageable);

	    List<PostDto> postDtos = pagePosts.getContent()
	            .stream()
	            .map(post -> this.modelMapper.map(post, PostDto.class))
	            .collect(Collectors.toList());

	    PostResponse response = new PostResponse();
	    response.setContent(postDtos);
	    response.setPageNumber(pagePosts.getNumber());
	    response.setPageSize(pagePosts.getSize());
	    response.setTotalElement(pagePosts.getTotalElements());
	    response.setTotalPage(pagePosts.getTotalPages());
	    response.setLastPage(pagePosts.isLast());
	    response.setSortDir(sortDir);

	    return response;
	}

	@Override
	public PostResponse getPostsByUser(Integer userId, int pageNumber, int pageSize, String sortBy, String sortDir) {

	    User user = userRepo.findById(userId)
	                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

	    Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

	    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

	    Page<Post> pagePost = postRepo.findByUser(user, pageable);

	    List<PostDto> postDtos = pagePost.getContent().stream()
	                .map(post -> modelMapper.map(post, PostDto.class))
	                .collect(Collectors.toList());

	    PostResponse postResponse = new PostResponse();
	    postResponse.setContent(postDtos);
	    postResponse.setPageNumber(pagePost.getNumber());
	    postResponse.setPageSize(pagePost.getSize());
	    postResponse.setTotalElement(pagePost.getTotalElements());
	    postResponse.setTotalPage(pagePost.getTotalPages());
	    postResponse.setLastPage(pagePost.isLast());
	    postResponse.setSortDir(sortDir);

	    return postResponse;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	    return postDtos;
	}
	
	

}
