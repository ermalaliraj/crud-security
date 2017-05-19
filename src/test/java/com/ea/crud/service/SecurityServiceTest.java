package com.ea.crud.service;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import com.ea.crud.dto.ItemDto;
import com.ea.crud.dto.UserDto;

public class SecurityServiceTest extends AbstractContextSecurityTest{

	protected static final transient Log logger = LogFactory.getLog(SecurityServiceTest.class);
    
	@Autowired
	UserService userService;
	@Autowired
	ItemService itemService;

	@Test
	public void testNewUser() throws Exception{
		UserDto u = new UserDto();
		u.setUsername("ermal");
		u.setPassword("ermal");
		
		userService.updateUser(u);
		UserDto dbUser = (UserDto) userService.loadUserByUsername("ermal");
		assertNotNull(dbUser);
	}
	
	@Test
	public void testAccessDenied() throws Exception {
		login("ermalu", "ermalu", "ROLE_INVALID");
		itemService.findAll();
	}
	
	/**
	 * The test will pass since the method has no restrictions.
	 * @throws Exception 
	 */
	@Test
	public void testNotRestricted() throws Exception {
		itemService.findById(1L);
	}
	
	@Test
	public void testInsertItem() throws Exception {
		login("ermala", "ermala", "ROLE_USER");
		ItemDto i = new ItemDto("item1");
		i = itemService.insert(i);
	}

	@Test(expected=AccessDeniedException.class)
	public void testDeleteItemDenied() throws Exception {
		login("ermala", "ermala", "ROLE_USER");
		ItemDto i = new ItemDto("item1");
		itemService.delete(i);
	}
	
	@Test
	public void testDeleteItemOK() throws Exception {
		login("ermala", "ermala", "ROLE_ADMIN");
		ItemDto i = new ItemDto("item1");
		itemService.delete(i);
	}
	
	@Test(expected=AuthenticationException.class)
	public void testInsertItemNoAuth() throws Exception {
		ItemDto i = new ItemDto("item1");
		i = itemService.insert(i);
		itemService.insert(i);
	}
}
