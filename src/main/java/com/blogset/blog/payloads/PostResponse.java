package com.blogset.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
	
	
	private List<PostDto> content;
	private int pageNumber;
	private  int pageSize;
	private long totalElement;
	private int totalPage;
	private boolean lastPage;
	private String sortDir;
	

}
