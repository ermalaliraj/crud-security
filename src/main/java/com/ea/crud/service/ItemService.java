package com.ea.crud.service;

import java.util.List;

import com.ea.crud.dto.ItemDto;

public interface ItemService {

	public ItemDto insert(ItemDto dto) throws Exception;

	public void delete(ItemDto dto) throws Exception;

	public ItemDto findById(Long id) throws Exception;

	public List<ItemDto> findAll() throws Exception;

}
