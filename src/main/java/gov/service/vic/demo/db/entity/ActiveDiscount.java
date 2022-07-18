package gov.service.vic.demo.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
//TODO Make this store active discounts
public class ActiveDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int activeDiscountId;

    private String discountCode;

    public ActiveDiscount() {}
}
