package fr.wijin.spring.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import fr.wijin.spring.jpa.config.AppConfig;
import fr.wijin.spring.jpa.model.User;
import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
@TestMethodOrder(OrderAnnotation.class)
class UserRepositoryImplTest {

	Logger logger = LoggerFactory.getLogger(UserRepositoryImplTest.class);

	@Autowired
	private UserRepository userRepository;

	@Test
	@Order(1)
	void givenUsernameAndPassword_whenCallingTypedQueryMethod_thenReturnUser() {
		User user = userRepository.findByUsernameAndPassword("toto", "1234");
		Assertions.assertNotNull(user, "No user found for username and password");
	}

	@Test
	@Order(2)
	void givenId_whenCallingTypedQueryMethod_thenReturnUser() {
		Optional<User> user = userRepository.findById(1);
		Assertions.assertTrue(user.isPresent(), "No user found for id 1");
	}

	@Test
	@Order(3)
	void whenCallingTypedQueryMethod_thenReturnListOfUsers() {
		List<User> users = userRepository.findAll();
		Assertions.assertEquals(1, users.size(), "Wrong number of users");
	}

	@Test
	@Order(4)
	void givenUser_whenCallingTypedQueryMethodForCreate_thenReturnOK() {
		User newUser = new User();
		newUser.setUsername("mtest");
		newUser.setPassword("mtest");
		newUser.setMail("mtest@test.fr");

		List<User> users = userRepository.findAll();
		int numberOfUsersBeforeCreation = users.size();

		userRepository.save(newUser);

		List<User> usersAfterCreation = userRepository.findAll();
		int numberOfUsersAfterCreation = usersAfterCreation.size();
		Assertions.assertEquals(numberOfUsersBeforeCreation + 1, numberOfUsersAfterCreation);
	}

	@Test
	@Order(5)
	void givenUser_whenCallingTypedQueryMethodForUpdate_thenReturnOK() {

		Optional<User> user = userRepository.findById(1);
		user.get().setMail("nouveauMail@test.fr");

		userRepository.save(user.get());

		Optional<User> updatedUser = userRepository.findById(1);
		Assertions.assertEquals("nouveauMail@test.fr", updatedUser.get().getMail());
	}

	@Test
	@Order(6)
	void givenUser_whenCallingTypedQueryMethodForDelete_thenReturnOK() {
		Optional<User> user = userRepository.findById(1);

		userRepository.delete(user.get());

		Optional<User> deletedUser = userRepository.findById(1);
		Assertions.assertTrue(deletedUser.isEmpty(), "Deleted order must be null");
	}

}
