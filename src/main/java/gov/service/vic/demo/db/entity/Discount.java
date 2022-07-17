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
public class Discount {

    @Id
    private String discountCode;

    private int quantity;

    private String desc;

    @ManyToOne
    private Order order;

    public Discount() {

    }

}