package com.SpringSecurity.Jwt_SpringSecurity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.SpringSecurity.Jwt_SpringSecurity.Entity.UserEntity;
import com.SpringSecurity.Jwt_SpringSecurity.exception.ResourceNotFoundException;
import com.SpringSecurity.Jwt_SpringSecurity.repository.UserRepository;


@Component
@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	 
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		UserEntity userEntity = userRepository.findByusername(username);
		
//		int id =2;
//		UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		String name = userEntity.getUsername();
		 
		String pass = userEntity.getPassword();
		
		return new User(name,pass,new ArrayList<>());
		
	}



	public List<UserEntity> getAllUserDetails() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public UserEntity getUserByName(String username) {
		
		return userRepository.findByusername(username);
	}
	
}
