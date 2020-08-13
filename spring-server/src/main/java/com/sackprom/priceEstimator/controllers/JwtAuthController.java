package com.sackprom.priceEstimator.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sackprom.priceEstimator.config.jwt.JwtTokenUtil;
import com.sackprom.priceEstimator.dao.RoleDAO;
import com.sackprom.priceEstimator.dao.UserDAO;
import com.sackprom.priceEstimator.message.request.SigninForm;
import com.sackprom.priceEstimator.message.request.SignupForm;
import com.sackprom.priceEstimator.message.response.JwtResponse;
import com.sackprom.priceEstimator.model.Role;
import com.sackprom.priceEstimator.model.RoleName;
import com.sackprom.priceEstimator.model.User;

/*
 * Defines 2 APIs:
 * 
 *  - /api/auth/signup: sign up
 *    1. Check username/email is already in use.
 *    2. Create User object.
 *    3. Store to database.
 *    
 *  - /api/auth/signin: sign in
 *    1. Attempt to authenticate with AuthenticationManager bean.
 *    2. Add authentication object to SecurityContextHolder.
 *    3. Generate JWT token.
 *    4. Return JWT to client.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class JwtAuthController {

	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
    UserDAO userRepository;
 
    @Autowired
    RoleDAO roleRepository;
 
    @Autowired
    PasswordEncoder encoder;
 
    @Autowired
    JwtTokenUtil jwtProvider;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthController.class);
    
    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninForm signinRequest) {
    	
    	LOGGER.info(signinRequest.getUsername());
 
        Authentication authentication = authenticationManager
        		.authenticate(
        				new UsernamePasswordAuthenticationToken(
        						signinRequest.getUsername(), 
        						signinRequest.getPassword()
        				)
        		); // (1)
 
        SecurityContextHolder.getContext().setAuthentication(authentication); // (2)
 
        String jwt = jwtProvider.generateJwtToken(authentication); // (3)
        return ResponseEntity.ok(new JwtResponse(jwt)); // (4)
    }
    
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(
    		@Valid 
    		@RequestBody 
    		SignupForm signUpRequest
    ) {
    	
        if(userRepository.existsByUsername(signUpRequest.getUsername())) { // (1)
            return new ResponseEntity<String>(
            		"Fail -> Username is already taken!", 
            		HttpStatus.BAD_REQUEST
            );
        }
 
        if(userRepository.existsByEmail(signUpRequest.getEmail())) { // (1)
            return new ResponseEntity<String>(
            		"Fail -> Email is already in use!", 
                    HttpStatus.BAD_REQUEST
            );
        }
 
        // Creating user's account
        User user = new User(
        		signUpRequest.getName(), 
        		signUpRequest.getUsername(), 
                signUpRequest.getEmail(), 
                encoder.encode(signUpRequest.getPassword())
        ); // (2)

        System.out.println("----------- 0 -----------");
        System.out.println(signUpRequest.getName());
        System.out.println(signUpRequest.getRoles());
        System.out.println("----------- 1 -----------");
        
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
 
        strRoles.forEach(
        		role -> {
        			switch(role) {
        			
        			case "admin": 
        				Role adminRole = roleRepository
        				.findByName(RoleName.ROLE_ADMIN)
        				.orElseThrow(
        						() -> new RuntimeException("Fail! -> Cause: User Role not find.")
        				);
        				roles.add(adminRole);
        			break;
        			
        			case "pm": 
        				Role pmRole = roleRepository
        				.findByName(RoleName.ROLE_PM)
        				.orElseThrow(
        						() -> new RuntimeException("Fail! -> Cause: User Role not find.")
        				);
        				roles.add(pmRole);
        			break;
        			
        			default: 
        				Role userRole = roleRepository
        				.findByName(RoleName.ROLE_USER)
        				.orElseThrow(
        						() -> new RuntimeException("Fail! -> Cause: User Role not find.")
        				);
        				roles.add(userRole);
        			
        			}
        		}
        );
        
        user.setRoles(roles);
        userRepository.save(user); // (3)
 
        return ResponseEntity.ok().body("User registered successfully!");
    }
    
}
