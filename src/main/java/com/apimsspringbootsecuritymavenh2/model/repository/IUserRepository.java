/**
 * 
 */
package com.apimsspringbootsecuritymavenh2.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apimsspringbootsecuritymavenh2.model.entity.User;

/**
 * Interface padrao do Spring.
 * 
 * @author eloi
 *
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

}
