package com.mycms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mycms.domain.SitePage;

public interface SitePageRepository extends MongoRepository<SitePage, String> {

	SitePage readByValidKey(String pageKey);

}
