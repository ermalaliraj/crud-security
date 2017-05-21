package com.ea.crud.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

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
	public UserDto createUser(UserDto user) throws Exception {
		try {
			UserEntity e = new UserEntity();
			e.setId(user.getId());
			e.setUsername(user.getUsername());
			e.setPassword(user.getPassword());
			List<RoleEntity> roles = rolesFromDtoList(user.getRoles());
			e.setRoles(roles);
			entityManager.persist(e);
			user.setId(e.getId());
			return user;
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
			e.setId(user.getId());
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
	public void deleteById(Long id) throws Exception {
		try {
			UserEntity e = entityManager.getReference(UserEntity.class, id);
			entityManager.remove(e);
		} catch (Exception e) {
			logger.error("Error deleting user with id: " + id , e);
			throw e;
		}
	}
	
	public void deleteByUsername(String username) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("delete from UserEntity where username = :username ");
			
			Query q = entityManager.createNativeQuery(sb.toString());
			q.setParameter("username", username);
			
			int count = q.executeUpdate();
			logger.info("Deleted total nr. users: " + count);
		} catch (Exception e) {
			logger.error("Error deleting user: " + username, e);
			throw e;
		}
	}
	
	@Override
	public UserDto getById(Long id) throws Exception {
		UserDto dto = null;
		try {
			UserEntity e = (UserEntity) entityManager.find(UserEntity.class, id);
			if (e != null) {
				dto = new UserDto();
				dto.setId(e.getId());
				dto.setUsername(e.getUsername());
				dto.setPassword(e.getPassword());
				List<RoleDto> roles = dtoRolesFromEntityList(e.getRoles());
				dto.setRoles(roles);
			}
		} catch(NoResultException e){
			logger.warn("No entity foudn with id: " + id);
		} catch (Exception e) {
			logger.error("Error in findByID id: " + id, e);
			throw e;
		}
		return dto;
	}


	@Override
	public UserDto getByUsername(String username) throws Exception {
		UserDto dto = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select e from UserEntity e ");
			sb.append("where e.username = :username ");
			
			Query q = entityManager.createQuery(sb.toString());
			q.setParameter("username", username);
			
			UserEntity e = (UserEntity) q.getSingleResult();
			if (e != null) {
				dto = new UserDto();
				dto.setId(e.getId());
				dto.setUsername(e.getUsername());
				dto.setPassword(e.getPassword());
				List<RoleDto> roles = dtoRolesFromEntityList(e.getRoles());
				dto.setRoles(roles);
			}
		} catch(NoResultException e){
			logger.warn("No entity found with username: " + username);
		} catch (Exception e) {
			logger.error("Error in getByUsername username: " + username, e);
			throw e;
		}
		return dto;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {
		UserDto user = null;
		try {
			logger.info("Login utente '" + userName + "'");
			user = getByUsername(userName);
//			if (user == null) {
//				logger.warn("Utente non trovato");
//				throw new UsernameNotFoundException(userName);
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
				dto.setId(e.getId());
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
