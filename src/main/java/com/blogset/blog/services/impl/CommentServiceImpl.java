package com.blogset.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogset.blog.entities.Comment;
import com.blogset.blog.entities.Post;
import com.blogset.blog.exceptions.ResourceNotFoundException;
import com.blogset.blog.payloads.CommentDto;
import com.blogset.blog.repositories.CommentRepo;
import com.blogset.blog.repositories.PostRepo;
import com.blogset.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private  CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment com = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "comment id", commentId));

		this.commentRepo.delete(com);
	}

}
