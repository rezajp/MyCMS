package com.mycms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycms.domain.SiteSettings;

public interface SiteSettingsRepository extends JpaRepository<SiteSettings, Integer> {

	public List<SiteSettings> findByActive(boolean active);
}
