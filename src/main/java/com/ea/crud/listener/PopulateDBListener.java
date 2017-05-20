package com.ea.crud.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ea.crud.dto.RoleDto;
import com.ea.crud.service.RoleService;

@Component
public class PopulateDBListener implements ServletContextListener {

	protected static final transient Log logger = LogFactory.getLog(PopulateDBListener.class);

	@Autowired //not works here, Use spring context
	RoleService roleService;
	
    public void contextInitialized(ServletContextEvent sce) {
    	try {
    		logger.info("Populating DB");
    		roleService = WebApplicationContextUtils
    				.getRequiredWebApplicationContext(sce.getServletContext())
    				.getBean(RoleService.class);
    		
    		emptyRoleTable();
    		populateRoleTable();
			logger.info("ROLES inserted in DB");
    	} catch (Exception e) {
			logger.error("Error populating DB!!!", e);
		}
    }
    
    private void populateRoleTable() throws Exception {
    	roleService.insert(new RoleDto(RoleDto.ROLE_ADMIN));
		roleService.insert(new RoleDto(RoleDto.ROLE_USER));
		roleService.insert(new RoleDto(RoleDto.ROLE_READONLY));
    }

	public void emptyRoleTable() throws Exception{
		List<RoleDto> list = roleService.getAllRoles();
		for (RoleDto dto : list) {
			roleService.delete(dto);
		}
	}

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	// do I have to use thread.interrupt()? 
    }
}