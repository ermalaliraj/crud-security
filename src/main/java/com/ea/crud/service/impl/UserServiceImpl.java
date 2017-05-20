package com.ea.crud.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ea.crud.dao.UserDAO;
import com.ea.crud.dto.UserDto;
import com.ea.crud.service.UserService;

@Service (value="userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	protected static transient Log logger = LogFactory.getLog(UserServiceImpl.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public void createUser(UserDto u) throws Exception {
		userDAO.createUser(u);
	}

	@Override
	public void updatePassword(UserDto u, String newPassword) throws Exception {
		UserDto dbU = userDAO.getByUsername(u.getUsername());
		logger.debug("Got from DB "+dbU);
		
		if(!dbU.getPassword().equals(u.getPassword())){
			logger.error("UserDto can not set a new password if he don't remeber the old one.");
			//throw new Exception("");
		}
		
		u.setPassword(newPassword);
		userDAO.updateUser(u);
	}

	@Override
	public UserDto getByUsername(String id) throws Exception {
		return userDAO.getByUsername(id);
	}
	
	@Override
	public void delete(UserDto u) throws Exception {
		userDAO.delete(u);
	}

	@Override
	public List<UserDto> getAllUsers() throws Exception {
		return userDAO.getAllUsers();
	}

	@Override
	public void updateUser(UserDto u) throws Exception {
		userDAO.updateUser(u);
	}

	public UserDetails loadUserByUsername(String username) {
		UserDetails userDetails = userDAO.loadUserByUsername(username);
		return userDetails;
	}

}
