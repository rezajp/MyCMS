package com.mycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycms.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
