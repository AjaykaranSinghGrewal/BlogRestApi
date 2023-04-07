package com.springboot.blog.payload;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class CommentDto {
	private long id;
	
	//name should not be null or empty
	@NotEmpty(message = "Name should not be empty")
	private String name;
	
	//email should not be null or empty
	@NotEmpty(message = "Email should not be empty")
	@Email
	private String email;
	
	@NotEmpty(message = "Body should not be empty")
	@Size(min=5, message="Comment body must be minimum 5 characters")
	private String body;
}
