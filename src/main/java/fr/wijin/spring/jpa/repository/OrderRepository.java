package fr.wijin.spring.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.wijin.spring.jpa.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{


	@Query("select o from Order o where status= :status and type = :type")
	List<Order> findByTypeAndStatus(String type, String status);

	/*	Récupérer les commande à partir d'un Customer */
	//@Query("select o from Order o where o.customer.id = :customerId")
	@Query("select c.orders from Customer c where c.id = :customerId")
	List<Order> getOrdersByCustomerId(@Param("customerId")Integer customerId);


}
