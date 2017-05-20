package com.ea.crud.dao;

import java.util.List;

import com.ea.crud.dto.RoleDto;

public interface RoleDAO {

	RoleDto insert(RoleDto dto) throws Exception;
	
	void delete(RoleDto dto) throws Exception;

	RoleDto getByName(String name) throws Exception;
	
	List<RoleDto> getAllRoles() throws Exception;
	
}
