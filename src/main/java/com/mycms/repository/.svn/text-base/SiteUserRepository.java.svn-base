package com.mycms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mycms.domain.SiteUser;

public interface SiteUserRepository extends MongoRepository<SiteUser, String> {

	SiteUser readByUsername(String username);

}
