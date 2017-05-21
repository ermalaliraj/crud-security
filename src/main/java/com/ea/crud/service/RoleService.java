package com.ea.crud.service;

import java.util.List;

import com.ea.crud.dto.RoleDto;

public interface RoleService {

	RoleDto insert(RoleDto dto) throws Exception;
	
	void deleteById(Long id) throws Exception;

	void deleteAllRoles() throws Exception;
	
	RoleDto getByName(String name) throws Exception;
	
	List<RoleDto> getAllRoles() throws Exception;

	
	
}
