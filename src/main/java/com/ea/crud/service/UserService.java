package com.ea.crud.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ea.crud.dto.UserDto;

public interface UserService extends UserDetailsService {

	public UserDetails loadUserByUsername(String userName);
	
	public UserDto findById(String id) throws Exception;
	
	public void createUser(UserDto u) throws Exception;

	public void updateUser(UserDto u) throws Exception;
	
	public void updatePassword(UserDto u, String oldPassword) throws Exception;
	
	void delete(UserDto u) throws Exception;

	public List<UserDto> findAllOrderById() throws Exception;
	
}
