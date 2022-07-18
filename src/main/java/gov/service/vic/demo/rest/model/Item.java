package gov.service.vic.demo.rest.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Item {
    private String itemName;
    private float price;
    private int quantity;
    private String itemType;
}
