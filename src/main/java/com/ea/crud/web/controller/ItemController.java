package com.ea.crud.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ea.crud.dto.ItemDto;
import com.ea.crud.service.ItemService;
import com.ea.crud.service.UserService;

@Controller
public class ItemController {

	protected static transient Log logger = LogFactory.getLog(ItemController.class);

	@Autowired
	ItemService itemService;
	@Autowired
	UserService userService;

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * When a new user is been registered from the Registration page: 1. the
	 * user is inserted in DB 2. the user is authenticated through
	 * SpringSecurity 3. the acl tables are populated for the new user
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addItemForm", method = RequestMethod.GET)
	public ModelAndView addItem(@ModelAttribute("command") ItemDto item, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("items", itemService.findAll());
		} catch (Exception e) {
			logger.error("Error in addItem", e);
		}
		return new ModelAndView("itemsList", model);
	}

	/**
	 * When a new item is been added the following occurs: 1. the item is added
	 * in DB 2. acl tables are populated for the new item
	 */
	@RequestMapping(value = "/saveItem", method = RequestMethod.POST)
	public ModelAndView saveItem(@ModelAttribute("command") ItemDto item, BindingResult result) {
		try {
			logger.debug("Inserting item:" + item);
			item = itemService.insert(item);
		} catch (Exception e) {
			logger.error("Error in saveItem", e);
		}
		return new ModelAndView("redirect:/addItemForm.html");
	}

	@RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
	public ModelAndView deleteItem(@ModelAttribute("command") ItemDto item, BindingResult result) {
		try {
			logger.debug("deleting item:" + item);
			// itemService.delete(item);
			// ERROR: Removing a detached instance error
			// If you use an DtoManager with a transaction
			// persistence context model outside of an active
			// transaction, each method invocation creates a new
			// persistence context, performs the method action, and
			// ends the persistence context.
			// return new ModelAndView("redirect:/addItemForm.html");
		} catch (Exception e) {
			logger.error("Error in saveItem", e);
		}
		return new ModelAndView("redirect:/addItemForm.html");
	}

}
