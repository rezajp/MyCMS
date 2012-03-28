package com.mycms.security;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;
import com.mycms.domain.SiteUser;
import com.mycms.repository.SiteUserRepository;

@Service("customOpenIdUserDetailsService")
public class CustomOpenIdUserDetailsServiceImpl implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
	 
	   //private final Map<String, CustomUserDetails> registeredUsers = new HashMap<String, CustomUserDetails>();
	 
	   private static final List<GrantedAuthority> DEFAULT_AUTHORITIES = AuthorityUtils.createAuthorityList("ROLE_USER");
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
	   /**
	     * Implementation of {@code UserDetailsService}. We only need this to satisfy the {@code RememberMeServices}
	     * requirements.
	     */
	   public UserDetails loadUserByUsername(String userName)
				throws UsernameNotFoundException, DataAccessException {

			SiteUser userEntity = siteUserRepository.readByUsername(userName);
			if (userEntity == null)
				throw new UsernameNotFoundException("user not found");
			return userAssembler.buildUserFromUserEntity(userEntity);

		}
	 
	   /**
	     * Implementation of {@code AuthenticationUserDetailsService} which allows full access to the submitted
	     * {@code Authentication} object. Used by the OpenIDAuthenticationProvider.
	     */
	   public UserDetails loadUserDetails(OpenIDAuthenticationToken token) {
	       String id = token.getIdentityUrl();
	 
	       SiteUser userEntity = siteUserRepository.readByUsername(token.getIdentityUrl());
			if (userEntity != null){
	           return userAssembler.buildUserFromUserEntity(userEntity);
	       }
	 
	       String email = null;
	       String firstName = null;
	       String lastName = null;
	       String fullName = null;
	 
	       List<OpenIDAttribute> attributes = token.getAttributes();
	 
	       for (OpenIDAttribute attribute : attributes) {
	           if (attribute.getName().equals("email")) {
	               email = attribute.getValues().get(0);
	           }
	 
	           if (attribute.getName().equals("firstname")) {
	               firstName = attribute.getValues().get(0);
	           }
	 
	           if (attribute.getName().equals("lastname")) {
	               lastName = attribute.getValues().get(0);
	           }
	 
	           if (attribute.getName().equals("fullname")) {
	               fullName = attribute.getValues().get(0);
	           }
	       }
	 
	       if (fullName == null) {
	           StringBuilder fullNameBldr = new StringBuilder();
	 
	           if (firstName != null) {
	               fullNameBldr.append(firstName);
	           }
	 
	           if (lastName != null) {
	               fullNameBldr.append(" ").append(lastName);
	           }
	           fullName = fullNameBldr.toString();
	       }
	 
	       userEntity = new SiteUser();
	       userEntity.setUsername(id);
	       userEntity.setEmail(email);
	       userEntity.setName(fullName);
	       userEntity.setPassword(".");
	       
	       siteUserRepository.save(userEntity);
	 
	      
	 
	       return userAssembler.buildUserFromUserEntity(userEntity);
	   }
}