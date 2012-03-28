package com.mycms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mycms.domain.SiteSettings;

public interface SiteSettingsRepository extends MongoRepository<SiteSettings, String> {

	public List<SiteSettings> findByActive(boolean active);
}
