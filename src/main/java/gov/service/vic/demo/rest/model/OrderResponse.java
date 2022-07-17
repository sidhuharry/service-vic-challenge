package gov.service.vic.demo.rest.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public class OrderResponse {
    private UUID orderId;
    private OrderStatus orderStatus;
    private String message;
}

