package com.sackprom.priceEstimator.rest;

import com.sackprom.priceEstimator.dao.RoleDAO;
import com.sackprom.priceEstimator.dao.UserDAO;
import com.sackprom.priceEstimator.jwt.JwtTokenUtil;
import com.sackprom.priceEstimator.message.request.SigninForm;
import com.sackprom.priceEstimator.message.request.SignupForm;
import com.sackprom.priceEstimator.message.response.JwtResponse;
import com.sackprom.priceEstimator.model.Role;
import com.sackprom.priceEstimator.model.RoleName;
import com.sackprom.priceEstimator.model.User;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

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
public class AuthController {

	@Autowired
    AuthenticationManager authManager;
	
	@Autowired
    UserDAO userRepo;
 
    @Autowired
    RoleDAO roleRepo;
 
    @Autowired
    PasswordEncoder encoder;
 
    @Autowired
    JwtTokenUtil jwtProvider;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/sign-up")
	public ResponseEntity<String> signUp(@Valid @RequestBody SignupForm signUpRequest) {
		LOGGER.info( "---------- Registrando usuario ----------" );

		if(userRepo.existsByUsername(signUpRequest.getUsername())) { // (1)
			return new ResponseEntity<String>(
					"Fail -> Username is already taken!",
					HttpStatus.BAD_REQUEST
			);
		}

		if(userRepo.existsByEmail(signUpRequest.getEmail())) { // (1)
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

		LOGGER.info( "---------- Asignando roles ----------\nRoles:" );
		LOGGER.info( signUpRequest.getRoles().toString() );

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		LOGGER.warn( "---------- Pendiente switch de roles ----------" );

		strRoles.forEach(
				role -> {
					switch(role) {
						case "admin":
							Role adminRole = roleRepo
									.findByName(RoleName.ROLE_ADMIN)
									.orElseThrow(
											() -> new RuntimeException("Fail! -> Cause: User Role not find.")
									);
							roles.add(adminRole);
							break;

						case "pm":
							Role pmRole = roleRepo
									.findByName(RoleName.ROLE_PM)
									.orElseThrow(
											() -> new RuntimeException("Fail! -> Cause: User Role not find.")
									);
							roles.add(pmRole);
							break;

						default:
							Role userRole = roleRepo
									.findByName(RoleName.ROLE_USER)
									.orElseThrow(
											() -> new RuntimeException("Fail! -> Cause: User Role not find.")
									);
							roles.add(userRole);

					}
				}
		);

		user.setRoles(roles);
		userRepo.save(user); // (3)

		return ResponseEntity.ok().body("User registered successfully!");
	}
    
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SigninForm signInRequest) {
		LOGGER.info( "----------  Iniciando sesión ----------" );
    	LOGGER.info( "Username: " + signInRequest.getUsername());
 
        Authentication auth = authManager.authenticate(
        		new UsernamePasswordAuthenticationToken(
        				signInRequest.getUsername(),
						signInRequest.getPassword()
				)
		); // (1)
 
        SecurityContextHolder.getContext().setAuthentication(auth); // (2)
 
        String jwt = jwtProvider.generateJwtToken(auth); // (3)
        return ResponseEntity.ok(new JwtResponse(jwt)); // (4)
    }
    
}
