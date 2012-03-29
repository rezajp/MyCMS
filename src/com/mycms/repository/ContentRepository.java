package com.mycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mycms.domain.Content;


public interface ContentRepository extends JpaRepository<Content,Integer> {

}
