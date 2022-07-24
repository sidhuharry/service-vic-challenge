package gov.service.vic.demo.rest.utils;

import gov.service.vic.demo.db.entity.Order;
import gov.service.vic.demo.db.entity.OrderItem;
import gov.service.vic.demo.rest.model.*;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service public class ObjectMapper {

    public Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();
        if (!orderRequest.getItems().isEmpty()) {
            order.setOrderItems(orderRequest.getItems().stream().map(this::toOrderItem).collect(Collectors.toSet()));
        }

        if (!orderRequest.getDiscounts().isEmpty()) {
            order.setAppliedDiscounts(
                    orderRequest.getDiscounts().stream().map(this::toDiscountItem).collect(Collectors.toSet()));
        }

        order.setCustomerId(orderRequest.getCustomer().getCustomerId());
        order.setAmount(orderRequest.getAmount());

        return order;
    }

    public Order toOrder(OrderRequest orderRequest, String orderId) {
        Order order = toOrder(orderRequest);
        order.setOrderId(UUID.fromString(orderId));
        return order;
    }

    public OrderResponse toResponse(@NonNull Order order, OrderStatus orderStatus, String message) {
        return OrderResponse.builder().orderId(order.getOrderId()).orderStatus(orderStatus).message(message)
                .savedData(order).build();
    }

    private OrderItem toOrderItem(Item requestItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemName(requestItem.getItemName());
        orderItem.setPrice(requestItem.getPrice());
        orderItem.setQuantity(requestItem.getQuantity());
        return orderItem;
    }

    private gov.service.vic.demo.db.entity.AppliedDiscount toDiscountItem(AppliedDiscount requestDiscount) {
        gov.service.vic.demo.db.entity.AppliedDiscount appliedDiscount = new gov.service.vic.demo.db.entity.AppliedDiscount();
        appliedDiscount.setDiscountCode(requestDiscount.getDiscountCode());
        appliedDiscount.setQuantity(requestDiscount.getQuantity());
        appliedDiscount.setDesc(requestDiscount.getDesc());
        appliedDiscount.setDiscountValue(requestDiscount.getDiscountValue());
        return appliedDiscount;
    }

}
