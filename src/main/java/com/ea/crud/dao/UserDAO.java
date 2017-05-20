package com.ea.crud.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.ea.crud.dto.UserDto;

public interface UserDAO {

	void createUser(UserDto user) throws Exception;
	
	void updateUser(UserDto user) throws Exception;

	void delete(UserDto user) throws Exception;

	UserDto getByUsername(String username) throws Exception;
	UserDetails loadUserByUsername(String username);
	
	List<UserDto> getAllUsers() throws Exception;

}
