package gov.service.vic.demo.rest.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OrderRequest {

    private Customer customer;
    private List<Item> items;
    private List<Discount> discounts;

}

