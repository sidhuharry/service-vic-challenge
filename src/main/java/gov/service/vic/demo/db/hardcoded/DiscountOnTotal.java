package gov.service.vic.demo.db.hardcoded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DiscountOnTotal {
    private int discPercentage;
    private float spendingLimit;
}
