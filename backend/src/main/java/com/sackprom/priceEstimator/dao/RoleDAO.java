package com.sackprom.priceEstimator.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sackprom.priceEstimator.model.Role;
import com.sackprom.priceEstimator.model.RoleName;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleName roleName);
}
