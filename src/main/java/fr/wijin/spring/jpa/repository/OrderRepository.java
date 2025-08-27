package fr.wijin.spring.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.wijin.spring.jpa.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{


	@Query("select o from Order o where status= :string2 ans type = :string")
	List<Order> findByTypeAndStatus(String string, String string2);

}
