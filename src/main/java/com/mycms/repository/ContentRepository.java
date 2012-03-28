package com.mycms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mycms.domain.Content;


public interface ContentRepository extends MongoRepository<Content,String> {

}
