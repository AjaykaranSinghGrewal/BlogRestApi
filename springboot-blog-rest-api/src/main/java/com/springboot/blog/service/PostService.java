package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto);
	
	//get all posts
	//List<PostDto> getAllPosts();
	
	//get all post with pagination
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	//get post by id
	PostDto getPostById(long id);
	
	//update post by id
	PostDto updatePost(PostDto postDto, long id);
	
	//delete post by id
	void deletePost(long id);
}
