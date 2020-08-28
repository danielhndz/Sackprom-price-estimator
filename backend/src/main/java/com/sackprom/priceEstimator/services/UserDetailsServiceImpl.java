package com.sackprom.priceEstimator.services;

import com.sackprom.priceEstimator.dao.UserDAO;
import com.sackprom.priceEstimator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    UserDAO userRepo;
	
	/*
	 * Will find a record from users database tables 
	 * to build a UserDetails object for authentication.
	 */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo
				.findByUsername(username)
        		.orElseThrow(
        				() -> new UsernameNotFoundException(
        						"User Not Found with -> username or email : "
        						+ username
        		)
        );
        return UserDetailsImpl.build(user);
    }
}
