package com.ea.crud.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ea.crud.dto.UserDto;
import com.ea.crud.service.UserService;

@Controller
public class UserController {

	protected static transient Log logger = LogFactory.getLog(UserController.class);

	@Autowired
	UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/")
	public ModelAndView login(HttpSession session) {
		saveLoggedUserSession(session);
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView welcome(HttpSession session) {
		saveLoggedUserSession(session);
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLoginPage(@RequestParam(value = "error", required = false) boolean error) {
		Map<String, Object> model = new HashMap<String, Object>();

		if (error == true) {
			model.put("error", "Bad Credentials");
		} else {
			model.put("error", "");
		}
		return new ModelAndView("login", model);
	}

	@RequestMapping(value = "/register")
	public ModelAndView register(HttpSession session) {

		return new ModelAndView("register");
	}

	/**
	 * When a new user is been registered from the Registration page: 1. the
	 * user is inserted in DB 2. the user is authenticated through
	 * SpringSecurity 3. the acl tables are populated for the new user
	 * 
	 * @return
	 */
	@RequestMapping(value = "/registerNew")
	public ModelAndView registerNewUser(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session) {

		UserDto u = new UserDto();
		u.setUsername(email);
		u.setPassword(password);
		try {
			userService.createUser(u);
		} catch (Exception e) {
			logger.error("Error in registerNewUser", e);
		}

		// //after registering the user, create the role to login with spring
		// Collection<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		// roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		// // Place the new Authentication object in the security context.
		// UsernamePasswordAuthenticationToken usernameAndPassword =
		// new UsernamePasswordAuthenticationToken(email, password, roles);
		// SecurityContextHolder.getContext().setAuthentication(usernameAndPassword);

		saveLoggedUserSession(session);
		return new ModelAndView("home");
	}

	/**
	 * When a new user is been added by the admin the following occurs: 1. the
	 * user is added in DB 2. acl tables are populated for the new user
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute("command") UserDto user, BindingResult result) {
		try {
			logger.debug("Saving in DB user:" + user);
			userService.updateUser(user);
		} catch (Exception e) {
			logger.error("Error in saveUser", e);
		}
		return new ModelAndView("redirect:/add.html");
	}

	// @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addUser(@ModelAttribute("command") UserDto user, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<UserDto> list = userService.getAllUsers();
			model.put("users", list);
		} catch (Exception e) {
			logger.error("Error in addUser", e);
		}
		return new ModelAndView("addUser", model);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteEmployee(@ModelAttribute("command") UserDto user, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			logger.debug("Going to delete user:" + user);
			userService.delete(user);
			model.put("user", null);
			model.put("users", userService.getAllUsers());
		} catch (Exception e) {
			logger.error("Error in deleteEmployee", e);
		}
		return new ModelAndView("addUser", model);
	}

	@RequestMapping("/users")
	public ModelAndView listUsers() {
		ModelAndView mv = new ModelAndView("usersList");
		try {
			List<UserDto> list = userService.getAllUsers();
			mv.addObject("users", list);
		} catch (Exception e) {
			logger.error("Error in listUsers", e);
		}
		return mv;
	}

	public ModelAndView updateUser() {
		try {
			UserDto u = new UserDto();
			userService.updateUser(u);
		} catch (Exception e) {
			logger.error("Error in updateUser", e);
		}
		return new ModelAndView("home");
	}

	public ModelAndView deleteUser() {
		try {
			UserDto u = new UserDto();
			userService.delete(u);
		} catch (Exception e) {
			logger.error("Error in deleteUser", e);
		}
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public ModelAndView getDeniedPage() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("msg", "You don't have privileges to see this page. Please check another option!");
		return new ModelAndView("denied", model);
	}

	private void saveLoggedUserSession(HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			session.setAttribute("userName" , auth.getName());
			session.setAttribute("role" , auth.getAuthorities());
		} else {
			logger.error("NO SPRING SECURITY found!");
		}
	}

}
