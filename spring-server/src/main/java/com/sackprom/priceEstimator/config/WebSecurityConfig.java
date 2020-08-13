package com.sackprom.priceEstimator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sackprom.priceEstimator.config.jwt.JwtAuthEntryPoint;
import com.sackprom.priceEstimator.config.jwt.JwtRequestFilter;
import com.sackprom.priceEstimator.services.security.JwtUserDetailsService;
 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true	
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    JwtUserDetailsService userDetailsService;
 
    @Autowired
    private JwtAuthEntryPoint authEntryPoint;
 
    @Bean
    public JwtRequestFilter requestFilter() {
        return new JwtRequestFilter();
    }
 
    @Override
    public void configure(
    		AuthenticationManagerBuilder authenticationManagerBuilder
    ) throws Exception {
    	
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
 
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.cors().and().csrf().disable().
        	authorizeRequests()
        	
                .antMatchers(
                		"/api/auth/**", 
                		"/", 
                		"/showList"
                ).permitAll()
                
                .anyRequest().authenticated()
                
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                
                /*
                 * Make sure we use stateless session.
                 * Session won't be used to store user's state.
                 */
                .and()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	        
    	// Add a filter to validate the tokens with every request.
        http.addFilterBefore(
        		requestFilter(), 
        		UsernamePasswordAuthenticationFilter.class
        );
    }
}
