package com.sackprom.priceEstimator.rest;

import com.sackprom.priceEstimator.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
    private UserDetailsServiceImpl userDetailsService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/user-principle/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?> getUser(@PathVariable("username") String username) {
		LOGGER.info(username);
		return ResponseEntity.ok(userDetailsService.loadUserByUsername(username));
	}
}
