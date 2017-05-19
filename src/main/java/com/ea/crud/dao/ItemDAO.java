package com.ea.crud.dao;

import java.util.List;

import com.ea.crud.dto.ItemDto;

public interface ItemDAO {

	ItemDto insert(ItemDto dto) throws Exception;
	
	void delete(ItemDto dto) throws Exception;

	ItemDto findById(Long id) throws Exception;
	
	List<ItemDto> findAll() throws Exception;
	
}
