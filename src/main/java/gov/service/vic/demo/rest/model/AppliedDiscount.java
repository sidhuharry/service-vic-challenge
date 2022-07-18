package gov.service.vic.demo.rest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AppliedDiscount {
    private String discountCode;
    private String desc;
    private float discountValue;
    private int quantity;
}
