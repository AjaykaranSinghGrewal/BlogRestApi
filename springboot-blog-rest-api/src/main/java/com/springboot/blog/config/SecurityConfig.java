package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
@Configuration
@EnableMethodSecurity
//enable security in using swagger api
@SecurityScheme(
	    name = "basicAuth",
	    type = SecuritySchemeType.HTTP,
	    scheme = "basic"
)
public class SecurityConfig {
	
	private UserDetailsService userDetailsService;
	
	public SecurityConfig(UserDetailsService userDetailsService){
		this.userDetailsService = userDetailsService;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//enable get requests for all users
		http.csrf().disable()
		.authorizeHttpRequests((authorize) -> authorize
		.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
		//permit authorization web links for everybody
		.requestMatchers("/api/auth/**").permitAll()
		//permit swagger web link for all
		.requestMatchers("/swagger-ui/**").permitAll()
		.requestMatchers("/v3/api-docs/**").permitAll()
		.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	//because we're doing database authentication using AuthenticationManager, the below in-memory details are not required
	//user base authentication
	//@Bean
	//public UserDetailsService userDetailsService() {
	//	UserDetails user = User.builder().username("user").password(passwordEncoder().encode("pass")).roles("USER").build();
	//	
	//	UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
	//	
	//	return new InMemoryUserDetailsManager(user,admin);
	//}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
