package com.springboot.blog.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	private AuthenticationManager authenticationManager;
	private UserRepository userReporsitory;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	public AuthServiceImpl(AuthenticationManager authenticationManager,
			UserRepository userReporsitory, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		super();
		this.authenticationManager = authenticationManager;
		this.userReporsitory = userReporsitory;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String login(LoginDto loginDto) {
		// TODO Auto-generated method stub
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "User logged-in successfully";
	}
	
	 @Override 
	 public String register(RegisterDto registerDto){ 
		 // TODO Auto-generated method stub 
		 
		 //add check if username already exixts in database
		 if(userReporsitory.existsByUsername(registerDto.getUsername())) {
		 System.out.println("Username exists"); }
		  
		 //add check for email in database
		 if(userReporsitory.existsByEmail(registerDto.getEmail())) {
		 System.out.println("Email exists"); }
		  
		 User user = new User(); user.setName(registerDto.getName());
		 user.setEmail(registerDto.getEmail());
		 user.setUsername(registerDto.getUsername());
		 user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		  
		 Set<Role> roles = new HashSet<>(); Role userRole =
		 roleRepository.findByName("ROLE_USER").get(); roles.add(userRole);
		 user.setRoles(roles);
		  
		 userReporsitory.save(user);
	  
		 return "User Registered successfully"; 
	 }
	 

}
