package gov.service.vic.demo.model.rest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
public class OrderRequest {

    private Customer customer;
    private List<Item> items;
    private List<Discount> discounts;

}

