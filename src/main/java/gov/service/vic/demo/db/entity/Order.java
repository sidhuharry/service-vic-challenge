package gov.service.vic.demo.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@ToString
@Entity
@Getter
@Setter
@Table(name = "CUSTOMER_ORDERS")
public class Order {
    public Order() {
    }

    private String customerId;

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID orderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<AppliedDiscount> appliedDiscounts;

    private float amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return orderId != null && Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
