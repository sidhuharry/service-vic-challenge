package gov.service.vic.demo.service;

import gov.service.vic.demo.db.entity.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface IOrderService {
    Order save(Order order);

    Optional<Order> orderExists(UUID orderId);
}
