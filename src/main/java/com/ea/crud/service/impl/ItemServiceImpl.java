package com.ea.crud.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ea.crud.dao.ItemDAO;
import com.ea.crud.dto.ItemDto;
import com.ea.crud.service.ItemService;

@Service (value="itemService")
@Transactional
public class ItemServiceImpl implements ItemService {
	
	protected static transient Log logger = LogFactory.getLog(ItemServiceImpl.class);
	
	@Autowired
	private ItemDAO itemDAO;

	@Override
	public ItemDto insert(ItemDto dto) throws Exception {
		return itemDAO.insert(dto);
	}
	
	@Override
	public void delete(ItemDto dto) throws Exception {
		itemDAO.delete(dto);
	}
	
	@Override
	public ItemDto findById(Long id) throws Exception {
		return itemDAO.findById(id);
	}
	
	@Override
	public List<ItemDto> findAll() throws Exception {
		return itemDAO.findAll();
	}

}
