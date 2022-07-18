package gov.service.vic.demo.rest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.List;

@Builder
@Getter
@Setter
public class OrderRequest implements  Cloneable {

    private Customer customer;
    private List<Item> items;
    private List<AppliedDiscount> discounts;
    private float amount;
    private float amountBeforeDiscounts;
    /**
     * To be used in case user want to update the order, otherwise null
     */
    @Nullable
    private String orderId;

    @Override public OrderRequest clone() {
        try {
            OrderRequest clone = (OrderRequest) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

