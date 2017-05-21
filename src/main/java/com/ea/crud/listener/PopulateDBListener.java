package com.ea.crud.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ea.crud.dto.RoleDto;
import com.ea.crud.dto.UserDto;
import com.ea.crud.service.RoleService;
import com.ea.crud.service.UserService;

@Component
public class PopulateDBListener implements ServletContextListener {

	protected static final transient Log logger = LogFactory.getLog(PopulateDBListener.class);

	@Autowired //not works here, Use spring context
	RoleService roleService;
	UserService userService;
	
    public void contextInitialized(ServletContextEvent sce) {
    	try {
    		logger.info("Populating DB");
    		roleService = WebApplicationContextUtils
    				.getRequiredWebApplicationContext(sce.getServletContext())
    				.getBean(RoleService.class);
    		userService = WebApplicationContextUtils
    				.getRequiredWebApplicationContext(sce.getServletContext())
    				.getBean(UserService.class);
    		
    		emptyRoleTable();
    		populateRoleTable();
    		populateUserTable();
			logger.info("ROLES inserted in DB");
    	} catch (Exception e) {
			logger.error("Error populating DB!!!", e);
		}
    }

	private void populateRoleTable() throws Exception {
    	roleService.insert(new RoleDto(RoleDto.ROLE_ADMIN));
    	roleService.insert(new RoleDto(RoleDto.ROLE_PUBLISHER));
		roleService.insert(new RoleDto(RoleDto.ROLE_USER));
		roleService.insert(new RoleDto(RoleDto.ROLE_READONLY));
    }

    private void populateUserTable() throws Exception {
    	UserDto user = new UserDto("ermal", "ermal");
    	user.addRole(new RoleDto(RoleDto.ROLE_ADMIN));
		userService.createUser(user);
		
		user = new UserDto("pub", "pub");
    	user.addRole(new RoleDto(RoleDto.ROLE_PUBLISHER));
		userService.createUser(user);
		
		user = new UserDto("user1", "user1");
    	user.addRole(new RoleDto(RoleDto.ROLE_USER));
		userService.createUser(user);

		user = new UserDto("user2", "user2");
		user.addRole(new RoleDto(RoleDto.ROLE_PUBLISHER));
    	user.addRole(new RoleDto(RoleDto.ROLE_USER));
		userService.createUser(user);
		
		user = new UserDto("readonly", "readonly");
		user.addRole(new RoleDto(RoleDto.ROLE_READONLY));
		userService.createUser(user);
	}

    
	public void emptyRoleTable() throws Exception{
		roleService.deleteAllRoles();
	}

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	// do I have to use thread.interrupt()? 
    }
}