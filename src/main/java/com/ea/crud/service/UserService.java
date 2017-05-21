package com.ea.crud.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ea.crud.dto.UserDto;

public interface UserService extends UserDetailsService {

	public UserDetails loadUserByUsername(String userName);
	
	public UserDto getByUsername(String userName) throws Exception;
	
	public UserDto createUser(UserDto u) throws Exception;

	public void updateUser(UserDto u) throws Exception;
	
	public void updatePassword(UserDto u, String oldPassword) throws Exception;
	
	void deleteById(Long id) throws Exception;
	
	void deleteByUsername(String username) throws Exception;

	public List<UserDto> getAllUsers() throws Exception;
	
}
