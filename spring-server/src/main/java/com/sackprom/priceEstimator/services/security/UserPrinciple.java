package com.sackprom.priceEstimator.services.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sackprom.priceEstimator.model.User;

/*
 * UserPrinciple is not used directly by Spring Security for security purposes.
 * It simply stores user information which is later encapsulated into Authentication objects.
 * This allows non-security related user information 
 * (such as email addresses, telephone numbers etc) 
 * to be stored.
 */
public class UserPrinciple implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1300470222792091851L;
	private Long id;
	private String name;
	private String username;
	private String email;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	/**
	 * @param id
	 * @param name
	 * @param username
	 * @param email
	 * @param password
	 * @param authorities
	 */
	public UserPrinciple(
			Long id, 
			String name, String username, String email, 
			String password,
			Collection<? extends GrantedAuthority> authorities
	) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetails build(User user) {
		List<GrantedAuthority> authorities = user
				.getRoles().stream().map(
						role -> new SimpleGrantedAuthority(
								role.getName().name()
						)
				).collect(Collectors.toList());
		
		return new UserPrinciple(
				user.getId(), 
				user.getName(), 
				user.getUsername(), 
				user.getEmail(), 
				user.getPassword(), 
				authorities
		);
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }

}
