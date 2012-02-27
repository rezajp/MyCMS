package com.mycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycms.domain.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
