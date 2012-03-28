package com.mycms.security;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.mycms.domain.Role;

@Service
public class UserAssemblerImpl implements UserAssembler {


	public User buildUserFromUserEntity(
			com.mycms.domain.SiteUser userEntity) {

		String username = userEntity.getUsername();
		String password = userEntity.getPassword();
		boolean enabled =true;// userEntity.isActive();
		boolean accountNonExpired =true;// userEntity.isActive();
		boolean credentialsNonExpired =true;// userEntity.isActive();
		boolean accountNonLocked =true;// userEntity.isActive();

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : userEntity.getRoles()) {
			authorities.addAll( AuthorityUtils.createAuthorityList(role.getName()));
		}
		return new User(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}
}
