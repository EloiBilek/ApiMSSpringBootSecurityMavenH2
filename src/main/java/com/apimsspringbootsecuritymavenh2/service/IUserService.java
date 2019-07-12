/**
 * 
 */
package com.apimsspringbootsecuritymavenh2.service;

import java.util.List;
import java.util.Optional;

import com.apimsspringbootsecuritymavenh2.common.IOperations;
import com.apimsspringbootsecuritymavenh2.config.security.AccountCredentials;
import com.apimsspringbootsecuritymavenh2.model.entity.User;

/**
 * Interface para implementacao da classe de servico do usuario.
 * 
 * @author eloi
 *
 */
public interface IUserService extends IOperations<User> {

	public Optional<User> findById(final long id);

	public List<User> findAll();

	public User create(final User user);

	public User update(final User user);

	public void deleteById(final long entityId);

	public String updatePassword(AccountCredentials ccountCredentials);

}
