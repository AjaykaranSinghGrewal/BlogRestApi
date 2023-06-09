package com.springboot.blog.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/posts")
@Tag(
		name="CRUD REST APIs for Post Resource"
)
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	//add below annotation to not allow everyone to use admin access apis with ROLE_USER accounts
	@SecurityRequirement(
			name="basicAuth"
	)
	//create blog post & only ADMIN role user can create a POST
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	//get all posts rest api
	//@GetMapping
	//public List<PostDto> getAllPosts() {
	//	return postService.getAllPosts();
	//}
	
	//get all posts rest api & support pagination
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value="pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value="pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue="id", required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue="asc", required=false) String sortDir
	){
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}
	
	//get post by id rest api
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	//add below annotation to not allow everyone to use admin access apis with ROLE_USER accounts
	@SecurityRequirement(
			name="basicAuth"
	)
	//update post by id rest api
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name="id") long id) {
		PostDto postResponse = postService.updatePost(postDto, id);
		return new ResponseEntity<PostDto>(postResponse, HttpStatus.OK);
	}
	
	//add below annotation to not allow everyone to use admin access apis with ROLE_USER accounts
	@SecurityRequirement(
			name="basicAuth"
	)
	//delete post rest api
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name="id") long id) {
		postService.deletePost(id);
		return new ResponseEntity<>("Post Entity deleted successully.", HttpStatus.OK);
	}
	
}


