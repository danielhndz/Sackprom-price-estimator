package com.sackprom.priceEstimator.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sackprom.priceEstimator.services.security.JwtUserDetailsService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/user")
public class User {

	@Autowired
    private JwtUserDetailsService userDetailsService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
	
	@GetMapping("/user-principle/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?> getUser(@PathVariable("username") String username) {
		LOGGER.info(username);
		return ResponseEntity.ok(userDetailsService.loadUserByUsername(username));
	}
}
