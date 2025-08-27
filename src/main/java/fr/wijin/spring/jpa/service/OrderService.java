package fr.wijin.spring.jpa.service;

import java.util.List;

import fr.wijin.spring.jpa.model.Order;

// TODO
public interface OrderService {

	List<Order> getAllOrders();

	Order getOrderById(Integer valueOf);

	void updateOrder(Order order);

	Order createOrder(Order order);

	void deleteOrder(int i);

}
