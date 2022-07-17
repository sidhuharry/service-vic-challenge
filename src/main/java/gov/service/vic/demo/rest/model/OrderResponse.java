package gov.service.vic.demo.rest.model;

import lombok.Builder;

@Builder
public class OrderResponse {
    private String orderId;
    private OrderStatus orderStatus;
    private String message;
}

