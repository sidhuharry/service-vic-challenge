package gov.service.vic.demo.service;

import gov.service.vic.demo.model.Order;
import org.springframework.stereotype.Service;

@Service
public interface IOrderService {
    Order save(Order order);
}
