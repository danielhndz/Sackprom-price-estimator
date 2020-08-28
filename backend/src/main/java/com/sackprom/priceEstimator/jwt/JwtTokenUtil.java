package com.sackprom.priceEstimator.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sackprom.priceEstimator.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/*
 * JwtTokenUtil is an util class -> it implements useful functions:
 * 
 * - Generate a JWT token
 * - Validate a JWT token
 * - Parse username from JWT token
 */
@Component
public class JwtTokenUtil {
	
	@Value("${sackprom.app.jwtKey}")
    private String jwtKey;
	
    @Value("${sackprom.app.jwtExpiration}")
    private int jwtExpiration;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
        		.setSubject((user.getUsername()))
        		.setIssuedAt(new Date())
        		.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
        		.signWith(SignatureAlgorithm.HS512, jwtKey)
        		.compact();
    }
    
	public boolean validateJwtToken(String jwt) {
		try {
            Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(jwt);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty -> Message: {}", e);
        }
        return false;
	}

	public String getUsernameFromJwtToken(String jwt) {
		return Jwts.parser()
				.setSigningKey(jwtKey)
				.parseClaimsJws(jwt)
				.getBody().getSubject();
	}

}
