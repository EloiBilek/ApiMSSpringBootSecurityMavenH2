/**
 * 
 */
package com.apimsspringbootsecuritymavenh2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apimsspringbootsecuritymavenh2.config.security.AccountCredentials;
import com.apimsspringbootsecuritymavenh2.model.entity.User;
import com.apimsspringbootsecuritymavenh2.model.repository.IUserRepository;
import com.apimsspringbootsecuritymavenh2.service.IUserService;

/**
 * Classe de Servico, implementando as chamadas para a camada de Persistencia.
 * 
 * @author eloi
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService implements IUserService {

	private IUserRepository userRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserService(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public Optional<User> findById(final long id) {
		Optional<User> user = userRepository.findById(id);
		return user;
	}

	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		users = userRepository.findAll();
		return users;
	}

	@Override
	public User create(final User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User update(User user) {
		Optional<User> userDB = this.findById(user.getId());
		if (userDB.isPresent()) {
			user.setPassword(userDB.get().getPassword());
			user = userRepository.saveAndFlush(user);
		} else {
			user = null;
		}
		return user;
	}

	@Override
	public void deleteById(final long id) {
		userRepository.deleteById(id);
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	public String updatePassword(AccountCredentials accountCredentials) {
		User userDb = userRepository.findByEmail(accountCredentials.getEmail());
		if (userDb != null) {
			if (bCryptPasswordEncoder.matches(accountCredentials.getPassword(), userDb.getPassword())) {
				userDb.setPassword(bCryptPasswordEncoder.encode(accountCredentials.getNewPassword()));
				this.update(userDb);
				return "Senha alterada com sucesso!";
			}
		}
		return "Falha ao atualizar senha, veja o log do servidor!";
	}

}
