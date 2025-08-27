package fr.wijin.spring.jpa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.wijin.spring.jpa.model.Order;

@Service
public interface OrderService {

	List<Order> getAllOrders();

	Order getOrderById(Integer valueOf);

	Order updateOrder(Order order) throws Exception;

	Order createOrder(Order order);

	void deleteOrder(int i);

}
