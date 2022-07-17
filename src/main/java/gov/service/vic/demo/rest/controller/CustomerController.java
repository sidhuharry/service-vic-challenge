package gov.service.vic.demo.rest.controller;

import gov.service.vic.demo.db.entity.Order;
import gov.service.vic.demo.rest.model.OrderRequest;
import gov.service.vic.demo.rest.model.OrderResponse;
import gov.service.vic.demo.rest.model.OrderStatus;
import gov.service.vic.demo.rest.utils.ObjectMapper;
import gov.service.vic.demo.db.service.impl.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private OrderService orderService;

    private ObjectMapper objectMapper;

    public CustomerController(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/{customerId}/order")
    OrderResponse newOrder(@RequestBody OrderRequest orderRequest, @PathVariable String customerId) {

        // validate the request

        // process the request

        // convert it into payload and return
        Order order = orderService.save(objectMapper.toOrder(orderRequest, customerId));
        // get any message
        // set status
        return objectMapper.toResponse(order, OrderStatus.SUCCESS, "Order placed. Your wait time is eternity.");
    }


    @PutMapping("/{customerId}/order/{orderId}")
    OrderResponse newOrder(@RequestBody OrderRequest orderRequest, @PathVariable String customerId,
                           @PathVariable String orderId) {

        // validate the request

        // process the request

        // convert it into payload and return
        Order order = orderService.save(objectMapper.toOrder(orderRequest, customerId, orderId));
        // get any message
        // set status
        return objectMapper.toResponse(order, OrderStatus.SUCCESS, "Order Updated with new items");
    }

}
