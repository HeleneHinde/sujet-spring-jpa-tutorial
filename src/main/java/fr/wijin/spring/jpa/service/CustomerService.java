package fr.wijin.spring.jpa.service;

import java.util.List;

import fr.wijin.spring.jpa.model.Customer;

public interface CustomerService {

	/**
	 * Get a customer by id
	 * 
	 * @param id the id
	 * @return the Customer
	 */
	Customer getCustomerById(Integer id);

	/**
	 * Get all customers
	 * 
	 * @return a list of customers
	 */
	List<Customer> getAllCustomers();

	/**
	 * Get customers by active status
	 * 
	 * @param active true or false
	 * @return a list of customers by active status
	 */
	List<Customer> getCustomersByActive(Boolean active);

	/**
	 * Create a customer
	 * 
	 * @param customer the customer to create
	 */
	Customer createCustomer(Customer customer);

	/**
	 * Create 2 customers with a pb to demonstrate transactional rollback
	 * 
	 * @param customer
	 * @return
	 */
	void createCustomersWithProblem(Customer customer, Customer customer2);

	/**
	 * Update a customer
	 * 
	 * @param customer the customer to update
	 * @return
	 */
	Customer updateCustomer(Customer customer) throws Exception;

	/**
	 * Delete a customer
	 * 
	 * @param customerId the id of the customer to delete
	 */
	void deleteCustomer(Integer customerId);

	/**
	 * Try to delete 2 customers with a pb to demonstrate transactional rollback
	 * 
	 * @param customer
	 * @throws Exception
	 */
	void deleteCustomersWithProblem(Customer customer) throws Exception;

	/**
	 * Update the customer's status (active)
	 * 
	 * @param customerId Id of the customer
	 * @param active     boolean value of the status to patch
	 * @throws Exception
	 */
	void patchCustomerStatus(Integer customerId, boolean active) throws Exception;

	public List<Customer> searchCustomer(String lastname, String firstname, String phone);
}
