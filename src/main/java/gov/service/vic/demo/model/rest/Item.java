package gov.service.vic.demo.model.rest;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Item {
    private String itemName;
    private float price;
    private int quantity;
    private ItemType itemType;
}
