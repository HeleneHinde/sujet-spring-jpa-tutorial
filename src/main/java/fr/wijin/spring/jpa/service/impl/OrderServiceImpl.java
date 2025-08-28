package fr.wijin.spring.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.wijin.spring.jpa.model.Order;
import fr.wijin.spring.jpa.repository.OrderRepository;
import fr.wijin.spring.jpa.service.OrderService;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class OrderServiceImpl implements OrderService{

	Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Integer valueOf) {
		Optional<Order> order = orderRepository.findById(valueOf);
		return order.isEmpty() ? null : order.get();
    }

    @Override
    public Order updateOrder(Order order) throws Exception {
		logger.debug("attempting to update order {}...", order.getId());
		Order orderExistant = this.getOrderById(order.getId());
		orderExistant.setLabel(order.getLabel());
		orderExistant.setAdrEt(order.getAdrEt());
		orderExistant.setNumberOfDays(order.getNumberOfDays());
		orderExistant.setTva(order.getTva());
		orderExistant.setStatus(order.getStatus());
		orderExistant.setType(order.getType());
		orderExistant.setNotes(order.getNotes());
		return orderRepository.save(orderExistant);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(int i) {
        Order order = orderRepository.getReferenceById(i);
        if(null != order){
            orderRepository.delete(order);  
        } else {
            logger.error("impossible de supprimer car il n'existe pas d'odrder avec ce numéro", i);
        }

    }
    
    
}
