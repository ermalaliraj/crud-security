package com.ea.crud.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.ea.crud.dto.RoleDto;
import com.ea.crud.dto.UserDto;

public class UserServiceTest extends AbstractSpringTest{

	protected static final transient Log logger = LogFactory.getLog(UserServiceTest.class);
    
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	
	@Before
	public void init() throws Exception{
		roleService.insert(new RoleDto(RoleDto.ROLE_ADMIN));
		roleService.insert(new RoleDto(RoleDto.ROLE_USER));
	}
	@After
	public void after() throws Exception{
		List<UserDto> listUsers = userService.getAllUsers();
		for (UserDto dto : listUsers) {
			userService.deleteById(dto.getId());
		}
		
		roleService.deleteAllRoles();
	}
	
	/**
	 * A CRUD test USER: Insert, read, update, delete
	 */
	@Test
    public void HP_crud() throws Exception{
    	String u = "ermal";
    	String p = "aliraj";
    	
    	UserDto user = new UserDto(u, p);
    	logger.debug("before inserting userDto: "+user);
    	
    	// 1. Insert
		user = userService.createUser(user);
		logger.debug("after inserted userDto: "+user);
		
		// 2. Get
		UserDto dbUser = userService.getByUsername(user.getUsername());
		logger.debug("User got from DB: "+dbUser);
		assertNotNull(dbUser);
		assertEquals(user.getUsername(), dbUser.getUsername());
		
		// 3. Update
		userService.updatePassword(dbUser, "newpwd");
		logger.debug("Password updated");
		
		UserDto userUpdated = userService.getByUsername(dbUser.getUsername());
		
		logger.debug("User got from DB (pwd updated):"+userUpdated);
		assertNotNull(userUpdated);
		assertEquals(userUpdated.getPassword(), "newpwd");
		
		// 4. delete
		userService.deleteById(userUpdated.getId());
		UserDto deletedUser = userService.getByUsername(userUpdated.getUsername());
		assertNull(deletedUser); 
	}
	
	@Test
    public void HP_userRoles() throws Exception{
    	UserDto user = new UserDto("ermalu", "ermalp");
    	user.addRole(new RoleDto(RoleDto.ROLE_ADMIN));
    	user.addRole(new RoleDto(RoleDto.ROLE_USER));
    	logger.debug("before inserting userDto: "+user);
    	
    	// 1. Insert
		userService.createUser(user);
		logger.debug("after inserted userDto: "+user);
		
		// 2. Get
		UserDto dbUser = userService.getByUsername(user.getUsername());
		logger.debug("User got from DB: "+dbUser);
		assertNotNull(dbUser.getRoles());
		assertEquals(2, dbUser.getRoles().size());
	}
    
	@Test(expected=DataIntegrityViolationException.class)
    public void EX_sameRomeForUser() throws Exception{
    	UserDto user = new UserDto("ermalu", "ermalp");
    	user.addRole(new RoleDto(RoleDto.ROLE_ADMIN));
    	user.addRole(new RoleDto(RoleDto.ROLE_ADMIN));
    	userService.createUser(user);
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
		
    	List<UserDto> list = userService.getAllUsers();
    	
    	assertTrue(list.size() > 1); // since data are persisted in DB
    	assertEquals(list.size(), 3); 
    }

}
