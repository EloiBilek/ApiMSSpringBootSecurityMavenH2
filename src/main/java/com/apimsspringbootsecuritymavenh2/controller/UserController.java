/**
 * 
 */
package com.apimsspringbootsecuritymavenh2.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apimsspringbootsecuritymavenh2.config.security.AccountCredentials;
import com.apimsspringbootsecuritymavenh2.model.entity.User;
import com.apimsspringbootsecuritymavenh2.service.IUserService;

/**
 * Classe Controller, com as definicoes de acesso ao recurso Usuario via Rest.
 * 
 * @author eloi
 *
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

	private IUserService userService;

	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> findAll() {
		return userService.findAll();
	}

	@GetMapping(path = { "/{id}" })
	public User findById(@PathVariable final Long id) {
		return userService.findById(id).get();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@RequestBody final User resource, final HttpServletResponse response) {
		return userService.create(resource);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public User update(@RequestBody final User resource, final HttpServletResponse response) {
		return userService.update(resource);
	}

	@DeleteMapping(path = { "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable final Long id, final HttpServletResponse response) {
		userService.deleteById(id);
	}

	// Teste via browser...
	@GetMapping(path = { "/status" })
	public String status() {
		return "ApiMSSpringBootSecurityMavenH2 on Tomcat and H2 - OK";
	}

	// Nova senha.
	@PostMapping(path = { "/newpassword" })
	@ResponseStatus(HttpStatus.OK)
	public String updatePassword(@RequestBody final AccountCredentials resource, final HttpServletResponse response) {
		return userService.updatePassword(resource);
	}

}
