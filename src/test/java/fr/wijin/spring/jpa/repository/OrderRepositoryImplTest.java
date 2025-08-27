package fr.wijin.spring.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
import fr.wijin.spring.jpa.model.Customer;
import fr.wijin.spring.jpa.model.Order;
import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
@TestMethodOrder(OrderAnnotation.class)
class OrderRepositoryImplTest {

	Logger logger = LoggerFactory.getLogger(OrderRepositoryImplTest.class);

	public static final String LABEL = "Formation Java";
	public static final String LASTNAME = "JONES";

	@Autowired
	private OrderRepository orderRepository;

	@Test
	@org.junit.jupiter.api.Order(1)
	void givenOrderId_whenCallingTypedQueryMethod_thenReturnOrder() {
		Optional<Order> order = orderRepository.findById(1);
		System.out.println("Commande = " + order.get());
		logger.debug("Commande = {}", order.get());
		Assertions.assertTrue(order.isPresent(), "Order not found");
	}

	@Test
	@org.junit.jupiter.api.Order(2)
	void givenOrderId_whenCallingTypedQueryMethod_thenReturnExpectedOrder() {
		Optional<Order> order = orderRepository.findById(1);
		Assertions.assertEquals(LABEL, order.get().getLabel(), "Order label should be " + LABEL);
	}

	@Test
	@org.junit.jupiter.api.Order(3)
	void givenOrderId_whenCallingTypedQueryMethod_thenReturnExpectedCustomer() {
		Optional<Order> order = orderRepository.findById(1);
		Assertions.assertEquals(LASTNAME, order.get().getCustomer().getLastname(),
				"Customer lastname should be " + LASTNAME);
	}

	@Test
	@org.junit.jupiter.api.Order(4)
	void whenCallingTypedQueryMethod_thenReturnListOfOrders() {
		List<Order> orders = orderRepository.findAll();
		Assertions.assertEquals(3, orders.size(), "Wrong number of orders");
	}

	@Test
	@org.junit.jupiter.api.Order(5)
	void whenCallingNamedQueryMethod_thenReturnListOfOrders() {
		List<Order> orders = orderRepository.findByTypeAndStatus("Forfait", "En attente");
		Assertions.assertEquals(1, orders.size(), "Wrong number of orders for type and status");
	}

	@Test
	@org.junit.jupiter.api.Order(6)
	void givenOrder_whenCallingTypedQueryMethodForCreate_thenReturnOK() {
		Order newOrder = new Order();
		newOrder.setLabel(LABEL);
		newOrder.setNumberOfDays(Double.valueOf(5));
		newOrder.setAdrEt(Double.valueOf(350));
		newOrder.setTva(Double.valueOf(20.0));
		newOrder.setType("Super commande");
		newOrder.setStatus("En cours");
		newOrder.setNotes("Les notes sur la commande");
		Customer customer = new Customer();
		customer.setId(4);
		newOrder.setCustomer(customer);

		List<Order> orders = orderRepository.findAll();
		int numberOfOrdersBeforeCreation = orders.size();

		Order createdOrder = orderRepository.save(newOrder);
		System.out.println("Commande créée = " + createdOrder);
		System.out.println("Customer associé = " + createdOrder.getCustomer());
		List<Order> ordersAfterCreation = orderRepository.findAll();
		int numberOfOrdersAfterCreation = ordersAfterCreation.size();
		Assertions.assertEquals(numberOfOrdersBeforeCreation + 1, numberOfOrdersAfterCreation);
	}

	@Test
	@org.junit.jupiter.api.Order(7)
	void givenOrder_whenCallingTypedQueryMethodForUpdate_thenReturnOK() {

		Optional<Order> order = orderRepository.findById(2);
		order.get().setLabel("Nouveau libellé");

		orderRepository.save(order.get());

		Optional<Order> updatedOrder = orderRepository.findById(2);
		Assertions.assertEquals("Nouveau libellé", updatedOrder.get().getLabel());
	}

	@Test
	@org.junit.jupiter.api.Order(8)
	void givenOrder_whenCallingTypedQueryMethodForDelete_thenReturnOK() {
		Optional<Order> order = orderRepository.findById(2);

		orderRepository.delete(order.get());

		Optional<Order> deletedOrder = orderRepository.findById(2);
		Assertions.assertTrue(deletedOrder.isEmpty(), "Deleted order must be null");
	}

}
