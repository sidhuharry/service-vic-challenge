package gov.service.vic.demo.db.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class OrderItem {

    public OrderItem() {
    }

    @Id
    @GeneratedValue
    private long orderItemId;

    private String itemName;

    private int quantity;

    private float price;

    @ManyToOne
    private Order order;

}
