package com.sackprom.priceEstimator.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sackprom.priceEstimator.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findAll();
}
