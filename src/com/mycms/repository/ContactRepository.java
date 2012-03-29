package com.mycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycms.domain.Contact;

public interface ContactRepository extends JpaRepository<Contact,Integer> {

}

