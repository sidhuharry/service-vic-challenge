package gov.service.vic.demo.rest.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.List;

@Builder
@Getter
public class OrderRequest {

    private Customer customer;
    private List<Item> items;
    private List<Discount> discounts;
    /**
     * To be used in case user want to update the order, otherwise null
     */
    @Nullable
    private String orderId;

}

