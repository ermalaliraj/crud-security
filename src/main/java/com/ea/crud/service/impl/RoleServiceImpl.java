package com.ea.crud.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ea.crud.dao.RoleDAO;
import com.ea.crud.dto.RoleDto;
import com.ea.crud.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	protected static transient Log logger = LogFactory.getLog(RoleServiceImpl.class);
	
	@Autowired
	private RoleDAO roleDAO;

	@Override
	public RoleDto insert(RoleDto dto) throws Exception {
		return roleDAO.insert(dto);
	}
	
	@Override
	public void delete(RoleDto dto) throws Exception {
		roleDAO.delete(dto);
	}
	
	@Override
	public RoleDto getByName(String name) throws Exception {
		return roleDAO.getByName(name);
	}
	
	@Override
	public List<RoleDto> getAllRoles() throws Exception {
		return roleDAO.getAllRoles();
	}

}
