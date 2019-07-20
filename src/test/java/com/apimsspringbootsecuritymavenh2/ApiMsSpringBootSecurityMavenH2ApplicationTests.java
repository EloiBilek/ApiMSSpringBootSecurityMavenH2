package com.apimsspringbootsecuritymavenh2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.apimsspringbootsecuritymavenh2.config.security.AccountCredentials;
import com.apimsspringbootsecuritymavenh2.enums.RoleName;
import com.apimsspringbootsecuritymavenh2.model.entity.Role;
import com.apimsspringbootsecuritymavenh2.model.entity.User;

//@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
		user.setName("Teste JUnit 2");
		user.setEmail("teste@test.com.br");
		user.setActive(true);
		user.setPassword("xpto1234");

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

		// Execute call
		TestRestTemplate rest = new TestRestTemplate();

		rest.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
			request.getHeaders().add("Authorization", token);
			return execution.execute(request, body);
		}));

		@SuppressWarnings("unchecked")
		ResponseEntity<User> response = rest.getForEntity(URL_TEST, String.class, Collections.EMPTY_MAP);

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
		// Execute call
		TestRestTemplate rest = new TestRestTemplate();

		rest.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
			request.getHeaders().add("Authorization", token);
			return execution.execute(request, body);
		}));

		@SuppressWarnings("unchecked")
		ResponseEntity<User> response = rest.getForEntity(URL_TEST + "/" + idUser, User.class, Collections.EMPTY_MAP);

		// User to update
		User userUpdated = response.getBody();
		userUpdated.setActive(false);
		userUpdated.setName("TEste Update 1");

		try {
			rest.put(URL_TEST, userUpdated);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// Test of JUnit...
		assertEquals(HttpStatus.OK, response.getStatusCode().OK);

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

		assertTrue(true);

	}

}
