package gov.service.vic.demo.rest.model;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public class OrderResponse {
    private UUID orderId;
    private OrderStatus orderStatus;
    private String message;
    /**
     * This field will have valid data which was added to the DB.
     * Whoever made call to the endpoints, can use this response to show the finalised order.
     */
    private OrderRequest savedData;
}

