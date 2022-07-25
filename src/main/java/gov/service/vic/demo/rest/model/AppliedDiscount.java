package gov.service.vic.demo.rest.model;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppliedDiscount {
    private String discountCode;
    private String desc;
    private float discountValue;
    private int quantity;
}
