package gov.service.vic.demo.rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private String customerId;

    public Customer(String customerId) {
        this.customerId = customerId;
    }
}
