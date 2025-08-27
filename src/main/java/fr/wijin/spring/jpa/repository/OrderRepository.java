package fr.wijin.spring.jpa.repository;

import java.util.List;
import java.util.Optional;

import fr.wijin.spring.jpa.model.Order;

// TODO
public interface OrderRepository {

	Optional<Order> findById(int i);

	List<Order> findAll();

	List<Order> findByTypeAndStatus(String string, String string2);

	Order save(Order newOrder);

	void delete(Order order);

}
