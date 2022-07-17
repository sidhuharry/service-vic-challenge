package gov.service.vic.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Menu")
public class MenuItem {

    @Id
    @GeneratedValue
    private int menuItemId;

    private String itemName;

    private float price;

    public MenuItem() {
    }

    public MenuItem(int menuItemId, String itemName, float price) {
        this.menuItemId = menuItemId;
        this.itemName = itemName;
        this.price = price;
    }
}
