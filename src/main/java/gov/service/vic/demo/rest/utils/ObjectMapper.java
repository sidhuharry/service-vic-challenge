package gov.service.vic.demo.rest.utils;

import gov.service.vic.demo.db.entity.Order;
import gov.service.vic.demo.db.entity.OrderItem;
import gov.service.vic.demo.rest.model.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service public class ObjectMapper {

    public Order toOrder(OrderRequest orderRequest, String customerId) {
        Order order = new Order();
        if (!orderRequest.getItems().isEmpty()) {
            order.setOrderItems(orderRequest.getItems().stream().map(this::toOrderItem).collect(Collectors.toSet()));
        }

        if (!orderRequest.getDiscounts().isEmpty()) {
            order.setDiscounts(
                    orderRequest.getDiscounts().stream().map(this::toDiscountItem).collect(Collectors.toSet()));
        }

        order.setCustomerId(customerId);

        return order;
    }

    public Order toOrder(OrderRequest orderRequest, String customerId, String orderId) {
        Order order = toOrder(orderRequest, customerId);
        order.setOrderId(orderId);
        return order;
    }

    public OrderResponse toResponse(Order order, OrderStatus orderStatus, String message) {
        return OrderResponse.builder().orderId(order.getOrderId()).orderStatus(orderStatus).message(message).build();
    }

    private OrderItem toOrderItem(Item requestItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemName(requestItem.getItemName());
        orderItem.setPrice(requestItem.getPrice());
        orderItem.setQuantity(requestItem.getQuantity());

        return orderItem;
    }

    private gov.service.vic.demo.db.entity.Discount toDiscountItem(Discount requestDiscount) {
        gov.service.vic.demo.db.entity.Discount discount = new gov.service.vic.demo.db.entity.Discount();
        discount.setDiscountCode(requestDiscount.getDiscountCode());
        discount.setQuantity(requestDiscount.getQuantity());
        discount.setDesc(requestDiscount.getDesc());
        return discount;
    }

}
