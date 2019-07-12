package com.apimsspringbootsecuritymavenh2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.apimsspringbootsecuritymavenh2.config.security.AccountCredentials;
import com.apimsspringbootsecuritymavenh2.enums.RoleName;
import com.apimsspringbootsecuritymavenh2.model.entity.Role;
import com.apimsspringbootsecuritymavenh2.model.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiMsSpringBootSecurityMavenH2ApplicationTests {

	private static String URL_TEST = "http://localhost:8080/ApiMSSpringBootSecurityMavenH2/v1/users";

	private static String URL_LOGIN = "http://localhost:8080/ApiMSSpringBootSecurityMavenH2/login";

	private static long idUser;

	private static String token;

	@SuppressWarnings("static-access")
	@Test
	public void a_postTest() {
		// User to save
		User user = new User();
		user.setName("Teste JUnit");
		user.setEmail("teste@test.com");
		user.setActive(true);
		user.setPassword("xpto123");

		Role role = new Role();
		role.setId(1l);
		role.setName(RoleName.ADMIN);

		user.getRoles().add(role);

		// Execute call
		TestRestTemplate rest = new TestRestTemplate();
		@SuppressWarnings("unchecked")
		ResponseEntity<User> response = rest.postForEntity(URL_TEST, user, User.class, Collections.EMPTY_MAP);

		// User created and returned in response
		User userCreated = response.getBody();

		// Test of JUnit...
		assertEquals(HttpStatus.CREATED, response.getStatusCode().CREATED);
		assertEquals(user.getName(), userCreated.getName());
		assertTrue(userCreated.getId() > 0);

		// Set global atribute
		idUser = userCreated.getId();

		// Dataset print in console
		System.out.println(userCreated.toString());
	}

	@Test
	public void b_loginTest() {
		AccountCredentials accCred = new AccountCredentials();
		accCred.setEmail("teste@test.com");
		accCred.setPassword("xpto123");

		// Execute call
		TestRestTemplate rest = new TestRestTemplate();
		@SuppressWarnings("unchecked")
		ResponseEntity<String> response = rest.postForEntity(URL_LOGIN, accCred, AccountCredentials.class,
				Collections.EMPTY_MAP);

		token = response.getHeaders().get("Authorization").get(0);

		assertNotNull(token);

		// Dataset print in console
		System.out.println(response.getBody());
	}

	@SuppressWarnings("static-access")
	@Test
	public void c_getTest() {
		List<User> users = new ArrayList<User>();
		ObjectMapper mapper = new ObjectMapper();

		// Execute call
		TestRestTemplate rest = new TestRestTemplate();

		rest.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
			request.getHeaders().add("Authorization", token);
			return execution.execute(request, body);
		}));

		@SuppressWarnings("unchecked")
		ResponseEntity<String> response = rest.getForEntity(URL_TEST, String.class, Collections.EMPTY_MAP);

		// Parse string to list object
		try {
			users = mapper.readValue(response.getBody().toString(), new TypeReference<List<User>>() {
			});
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// Test of JUnit...
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode().ACCEPTED);

		// Dataset print in console
		if (users.size() > 0 && !users.isEmpty()) {
			for (User user : users) {
				System.out.println(user.toString());
			}
		}
	}

	@SuppressWarnings("static-access")
	@Test
	public void d_putTest() {
		// User to update
		User user = new User();
		user.setId(idUser);
		user.setName("Teste JUnit Update");
		user.setEmail("teste@test.com");
		user.setActive(true);

		// Execute call
		TestRestTemplate rest = new TestRestTemplate();

		rest.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
			request.getHeaders().add("Authorization", token);
			return execution.execute(request, body);
		}));

		try {
			rest.put(URL_TEST, user);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// RestTemplate put is void...
		@SuppressWarnings("unchecked")
		ResponseEntity<User> response = rest.getForEntity(URL_TEST + "/" + idUser, User.class, Collections.EMPTY_MAP);

		// User created and returned in response
		User userUpdated = response.getBody();

		// Test of JUnit...
		assertEquals(HttpStatus.OK, response.getStatusCode().OK);
		assertEquals(user.getName(), userUpdated.getName());
		assertTrue(userUpdated.getId() > 0);

		// Dataset print in console
		System.out.println(userUpdated.toString());
	}

	@Test
	public void e_deleteTest() {
		// Execute call
		TestRestTemplate rest = new TestRestTemplate();

		rest.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
			request.getHeaders().add("Authorization", token);
			return execution.execute(request, body);
		}));

		try {
			rest.delete(URL_TEST + "/" + idUser);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// RestTemplate delete is void...
		@SuppressWarnings("unchecked")
		ResponseEntity<User> response = rest.getForEntity(URL_TEST + "/" + idUser, User.class, Collections.EMPTY_MAP);

		// User created and returned in response
		User userFind = response.getBody();

		// Test of JUnit...
		assertEquals(null, userFind);

		// Dataset print in console
		System.out.println(userFind);
	}

}
