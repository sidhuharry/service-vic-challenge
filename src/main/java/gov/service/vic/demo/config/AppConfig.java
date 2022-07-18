package gov.service.vic.demo.config;

import gov.service.vic.demo.db.entity.MenuItem;
import gov.service.vic.demo.db.hardcoded.DiscountCode;
import gov.service.vic.demo.db.hardcoded.DiscountOnTotal;
import gov.service.vic.demo.db.hardcoded.GroupedItemsDiscount;
import gov.service.vic.demo.rest.model.ItemType;
import gov.service.vic.demo.service.IMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * Inits the in-memory db with dummy values
 */
@Configuration public class AppConfig {

    private final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    /**
     * This method will run when the application context is loaded
     */
    @Bean CommandLineRunner initDb(IMenuService menuService) {
        return (args) -> {
            LOG.debug("Starting local h2 db with dummy values in menu");
            menuService.saveAll(Arrays.asList(new MenuItem(1, "Hamburger", 5.0f), new MenuItem(2, "Cheeseburger", 6.0f),
                                              new MenuItem(3, "Chicken Burger", 7.0f),
                                              new MenuItem(2, "Cheeseburger", 6.0f), new MenuItem(4, "Fries", 3.0f),
                                              new MenuItem(5, "Coke", 2.0f), new MenuItem(6, "Pepsi", 2.0f),
                                              new MenuItem(7, "Sundae", 3.0f), new MenuItem(8, "Apple Pie", 3.0f)));
        };
    }

    /**
     * Stores the active bundled items discounts.
     * These are those discounts which are applied when items are bought together.
     * To be moved to db @ActiveDiscount entity
     **/
    @Bean @Qualifier("ActiveBundledItemsDiscounts")
    public Map<DiscountCode, GroupedItemsDiscount> activeBundledItemsDiscounts() {
        Map<DiscountCode, GroupedItemsDiscount> groupedItemsDiscountMap = new HashMap<>();
        groupedItemsDiscountMap.put(DiscountCode.BURGER_FRY_WITH_DRINK_MEAL, new GroupedItemsDiscount(
                new HashSet(Arrays.asList(ItemType.BURGER, ItemType.FRIES, ItemType.DRINK)), 2.0f));
        groupedItemsDiscountMap.put(DiscountCode.BURGER_FRY_DRINK_WITH_DESSERT_MEAL, new GroupedItemsDiscount(
                new HashSet(Arrays.asList(ItemType.BURGER, ItemType.FRIES, ItemType.DRINK, ItemType.DESSERT)), 3.0f));

        return groupedItemsDiscountMap;
    }

    /**
     * Stores discounts on total
     *
     * @return
     */
    @Bean @Qualifier("ActiveDiscountOnTotal") public Map<DiscountCode, DiscountOnTotal> activeDiscountsOnTotal() {
        Map<DiscountCode, DiscountOnTotal> discountOnTotatMap = new HashMap<>();
        discountOnTotatMap.put(DiscountCode.TOTAL_MORE_THAN_30AUD, new DiscountOnTotal(10, 30.0f));
        return discountOnTotatMap;
    }
}
