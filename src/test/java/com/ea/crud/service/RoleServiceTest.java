package com.ea.crud.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.ea.crud.dto.RoleDto;

public class RoleServiceTest extends AbstractSpringTest{

	protected static final transient Log logger = LogFactory.getLog(RoleServiceTest.class);
    
	@Autowired
	RoleService roleService;
	
	@After
	public void init() throws Exception{
		roleService.deleteAllRoles();
//		List<RoleDto> list = roleService.getAllRoles();
//		for (RoleDto dto : list) {
//			roleService.delete(dto);
//		}
	}
	
	@Test
    public void testCRUD() throws Exception{
    	RoleDto newDto = new RoleDto(RoleDto.ROLE_ADMIN);
    	logger.debug("before inserting dto: "+newDto);
    	
    	// 1. Insert
		roleService.insert(newDto);
		logger.debug("after inserted dto: "+newDto);
		
		// 2. Get
		RoleDto dbDto = roleService.getByName(RoleDto.ROLE_ADMIN);
		logger.debug("User got from DB: "+dbDto);
		assertNotNull(dbDto);
		
		roleService.insert(new RoleDto(RoleDto.ROLE_USER));
		
		List<RoleDto> list = roleService.getAllRoles();
		logger.debug("All DTOs from DB: "+list);
		assertNotNull(list);
		assertEquals(2,  list.size());
		
		roleService.deleteAllRoles();
		
		list = roleService.getAllRoles();
		logger.debug("All DTOs from DB: "+list);
		assertNotNull(list);
		assertEquals(0,  list.size());
	}
	

	@Test(expected=DataIntegrityViolationException.class)
    public void EX_testAlreadyPresentUser() throws Exception{
    	roleService.insert(new RoleDto(RoleDto.ROLE_ADMIN));
		roleService.insert(new RoleDto(RoleDto.ROLE_ADMIN));
	}
}
