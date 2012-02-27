package com.mycms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mycms.domain.SiteUser;
import com.mycms.repository.SiteUserRepository;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	SiteUserRepository siteUserRepository;
	@Autowired
	UserAssembler userAssembler;
	AuthenticationService authenticationService;
	public AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	@Autowired
	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}





	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {

		SiteUser userEntity = siteUserRepository.readByUsername(userName);
		if (userEntity == null)
			throw new UsernameNotFoundException("user not found");
		return userAssembler.buildUserFromUserEntity(userEntity);

	}

}
