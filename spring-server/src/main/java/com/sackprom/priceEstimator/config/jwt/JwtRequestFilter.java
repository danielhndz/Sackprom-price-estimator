package com.sackprom.priceEstimator.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sackprom.priceEstimator.services.security.JwtUserDetailsService;

/*
 * Executes once per request.
 * This is a filter base class that is used to guarantee a single execution per request dispatch.
 */
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
    private JwtTokenUtil tokenProvider;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);
    
    /*
     * Method will do:
     * 
     * 1. Get JWT token from header.
     * 2. Validate JWT.
     * 3. Parse username from validated JWT.
     * 4. Load data from users table.
     * 5. Set the authentication object to Security Context. 
     */
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain
	) throws ServletException, IOException {
		
		try {
			
			String jwt = getJwt(request); // (1)
			if (jwt!=null && tokenProvider.validateJwtToken(jwt)) { // (2)
				String username = tokenProvider.getUsernameFromJwtToken(jwt); // (3)
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username); // (4)
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(
								userDetails, 
								null, 
								userDetails.getAuthorities()
						);
				authentication.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
				);
				
				SecurityContextHolder.getContext().setAuthentication(authentication); // (5)
			}
		} catch (Exception e) {
			LOGGER.error("Can NOT set user authentication -> Message: {}", e);
		}
		
		filterChain.doFilter(request, response);
	}

	/*
	 * Get JWT token from header request.
	 */
	private String getJwt(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
	          return authHeader.replace("Bearer ","");
		}
		
		return null;
	}
	
}
