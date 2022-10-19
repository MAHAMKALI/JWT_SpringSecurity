package com.SpringSecurity.Jwt_SpringSecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringSecurity.Jwt_SpringSecurity.Entity.JWTRequest;
import com.SpringSecurity.Jwt_SpringSecurity.Entity.JWTResponse;
import com.SpringSecurity.Jwt_SpringSecurity.Entity.UserEntity;
import com.SpringSecurity.Jwt_SpringSecurity.service.UserService;
import com.SpringSecurity.Jwt_SpringSecurity.utility.JWTUtility;


@RestController
public class JwtController {
		
		@Autowired
		private JWTUtility jwtutility;
		
		@Autowired
		private AuthenticationManager authenticationManager;
		
		@Autowired
		private UserService  userService;
		
		
		@GetMapping("/home")
		public String homePage() {
			return "Welcome to spring boot with spring security jwt";
		}
		
		@PostMapping("/authenticate")
		public JWTResponse authenticate(@RequestBody JWTRequest jwtrequest) throws Exception {
			
			try {
				
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								jwtrequest.getUsername(), 
								jwtrequest.getPassword()
						)
						
				);
			}catch(BadCredentialsException e) {
				throw new Exception("INVALID CREDENTIASL",e);
			}
			
			final UserDetails userDetails = userService.loadUserByUsername(jwtrequest.getUsername());
			
			final String token = jwtutility.generateToken(userDetails);
			
			return new JWTResponse(token);
			
		}
		
		@GetMapping("/allusers")
		public List<UserEntity> getAllUserDetsils(){
			return userService.getAllUserDetails();
		}
		
		@GetMapping("/getUserByName/{username}")
		public UserEntity getUserByName(@PathVariable String username) {
			
			return userService.getUserByName(username);
		}
}



