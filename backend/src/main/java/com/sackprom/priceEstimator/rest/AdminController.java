package com.sackprom.priceEstimator.rest;

import com.sackprom.priceEstimator.dao.UserDAO;
import com.sackprom.priceEstimator.model.User;
import com.sackprom.priceEstimator.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	UserDAO userRepo;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String home() {
		return "/";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String test() {
		return "/test";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public String login() {
		return "/login";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<UserDetails> users() {
		List<UserDetails> userList = new ArrayList<>();
		UserDetails userPrinciple = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		for(User user : userRepo.findAll()) {
			if (!user.getUsername().equals(userPrinciple.getUsername())) {
				userList.add(userDetailsService.loadUserByUsername(user.getUsername()));
			}
		}
		return userList;
	}
}
