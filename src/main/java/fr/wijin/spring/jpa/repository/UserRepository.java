package fr.wijin.spring.jpa.repository;


import java.util.List;
import java.util.Optional;

import fr.wijin.spring.jpa.model.User;

// TODO
public interface UserRepository {

	/**
	 * Get a user by username
	 * 
	 * @param username the username
	 * @return the user
	 */
	User findByUsername(String username);

	/**
	 * Get a user by username and password
	 * 
	 * @param username the username
	 * @param password the password
	 * @return the user
	 */
	User findByUsernameAndPassword(String username, String password);

	Optional<User> findById(int i);

	List<User> findAll();

	void save(User user);

	void delete(User user);

}
