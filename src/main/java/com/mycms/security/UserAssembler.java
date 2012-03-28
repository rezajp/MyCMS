package com.mycms.security;

import org.springframework.security.core.userdetails.User;


public interface UserAssembler {
	public User buildUserFromUserEntity(com.mycms.domain.SiteUser userEntity) ;
}