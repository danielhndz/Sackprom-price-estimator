package com.sackprom.priceEstimator.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sackprom.priceEstimator.dao.UserDAO;
import com.sackprom.priceEstimator.model.User;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
    UserDAO userRepository;
	
	/*
	 * Will find a record from users database tables 
	 * to build a UserDetails object for authentication.
	 */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) 
    		throws UsernameNotFoundException {
      
        User user = userRepository
        		.findByUsername(username)
        		.orElseThrow(
        				() -> new UsernameNotFoundException(
        						"User Not Found with -> username or email : "
        						+ username
        		)
        );
 
        return UserPrinciple.build(user);
    }
}
