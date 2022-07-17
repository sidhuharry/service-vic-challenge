package gov.service.vic.demo.rest.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Discount {
    private String discountCode;
    private String desc;
    private int quantity;
}
