package com.ea.crud.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.ea.crud.dto.UserDto;

public interface UserDAO {

	UserDto createUser(UserDto user) throws Exception;
	
	void updateUser(UserDto user) throws Exception;

	void deleteById(Long id) throws Exception;
	
	void deleteByUsername(String username) throws Exception;

	UserDto getById(Long id) throws Exception;
	
	UserDto getByUsername(String username) throws Exception;
	UserDetails loadUserByUsername(String username);
	
	List<UserDto> getAllUsers() throws Exception;

}
