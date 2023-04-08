package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//describe better in swagger ui
@Schema(
		description = "LoginDto Model Information"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

	private String usernameOrEmail;
	private String password;
}
