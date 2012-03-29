package com.mycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycms.domain.SitePage;

public interface SitePageRepository extends JpaRepository<SitePage, Integer> {

	SitePage readByValidKey(String pageKey);

}
