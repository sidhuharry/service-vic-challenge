package gov.service.vic.demo.db.service.impl;

import gov.service.vic.demo.db.entity.Order;
import gov.service.vic.demo.db.repo.OrderRepo;
import gov.service.vic.demo.db.service.IOrderService;
import org.springframework.stereotype.Service;

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
}
