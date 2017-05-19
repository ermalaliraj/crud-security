package com.ea.crud.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ea.crud.dao.GenericDAO;
import com.ea.crud.dao.ItemDAO;
import com.ea.crud.dto.ItemDto;
import com.ea.crud.entity.ItemEntity;

@Repository
@Transactional
public class ItemDAOImpl extends GenericDAO implements ItemDAO {

	protected static final transient Log logger = LogFactory.getLog(ItemDAOImpl.class);

	@Override
	public ItemDto insert(ItemDto dto) throws Exception {
		try {
			ItemEntity e = new ItemEntity();
			e.setName(dto.getName());
			e.setDescription(dto.getDescription());
			entityManager.persist(e);

			dto.setId(e.getId());
			return dto;
		} catch (Exception e) {
			logger.error("Error inserting dto: " + dto, e);
			throw e;
		}
	}

	@Override
	public void delete(ItemDto dto) throws Exception {
		try {
			ItemEntity e = entityManager.getReference(ItemEntity.class, dto.getId());
			entityManager.remove(e);
		} catch (javax.persistence.EntityNotFoundException ex) {
			logger.error("Entity not found. dto: " + dto, ex);
		} catch (Exception e) {
			logger.error("Error deleting dto: " + dto, e);
			throw e;
		}
	}

	@Override
	public ItemDto findById(Long id) throws Exception {
		try {
			ItemEntity e = (ItemEntity) entityManager.find(ItemEntity.class, id);
			ItemDto dto = null;
			if (e != null) {
				dto = new ItemDto();
				dto.setId(e.getId());
				dto.setName(e.getName());
				dto.setDescription(e.getDescription());
			}
			return dto;
		} catch (Exception e) {
			logger.error("Error findById: " + id, e);
			throw e;
		}
	}

	@Override
	public List<ItemDto> findAll() throws Exception {
		try {
			@SuppressWarnings("unchecked")
			List<ItemEntity> eList = entityManager.createQuery("select e from ItemEntity e order by e.id")
					.getResultList();
			List<ItemDto> dtoList = new ArrayList<ItemDto>();
			ItemDto dto = null;
			for (ItemEntity e : eList) {
				dto = new ItemDto();
				dto.setId(e.getId());
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

}
