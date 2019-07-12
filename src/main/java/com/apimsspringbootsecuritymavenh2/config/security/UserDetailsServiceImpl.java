/**
 * 
 */
package com.apimsspringbootsecuritymavenh2.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apimsspringbootsecuritymavenh2.model.repository.IUserRepository;

/**
 * Classe que gera o UserDetails para autenticacao do login.
 * 
 * @author eloi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserRepository iUserRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.apimsspringbootsecuritymavenh2.model.entity.User applicationUser = iUserRepository.findByEmail(email);
		if (applicationUser == null) {
			throw new UsernameNotFoundException(email);
		}

		return new User(applicationUser.getName(), applicationUser.getPassword(), applicationUser.getRoles());
	}
}
