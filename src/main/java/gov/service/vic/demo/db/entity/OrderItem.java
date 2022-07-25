package gov.service.vic.demo.db.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

}
