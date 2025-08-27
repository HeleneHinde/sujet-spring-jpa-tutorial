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
import fr.wijin.spring.jpa.model.Order;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext
class OrderServiceImplTest {

	@Autowired
	private OrderService orderService;

	@Test
	void testGetAllOrders() {
		List<Order> orders = orderService.getAllOrders();
		assertEquals(3, orders.size());
	}

	@Test
	void testGetOrderById() {
		Order order = orderService.getOrderById(Integer.valueOf(1));
		assertNotNull(order);
	}

	@Test
	void testCreate() {
		Order order = new Order();
		order.setLabel("Libellé");
		Order createdOrder = orderService.createOrder(order);
		assertNotNull(createdOrder);
		List<Order> orders = orderService.getAllOrders();
		System.out.println("Nombre de orders : " + orders.size());
	}

	@Test
	void testUpdate() {
		Order order = new Order();
		order.setId(Integer.valueOf(1));
		order.setLabel("Nouveau libellé");
		try {
			orderService.updateOrder(order);
		} catch (Exception e) {
			fail();
		}

		Order modifiedOrder = orderService.getOrderById(1);
		assertEquals("Nouveau libellé", modifiedOrder.getLabel());
		List<Order> orders = orderService.getAllOrders();
		System.out.println("Nombre de orders : " + orders.size());
	}

	@Test
	void testDelete() {
		orderService.deleteOrder(3);
		Order deletedOrder = orderService.getOrderById(3);
		assertNull(deletedOrder);
		List<Order> orders = orderService.getAllOrders();
		System.out.println("Nombre de orders : " + orders.size());
	}

}
