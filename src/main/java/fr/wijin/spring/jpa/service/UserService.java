package fr.wijin.spring.jpa.service;

import java.util.List;

import fr.wijin.spring.jpa.model.User;

public interface UserService {

	/**
	 * Get all users
	 * 
	 * @return a list of users
	 */
	List<User> getAllUsers();

	/**
	 * Get a user by id
	 * 
	 * @param id the id
	 * @return the user
	 */
	User getUserById(Integer id);

	/**
	 * Create a user
	 * 
	 * @param user the user to create
	 * @return the user
	 */
	User createUser(User user);

	/**
	 * Update a user
	 * 
	 * @param user the user to update
	 * @return the user
	 * @throws Exception
	 */
	User updateUser(User user) throws Exception;

	/**
	 * Delete a user
	 * 
	 * @param id the id of the user
	 */
	void deleteUser(Integer id);

}
