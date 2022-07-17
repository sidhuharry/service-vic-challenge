package gov.service.vic.demo.db.service;

import gov.service.vic.demo.db.entity.Order;
import org.springframework.stereotype.Service;

@Service
public interface IOrderService {
    Order save(Order order);
}
