package fr.wijin.spring.jpa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import fr.wijin.spring.jpa.config.AppConfig;
import fr.wijin.spring.jpa.model.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext
class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@Test
	void testGetAllUsers() {
		List<User> users = userService.getAllUsers();
		assertEquals(1, users.size());
	}

	@Test
	void testGetUserById() {
		User user = userService.getUserById(Integer.valueOf(1));
		assertNotNull(user);
	}

	@Test
	void testCreate() {
		User user = new User();
		user.setMail("monmail@test.fr");
		User createdUser = userService.createUser(user);
		assertNotNull(createdUser);
		List<User> users = userService.getAllUsers();
		System.out.println("Nombre de users : " + users.size());
	}

	@Test
	void testUpdate() {
		User user = new User();
		user.setId(Integer.valueOf(1));
		user.setMail("mailmodifie@test.fr");
		try {
			userService.updateUser(user);
		} catch (Exception e) {
			fail();
		}

		User modifiedUser = userService.getUserById(1);
		assertEquals("mailmodifie@test.fr", modifiedUser.getMail());
		List<User> users = userService.getAllUsers();
		System.out.println("Nombre de users : " + users.size());
	}

	@Test
	void testDelete() {
		userService.deleteUser(1);
		User deletedUser = userService.getUserById(2);
		assertNull(deletedUser);
		List<User> users = userService.getAllUsers();
		System.out.println("Nombre de users : " + users.size());
	}

}
