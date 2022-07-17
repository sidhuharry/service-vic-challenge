package gov.service.vic.demo.service.impl;

import gov.service.vic.demo.db.entity.Order;
import gov.service.vic.demo.db.repo.OrderRepo;
import gov.service.vic.demo.service.IOrderService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {

    private OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Order save(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public Optional<Order> orderExists(UUID orderId) {
        return orderRepo.findById(orderId);
    }
}
