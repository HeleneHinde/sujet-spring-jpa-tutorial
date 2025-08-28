package fr.wijin.spring.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.wijin.spring.jpa.model.Customer;
import fr.wijin.spring.jpa.repository.CustomerRepository;
import fr.wijin.spring.jpa.service.CustomerService;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

	Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Customer getCustomerById(Integer customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		return customer.isEmpty() ? null : customer.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Customer> getCustomersByActive(Boolean active) {
		return customerRepository.findByActive(active);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void createCustomersWithProblem(Customer customer, Customer customer2) {
		customerRepository.save(customer);
		customerRepository.save(customer2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Customer updateCustomer(Customer customer) throws Exception {
		logger.debug("attempting to update customer {}...", customer.getId());
		Customer clientExistant = customerRepository.findById(customer.getId()).orElseThrow(Exception::new);
		clientExistant.setLastname(customer.getLastname());
		clientExistant.setFirstname(customer.getFirstname());
		clientExistant.setMail(customer.getMail());
		clientExistant.setCompany(customer.getCompany());
		clientExistant.setMobile(customer.getMobile());
		clientExistant.setNotes(customer.getNotes());
		clientExistant.setActive(customer.isActive());
		return customerRepository.save(clientExistant);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteCustomer(Integer customerId) {
		Customer customerToDelete = getCustomerById(customerId);
		if (customerToDelete != null) {
			customerRepository.delete(customerToDelete);
		} else {
			logger.error("Le customer n'existe pas : {}", customerId);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteCustomersWithProblem(Customer customer) throws Exception {
		if (getCustomerById(customer.getId()) != null) {
			customerRepository.delete(customer);

			Customer customer2 = new Customer();
			customer2.setId(99);

			customerRepository.delete(customer2);
			throw new Exception("Sortie pour simuler un pb");
		} else {
			logger.error("Le customer n'existe pas : {}", customer.getId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void patchCustomerStatus(Integer customerId, boolean active) throws Exception {
		logger.debug("attempting to patch customer {} with active = {}...", customerId, active);
		Customer existingCustomer = customerRepository.findById(customerId).orElseThrow(Exception::new);
		existingCustomer.setActive(active);
		customerRepository.save(existingCustomer);
	}

	@Override
	public List<Customer> searchCustomer(String lastname, String firstname, String phone) {

		Specification<Customer> specLastname = (root, criteriaQuery, criteriaBuilder) -> 
				criteriaBuilder.equal(root.get("lastname"), lastname);

		Specification<Customer> specFirstname = (root, criteriaQuery, criteriaBuilder) -> 
				criteriaBuilder.equal(root.get("firstname"), firstname);


		Specification<Customer> specPhone = (root, criteriaQuery, criteriaBuilder) -> 
				criteriaBuilder.equal(root.get("phone"), phone);

		Specification<Customer> recherche = null;
		if(lastname != null){
			recherche = recherche == null ? specLastname : recherche.and(specLastname);
		}
		if(firstname != null){
			recherche = recherche == null ? specFirstname : recherche.and(specFirstname);
		}
		if(phone != null){
			recherche = recherche == null ? specPhone : recherche.and(specPhone);
		}

		Sort sort = Sort.by("lastname");
		sort = sort.ascending();
		sort = sort.and(Sort.by("firstname").descending());
		Pageable pageable = PageRequest.of(0, 10, sort);

		Page<Customer> page = customerRepository.findAll(recherche, pageable);
		long nbCustomers = page.getTotalElements();
		int nbPages = page.getTotalPages();
		logger.info("Nombre de clients trouvés : {}, Nombre de pages : {}", nbCustomers, nbPages);

		return page.getContent();

	}

}
