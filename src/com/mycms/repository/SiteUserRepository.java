package com.mycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycms.domain.SiteUser;

public interface SiteUserRepository extends JpaRepository<SiteUser, Integer> {

	SiteUser readByUsername(String username);

}
