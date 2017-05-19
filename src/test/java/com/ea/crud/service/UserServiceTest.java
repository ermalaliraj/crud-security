package com.ea.crud.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ea.crud.dto.UserDto;

public class UserServiceTest extends AbstractSpringTest{

	protected static final transient Log logger = LogFactory.getLog(UserServiceTest.class);
    
	@Autowired
	UserService userService;
	
	/**
	 * A CRUD test: Insert, read, update, delete
	 */
	@Test
    public void testCRUD() throws Exception{
    	String u = "ermal";
    	String p = "aliraj";
    	
    	UserDto user = new UserDto();
    	user.setUsername(u);
    	user.setPassword(p);
    	logger.debug("before inserting userDto: "+user);
    	
    	// 1. Insert
		userService.createUser(user);
		logger.debug("after inserted userDto: "+user);
		
		// 2. Get
		UserDto dbUser = userService.findById(user.getUsername());
		logger.debug("User got from DB: "+dbUser);
		assertNotNull(dbUser);
		assertEquals(user.getUsername(), dbUser.getUsername());
		
		// 3. Update
		userService.updatePassword(dbUser, "newpwd");
		logger.debug("Password updated");
		
		UserDto userUpdated = userService.findById(dbUser.getUsername());
		
		logger.debug("User got from DB (pwd updated):"+userUpdated);
		assertNotNull(userUpdated);
		assertEquals(userUpdated.getPassword(), "newpwd");
		
		// 4. delete
		userService.delete(userUpdated);
		UserDto deletedUser = userService.findById(userUpdated.getUsername());
		assertNull(deletedUser); 
	}
    
    /**
     * Insert three users. 
     * Get the list of users, print it and check if has exactly 3 elements.
     */
    @Test
    public void testList() throws Exception{
    	UserDto a = new UserDto();
    	a.setUsername("a");
    	a.setPassword("a1");
    	userService.createUser(a);
    	
    	UserDto b = new UserDto();
    	b.setUsername("b");
    	b.setPassword("b1");
    	userService.createUser(b);
    	
    	UserDto c = new UserDto();
    	c.setUsername("c");
    	c.setPassword("c1");
    	userService.createUser(c);
		
    	List<UserDto> list = userService.findAllOrderById();
    	
    	assertTrue(list.size() > 1); // since data are persisted in DB
    	assertEquals(list.size(), 3); 
    }

}
