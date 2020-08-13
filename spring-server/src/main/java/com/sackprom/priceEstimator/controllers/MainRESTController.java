package com.sackprom.priceEstimator.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sackprom.priceEstimator.model.User;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MainRESTController {

	@RequestMapping(
			value = "/", 
			method = RequestMethod.GET
	)
	@ResponseBody
	public String home() {
		return "/";
	}
	
	@RequestMapping(
			value = "/test", 
			method = RequestMethod.GET
	)
	@ResponseBody
	public String test() {
		return "/test";
	}
	
	@RequestMapping(
			value = "/login", 
			method = RequestMethod.GET
	)
	@ResponseBody
	public String login() {
		return "/login";
	}
	
	@RequestMapping(
			value = "/showList", 
			method = RequestMethod.GET, 
			produces = "application/json"
	)
	@ResponseBody
	public static List<User> showList() {
		List<User> tempUsers = new ArrayList<>();
		User user1 = new User();
		user1.setName("name user1");
		user1.setUsername("username user1");
		user1.setEmail("email1");
		User user2 = new User();
		user2.setName("name user2");
		user2.setUsername("username user2");
		user2.setEmail("email2");
		tempUsers.add(user1);
		tempUsers.add(user2);
		return tempUsers;
	}
}
