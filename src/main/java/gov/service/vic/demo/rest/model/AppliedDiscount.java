package gov.service.vic.demo.rest.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppliedDiscount {
    private String discountCode;
    private String desc;
    private float discountValue;
    private int quantity;
}
