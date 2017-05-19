package com.ea.crud.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginAOP extends AbstractSpringTest {

	protected static final transient Log logger = LogFactory.getLog(LoginAOP.class);

	@Autowired
	UserService userService;

	/**
	 * Each login will be logged
	 */
	@Test
	public void testCRUD() {
		userService.loadUserByUsername("ermal");

	}
}
