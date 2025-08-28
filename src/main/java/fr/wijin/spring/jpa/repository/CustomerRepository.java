package fr.wijin.spring.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.wijin.spring.jpa.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

	/**
	 * Get a customer by lastname
	 * 
	 * @param lastname the lastname
	 * @return
	 */
	Customer findByLastname(String lastname);

	/**
	 * Get a list of customers by active
	 * 
	 * @param active true or false
	 * @return
	 */
	List<Customer> findByActive(@Param("active") Boolean active);

	/**
	 * Get a list of customers with a mobile number
	 * 
	 * @return
	 */
	@Query(value = "SELECT * FROM customers WHERE mobile IS NOT NULL", nativeQuery = true)
	List<Customer> findCustomersWithMobile();

	/**
	 * Get a page of customers by active
	 * 
	 * @param active   true or false
	 * @param pageable
	 * @return
	 */
	Page<Customer> findPageByActive(Boolean active, Pageable pageable);

}
