package gov.service.vic.demo.db.hardcoded;

import gov.service.vic.demo.rest.model.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class GroupedItemsDiscount {
    private Set<ItemType> itemsTogether;
    private float discountValue;
}
