package fr.wijin.spring.jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import fr.wijin.spring.jpa.config.AppConfig;
import fr.wijin.spring.jpa.model.Customer;
import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
class CustomerRepositoryImplTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void testFindByLastname() {
		Customer customer = customerRepository.findByLastname("JONES");
		assertEquals("Indiana", customer.getFirstname());
	}

	@Test
	void testGetAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		assertEquals(4, customers.size());
	}

	@Test
	void testGetCustomersByActive() {
		List<Customer> customers = customerRepository.findByActive(true);
		assertEquals(2, customers.size());
	}

	@Test
	void testFindCustomersWithMobile() {
		List<Customer> customers = customerRepository.findCustomersWithMobile();
		customers.forEach(customer -> assertNotNull(customer.getMobile()));
		assertEquals(3, customers.size());
	}

	@Test
	void testfindAllByActive() {
		// Première page, avec 1 élement par page
		Pageable pagingCustomer = PageRequest.of(0, 1);
		Page<Customer> customersPage1 = customerRepository.findPageByActive(true, pagingCustomer);
		System.out.println("Pagination Customers : N° de page = " + customersPage1.getNumber()
				+ ", Nombre elements sur page = " + customersPage1.getNumberOfElements() + ", Nb total elements = "
				+ customersPage1.getTotalElements() + ", nb total pages = " + customersPage1.getTotalPages());
		assertEquals(1, customersPage1.getNumberOfElements());

		Pageable pagingCustomer2 = PageRequest.of(1, 1);
		Page<Customer> customersPage2 = customerRepository.findPageByActive(true, pagingCustomer2);
		System.out.println("Pagination Customers : N° de page = " + customersPage2.getNumber()
				+ ", Nombre elements sur page = " + customersPage2.getNumberOfElements() + ", Nb total elements = "
				+ customersPage2.getTotalElements() + ", nb total pages = " + customersPage2.getTotalPages());
		assertEquals(1, customersPage2.getNumberOfElements());
	}

	@Test
	void testCreate() {
		Customer newCustomer = new Customer();
		newCustomer.setFirstname("Winnie");
		newCustomer.setLastname("L'Ourson");
		newCustomer.setCompany("Disney");
		newCustomer.setPhone("0222222222");
		newCustomer.setMobile("0666666666");
		newCustomer.setMail("winnie.l.ourson@disney.com");
		newCustomer.setNotes("Les notes de Winnie");
		newCustomer.setActive(true);

		customerRepository.save(newCustomer);

		Customer customer = customerRepository.findByLastname("L'Ourson");
		Assertions.assertNotNull(customer, "Winnie not found");
	}

	@Test
	void testUpdate() {
		Customer customer = customerRepository.findByLastname("JONES");
		customer.setCompany("Nouvelle entreprise");

		customerRepository.save(customer);

		Customer updatedCustomer = customerRepository.findByLastname("JONES");
		Assertions.assertEquals("Nouvelle entreprise", updatedCustomer.getCompany());
	}

	@Test
	void testDelete() {
		Optional<Customer> customer = customerRepository.findById(2);
		if (customer.isEmpty()) {
			fail();
		}
		customerRepository.delete(customer.get());

		Optional<Customer> deletedCustomer = customerRepository.findById(2);
		Assertions.assertTrue(deletedCustomer.isEmpty(), "Deleted customer must be null");

	}
}
