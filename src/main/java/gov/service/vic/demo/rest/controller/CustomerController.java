package gov.service.vic.demo.rest.controller;

import gov.service.vic.demo.db.entity.Customer;
import gov.service.vic.demo.db.entity.Order;
import gov.service.vic.demo.rest.exception.InternalErrorException;
import gov.service.vic.demo.rest.utils.RequestValidator;
import gov.service.vic.demo.service.impl.CustomerService;
import gov.service.vic.demo.rest.exception.ResourceNotFoundException;
import gov.service.vic.demo.rest.model.OrderRequest;
import gov.service.vic.demo.rest.model.OrderResponse;
import gov.service.vic.demo.rest.model.OrderStatus;
import gov.service.vic.demo.rest.utils.ObjectMapper;
import gov.service.vic.demo.service.impl.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private OrderService orderService;

    private CustomerService customerService;

    private ObjectMapper objectMapper;

    private RequestValidator requestValidator;

    public CustomerController(OrderService orderService, CustomerService customerService, ObjectMapper objectMapper,
                              RequestValidator requestValidator) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.objectMapper = objectMapper;
        this.requestValidator = requestValidator;
    }

    @PostMapping("/new")
    Customer newCustomer() {
        Customer customer = null;
        try {
            customer = customerService.save(new Customer());
        } catch (Exception exception) {
            throw new InternalErrorException("Could not generate the new customer.");
        }
        return customer;
    }

    @PostMapping("/{customerId}/order")
    OrderResponse newOrder(@RequestBody OrderRequest orderRequest, @PathVariable String customerId) {

        // [START] Validate the request
        // 1. Check if customer exists
        customerService.customerExists(UUID.fromString(customerId))
                .orElseThrow(() -> new ResourceNotFoundException(customerId));
        orderRequest.setCustomer(new gov.service.vic.demo.rest.model.Customer(customerId));
        // 2. Check payload for discrepancy and sanitize if found any
        OrderRequest sanitizedRequest = requestValidator.validateNewOrderRequest(orderRequest);
        // [END] Validate the request

        // Save order
        Order order = null;
        //try {
            order = orderService.save(objectMapper.toOrder(sanitizedRequest, customerId));
        //} catch (Exception exception) {
          //  throw new InternalErrorException(exception.getMessage());
        //}

        return order == null ? objectMapper.toResponse(order, OrderStatus.FAILURE,
                                                       "Unknown error")
                : objectMapper.toResponse(order, OrderStatus.SUCCESS, "Order placed. Your wait time is eternity.");
    }


    @PutMapping("/{customerId}/order/{orderId}")
    OrderResponse newOrder(@RequestBody OrderRequest orderRequest, @PathVariable String customerId,
                           @PathVariable String orderId) {

        // [START] Validate the request
        // 1. Check if customer exists
        customerService.customerExists(UUID.fromString(customerId))
                .orElseThrow(() -> new ResourceNotFoundException(customerId));
        // 2. Check payload
        requestValidator.validateUpdateOrderRequestFields(orderRequest);
        // [END] Validate the request

        // process the request
        // Save order
        Order order = null;
        try {
            order = orderService.save(objectMapper.toOrder(orderRequest, orderId));
        } catch (Exception exception) {
            throw new InternalErrorException(exception.getMessage());
        }

        return order == null ? objectMapper.toResponse(order, OrderStatus.FAILURE,
                                                       "Unknown error")
                : objectMapper.toResponse(order, OrderStatus.SUCCESS, "Order placed. Your wait time is eternity.");
    }

}
