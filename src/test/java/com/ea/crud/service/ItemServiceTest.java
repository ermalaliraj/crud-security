package com.ea.crud.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import com.ea.crud.dto.ItemDto;

public class ItemServiceTest extends AbstractSpringTest{

	protected static final transient Log logger = LogFactory.getLog(ItemServiceTest.class);
    
	@Autowired
	ItemService itemService;
	
	/**
	 * CRUD test: Insert, read, update, delete
	 */
	@Test
    public void testCRUD() throws Exception{
    	ItemDto dto = new ItemDto("item1");
    	logger.debug("before inserting: "+dto);

    	// 1. Insert
		dto = itemService.insert(dto);
		logger.debug("after inserting: "+dto);
		
		// 2. Get
		ItemDto dbDto = itemService.findById(dto.getId());
		logger.debug("get from db dbDto: "+dto);
		assertNotNull(dbDto);
		assertEquals(dto.getName(), dbDto.getName());
		
		// 4. delete
		itemService.delete(dbDto);
		ItemDto deletedItem = itemService.findById(dbDto.getId());
		assertNull(deletedItem);
	}
	
	@Test(expected=TransactionSystemException.class)
    public void testDeleteNotPresent() throws Exception{
    	ItemDto dto = new ItemDto("item1");
    	dto.setId(100L);
		itemService.delete(dto);
	}
    
    @Test
    public void testList() throws Exception{
    	itemService.insert(new ItemDto("item1"));
    	itemService.insert(new ItemDto("item2"));
    	itemService.insert(new ItemDto("item3"));
		
    	List<ItemDto> list = itemService.findAll();
    	assertNotNull(list);
    	assertTrue(list.size()>1);
      	assertEquals(3, list.size());
    }

}
