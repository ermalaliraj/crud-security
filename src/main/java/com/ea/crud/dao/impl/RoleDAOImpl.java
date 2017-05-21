package com.ea.crud.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ea.crud.dao.GenericDAO;
import com.ea.crud.dao.RoleDAO;
import com.ea.crud.dto.RoleDto;
import com.ea.crud.entity.RoleEntity;

@Repository
public class RoleDAOImpl extends GenericDAO implements RoleDAO {

	protected static final transient Log logger = LogFactory.getLog(RoleDAOImpl.class);

	@Override
	public RoleDto getById(Long id) throws Exception {
		try {
			RoleEntity e = (RoleEntity) entityManager.find(RoleEntity.class, id);
			RoleDto dto = null;
			if (e != null) {
				dto = new RoleDto();
				dto.setName(e.getName());
				dto.setDescription(e.getDescription());
			}
			return dto;
		} catch (Exception e) {
			logger.error("Error getById: " + id, e);
			throw e;
		}
	}
	
	public RoleDto getByName(String name) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select e from RoleEntity e ");
			sb.append("where e.name = :name ");
			
			Query q = entityManager.createQuery(sb.toString());
			q.setParameter("name", name);
			
			RoleEntity e = (RoleEntity) q.getSingleResult();
			RoleDto dto = null;
			if (e != null) {
				dto = new RoleDto();
				dto.setName(e.getName());
				dto.setDescription(e.getDescription());
			}
			return dto;
		} catch (Exception e) {
			logger.error("Error getByName: " + name, e);
			throw e;
		}
	}
	
	@Override
	public RoleDto insert(RoleDto dto) throws Exception {
		try {
			RoleEntity e = new RoleEntity();
			e.setName(dto.getName());
			e.setDescription(dto.getDescription());
			entityManager.persist(e);
			return dto;
		} catch (Exception e) {
			logger.error("Error inserting dto: " + dto, e);
			throw e;
		}
	}

	@Override
	public void deleteById(Long id) throws Exception {
		try {
			RoleEntity e = entityManager.getReference(RoleEntity.class, id);
			entityManager.remove(e);
		} catch (javax.persistence.EntityNotFoundException ex) {
			logger.error("Entity not found with id: " + id, ex);
		} catch (Exception e) {
			logger.error("Error deleting with id " + id, e);
			throw e;
		}
		
	}

	@Override
	public List<RoleDto> getAllRoles() throws Exception {
		try {
			@SuppressWarnings("unchecked")
			List<RoleEntity> eList = entityManager.createQuery("select e from RoleEntity e")
					.getResultList();
			List<RoleDto> dtoList = new ArrayList<RoleDto>();
			RoleDto dto = null;
			for (RoleEntity e : eList) {
				dto = new RoleDto();
				dto.setName(e.getName());
				dto.setDescription(e.getDescription());
				dtoList.add(dto);
			}
			return dtoList;
		} catch (Exception e) {
			logger.error("Error in findAll()", e);
			throw e;
		}
	}
	
	@Override
	public void deleteAllRoles() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from RoleEntity");
		
		Query q = entityManager.createNativeQuery(sb.toString());
		int count = q.executeUpdate();
		logger.info("Deleted total nr. roles: " + count);
	}

}
