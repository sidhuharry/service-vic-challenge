package gov.service.vic.demo.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class AppliedDiscount {

    @Id
    private String discountCode;

    private float discountValue;

    private int quantity;

    private String desc;

    @ManyToOne
    private Order order;

    public AppliedDiscount() {

    }

}
