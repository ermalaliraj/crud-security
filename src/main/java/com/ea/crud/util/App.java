package com.ea.crud.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ea.crud.service.UserService;

public class App {
	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "classpath:spring-context.xml",
		        		"classpath:persistence-context.xml"
		        		}
    		);

		UserService userService = (UserService) appContext.getBean("userService");

		try {
			userService.getByUsername("fsfs");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("before closing main");
	}
}