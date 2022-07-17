package gov.service.vic.demo.rest;

import gov.service.vic.demo.model.Order;
import gov.service.vic.demo.model.rest.Customer;
import gov.service.vic.demo.model.rest.OrderRequest;
import gov.service.vic.demo.model.rest.OrderResponse;
import gov.service.vic.demo.model.rest.OrderStatus;
import gov.service.vic.demo.rest.utils.Mapper;
import gov.service.vic.demo.service.impl.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private OrderService orderService;

    private Mapper mapper;

    public CustomerController(OrderService orderService, Mapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @PostMapping("/{customerId}/order")
    OrderResponse newOrder(@RequestBody OrderRequest orderRequest, @PathVariable String customerId) {

        // validate the request

        // process the request

        // convert it into payload and return
        Order order = orderService.save(mapper.toOrder(orderRequest, customerId));
        // get any message
        // set status
        return mapper.toResponse(order, OrderStatus.SUCCESS, "Order placed. Your wait time is eternity.");
    }


    @PutMapping("/{customerId}/order/{orderId}")
    OrderResponse newOrder(@RequestBody OrderRequest orderRequest, @PathVariable String customerId,
                           @PathVariable String orderId) {

        // validate the request

        // process the request

        // convert it into payload and return
        Order order = orderService.save(mapper.toOrder(orderRequest, customerId, orderId));
        // get any message
        // set status
        return mapper.toResponse(order, OrderStatus.SUCCESS, "Order Updated with new items");
    }

}
