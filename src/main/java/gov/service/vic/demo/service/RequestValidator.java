package gov.service.vic.demo.service;

import gov.service.vic.demo.rest.exception.InvalidRequestException;
import gov.service.vic.demo.rest.model.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RequestValidator {

    public void validateNewOrderRequest(OrderRequest orderRequest) throws InvalidRequestException {
        if (Objects.isNull(orderRequest.getCustomer().getCustomerId()) ||
                orderRequest.getCustomer().getCustomerId().isEmpty()) {
            throw new InvalidRequestException("Customer Id can't be null/empty");
        }

        if (Objects.isNull(orderRequest.getItems()) || orderRequest.getItems().isEmpty()) {
            throw new InvalidRequestException("Item list is empty. There can't be an order without items.");
        }

    }

    public void validateUpdateOrderRequest(OrderRequest orderRequest) throws InvalidRequestException {
        validateNewOrderRequest(orderRequest);
        if (orderRequest.getOrderId() == null) {
            throw new InvalidRequestException(
                    "Order Id is missing, you need to tell me which order am I supposed to update?");
        }
    }
}
