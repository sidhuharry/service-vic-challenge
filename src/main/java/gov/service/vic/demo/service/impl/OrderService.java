package gov.service.vic.demo.service.impl;

import gov.service.vic.demo.model.Order;
import gov.service.vic.demo.repo.OrderRepo;
import gov.service.vic.demo.service.IOrderService;
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
