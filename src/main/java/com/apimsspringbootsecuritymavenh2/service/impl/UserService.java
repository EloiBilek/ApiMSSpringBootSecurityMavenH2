/**
 * 
 */
package com.apimsspringbootsecuritymavenh2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Autowired
	public UserService(IUserRepository userRepository) {
		super();
		this.userRepository = userRepository;
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
		return userRepository.save(user);
	}

	@Override
	public User update(User user) {
		if (userRepository.existsById(user.getId())) {
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

}
