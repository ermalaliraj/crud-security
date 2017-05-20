package com.ea.crud.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.ea.crud.dao.GenericDAO;
import com.ea.crud.dao.UserDAO;
import com.ea.crud.dto.RoleDto;
import com.ea.crud.dto.UserDto;
import com.ea.crud.entity.RoleEntity;
import com.ea.crud.entity.UserEntity;

@Repository
public class UserDAOImpl extends GenericDAO implements UserDAO {

	protected static final transient Log logger = LogFactory.getLog(UserDAOImpl.class);

	@Override
	public void createUser(UserDto user) throws Exception {
		try {
			UserEntity e = new UserEntity();
			e.setUsername(user.getUsername());
			e.setPassword(user.getPassword());
			List<RoleEntity> roles = rolesFromDtoList(user.getRoles());
			e.setRoles(roles);
			entityManager.persist(e);
		} catch (Exception e) {
			logger.error("Error inserting user: " + user, e);
			throw e;
		}
	}

	@Override
	public void updateUser(UserDto user) throws Exception {
		try {
			if (user == null) {
				throw new IllegalArgumentException("No user specified");
			}
			UserEntity e = new UserEntity();
			e.setUsername(user.getUsername());
			e.setPassword(user.getPassword());
			List<RoleEntity> roles = rolesFromDtoList(user.getRoles());
			e.setRoles(roles);
			entityManager.merge(e);
		} catch (Exception e) {
			logger.error("Error updating user: " + user, e);
			throw e;
		}
	}

	@Override
	public void delete(UserDto dto) throws Exception {
		try {
			UserEntity e = entityManager.getReference(UserEntity.class, dto.getUsername());
			entityManager.remove(e);
		} catch (Exception e) {
			logger.error("Error deleting user: " + dto, e);
			throw e;
		}
	}

	@Override
	public UserDto getByUsername(String username) throws Exception {
		try {
			UserEntity e = (UserEntity) entityManager.find(UserEntity.class, username);
			UserDto dto = null;
			if (e != null) {
				dto = new UserDto();
				dto.setUsername(e.getUsername());
				dto.setPassword(e.getPassword());
				List<RoleDto> roles = dtoRolesFromEntityList(e.getRoles());
				dto.setRoles(roles);
			}
			return dto;
		} catch (Exception e) {
			logger.error("Error in findByID username: " + username, e);
			throw e;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {
		UserDto user = null;
		try {
			logger.info("Login utente '" + userName + "'");
			user = getByUsername(userName);
			if (user == null) {
				logger.warn("Utente non trovato");
				throw new UsernameNotFoundException(userName);
			} 
//			else {
//				user.setRoles(user.getRoles());
//				logger.info("Trovato utente " + user);
//			}
		} catch (Exception e) {
			logger.error("Error in loadUserByUsername: " + userName, e);
		}
		return user;
	}

	@Override
	public List<UserDto> getAllUsers() throws Exception {
		try {
			@SuppressWarnings("unchecked")
			List<UserEntity> listE = entityManager.createQuery("select e from UserEntity e")
					.getResultList();
			List<UserDto> retList = new ArrayList<UserDto>();
			UserDto dto = null;
			for (UserEntity e : listE) {
				dto = new UserDto();
				dto.setUsername(e.getUsername());
				dto.setPassword(e.getPassword());
				List<RoleDto> roles = dtoRolesFromEntityList(e.getRoles());
				dto.setRoles(roles);
				retList.add(dto);
			}
			return retList;
		} catch (Exception e) {
			logger.error("Error in getAllUsers ", e);
			throw e;
		}
	}
	
	private List<RoleEntity> rolesFromDtoList(List<RoleDto> rolesDto) {
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		RoleEntity r;
		for (RoleDto dto : rolesDto) {
			r = new RoleEntity();
			r.setName(dto.getName());
			r.setDescription(dto.getDescription());
			roles.add(r);
		}
		return roles;
	}
	
	private List<RoleDto> dtoRolesFromEntityList(List<RoleEntity> rolesEntity) {
		List<RoleDto> roles = new ArrayList<RoleDto>();
		RoleDto dto;
		for (RoleEntity e : rolesEntity) {
			dto = new RoleDto();
			dto.setName(e.getName());
			dto.setDescription(e.getDescription());
			roles.add(dto);
		}
		return roles;
	}

}
