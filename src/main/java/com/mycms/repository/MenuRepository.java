package com.mycms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mycms.domain.Menu;

public interface MenuRepository extends MongoRepository<Menu, String> {

}
