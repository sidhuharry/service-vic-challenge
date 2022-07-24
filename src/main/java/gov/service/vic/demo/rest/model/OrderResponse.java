package gov.service.vic.demo.rest.model;

import gov.service.vic.demo.db.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class OrderResponse {
    private UUID orderId;
    private OrderStatus orderStatus;
    private String message;
    /**
     * This field will have valid data which was added to the DB.
     * Whoever made call to the endpoints, can use this response to show the finalised order.
     */
    private Order savedData;
}

