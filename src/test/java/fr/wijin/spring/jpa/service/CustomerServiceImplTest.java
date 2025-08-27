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
import fr.wijin.spring.jpa.model.Customer;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext
class CustomerServiceImplTest {

	@Autowired
	private CustomerService customerService;

	@Test
	void testGetAll() {
		List<Customer> customers = customerService.getAllCustomers();
		assertEquals(4, customers.size());
	}

	@Test
	void testGetById() {
		Customer customer = customerService.getCustomerById(Integer.valueOf(1));
		assertNotNull(customer);
	}

	@Test
	void testCreate() {
		Customer customer = new Customer();
		customer.setLastname("MARTIN");
		Customer createdCustomer = customerService.createCustomer(customer);
		assertNotNull(createdCustomer);
		List<Customer> customers = customerService.getAllCustomers();
		System.out.println("Nombre de customers : " + customers.size());
	}

	@Test
	void testUpdate() {
		Customer customer = new Customer();
		customer.setId(Integer.valueOf(1));
		customer.setFirstname("Bob");
		try {
			customerService.updateCustomer(customer);
		} catch (Exception e) {
			fail();
		}

		Customer modifiedCustomer = customerService.getCustomerById(1);
		assertEquals("Bob", modifiedCustomer.getFirstname());
		List<Customer> customers = customerService.getAllCustomers();
		System.out.println("Nombre de customers : " + customers.size());
	}

	@Test
	void testDelete() {
		Customer customer = new Customer();
		customer.setId(Integer.valueOf(3));
		customerService.deleteCustomer(3);

		Customer deletedCustomer = customerService.getCustomerById(3);
		assertNull(deletedCustomer);
		List<Customer> customers = customerService.getAllCustomers();
		System.out.println("Nombre de customers : " + customers.size());
	}

	@Test
	void testCreatePb() {
		Customer customer = new Customer();
		customer.setFirstname("Darth");
		customer.setLastname("Vader");

		Customer customer2 = new Customer();
		customer2.setFirstname("Darth");
		customer2.setLastname("Vader");
		try {
			customerService.createCustomersWithProblem(customer, customer2);
		} catch (Exception e) {
			System.out.println("Exception  : " + e.getMessage());
		}

		List<Customer> customers = customerService.getAllCustomers();
		assertEquals(4, customers.size());
	}

	@Test
	void testDeletePb() throws Exception {
		Customer customer = new Customer();
		customer.setId(Integer.valueOf(4));

		try {
			List<Customer> customers = customerService.getAllCustomers();
			System.out.println("Nombre de customers avant suppression : " + customers.size());
			customerService.deleteCustomersWithProblem(customer);
		} catch (Exception e) {
			System.out.println("Exception  : " + e.getMessage());
		}

		List<Customer> customers = customerService.getAllCustomers();
		System.out.println("Nombre de customers après suppression : " + customers.size());
		assertEquals(4, customers.size());
	}

}
