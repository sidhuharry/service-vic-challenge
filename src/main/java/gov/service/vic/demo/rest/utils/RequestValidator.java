package gov.service.vic.demo.rest.utils;

import gov.service.vic.demo.db.hardcoded.DiscountCode;
import gov.service.vic.demo.db.hardcoded.DiscountOnTotal;
import gov.service.vic.demo.db.hardcoded.GroupedItemsDiscount;
import gov.service.vic.demo.rest.exception.InvalidRequestException;
import gov.service.vic.demo.rest.model.AppliedDiscount;
import gov.service.vic.demo.rest.model.Item;
import gov.service.vic.demo.rest.model.ItemType;
import gov.service.vic.demo.rest.model.OrderRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service public class RequestValidator {

    @Qualifier("ActiveBundledItemsDiscounts")
    private Map<DiscountCode, GroupedItemsDiscount> activeBundledItemsDisc;

    @Qualifier("ActiveDiscountOnTotal")
    private Map<DiscountCode, DiscountOnTotal> activeDiscOnTotal;

    public RequestValidator(Map<DiscountCode, GroupedItemsDiscount> activeBundledItemsDisc,
                            Map<DiscountCode, DiscountOnTotal> activeDiscOnTotal) {
        this.activeBundledItemsDisc = activeBundledItemsDisc;
        this.activeDiscOnTotal = activeDiscOnTotal;
    }

    public OrderRequest validateNewOrderRequest(OrderRequest orderRequest) throws InvalidRequestException {
        validateNewOrderRequestFields(orderRequest);
        return sanitizeDiscountsApplied(orderRequest);
    }

    /**
     * Check if the applied discounts match the active discounts. If not, remove the non-applicable discounts.
     */
    private OrderRequest sanitizeDiscountsApplied(OrderRequest orderRequest) {
        List<AppliedDiscount> invalidAppliedDiscount = new ArrayList<>();
        List<AppliedDiscount> appliedDiscountList = orderRequest.getDiscounts();
        int friesCount = 0;
        int burgerCount = 0;
        int dessertCount = 0;
        int drinksCount = 0;

        float discountAmount = 0.0f;

        for (Item item : orderRequest.getItems()) {
            if (ItemType.FRIES.equals(ItemType.valueOf(item.getItemType()))) {
                friesCount++;
            }
            if (ItemType.BURGER.equals(ItemType.valueOf(item.getItemType()))) {
                burgerCount++;
            }
            if (ItemType.DESSERT.equals(ItemType.valueOf(item.getItemType()))) {
                dessertCount++;
            }
            if (ItemType.DRINK.equals(ItemType.valueOf(item.getItemType()))) {
                drinksCount++;
            }
        }

        // unique number of each items and smallest of those will be what discount if applicable
        int countForOfferDrinksFriesWithDrink = Collections.min(new HashSet<>(
                Arrays.asList(burgerCount, friesCount, drinksCount)));

        burgerCount = burgerCount - countForOfferDrinksFriesWithDrink;

        friesCount = friesCount - countForOfferDrinksFriesWithDrink;

        drinksCount = drinksCount - countForOfferDrinksFriesWithDrink;

        int countForOfferDrinksFriesDrinkWithDessert = Collections.min(new HashSet<>(
                Arrays.asList(burgerCount, friesCount, drinksCount, dessertCount)));

        List<AppliedDiscount> actualAppliedDiscountList = new ArrayList<>();
        if (countForOfferDrinksFriesWithDrink > 0) {
            AppliedDiscount appliedDiscount = new AppliedDiscount();
            appliedDiscount.setDiscountValue(
                    activeBundledItemsDisc.get(DiscountCode.BURGER_FRY_WITH_DRINK_MEAL).getDiscountValue());
            appliedDiscount.setQuantity(countForOfferDrinksFriesWithDrink);
            appliedDiscount.setDesc("Burger fry with drink");
            actualAppliedDiscountList.add(appliedDiscount);
            discountAmount += appliedDiscount.getDiscountValue();
        }

        if (countForOfferDrinksFriesDrinkWithDessert > 0) {
            AppliedDiscount appliedDiscount = new AppliedDiscount();
            appliedDiscount.setDiscountValue(
                    activeBundledItemsDisc.get(DiscountCode.BURGER_FRY_DRINK_WITH_DESSERT_MEAL).getDiscountValue());
            appliedDiscount.setQuantity(countForOfferDrinksFriesDrinkWithDessert);
            appliedDiscount.setDesc("Burger fry drink with dessert");
            actualAppliedDiscountList.add(appliedDiscount);
            discountAmount += appliedDiscount.getDiscountValue();
        }

        float totalAfterApplyingDiscounts = orderRequest.getAmountBeforeDiscounts() - discountAmount;

        orderRequest.setAmount(totalAfterApplyingDiscounts);
        orderRequest.setDiscounts(actualAppliedDiscountList);
        return orderRequest;
    }

    private void validateNewOrderRequestFields(OrderRequest orderRequest) {
        if (Objects.isNull(orderRequest.getCustomer())) {
            throw new InvalidRequestException("Customer can't be null. Who's the order for?");
        }
        if (Objects.isNull(orderRequest.getCustomer().getCustomerId()) ||
                orderRequest.getCustomer().getCustomerId().isEmpty()) {
            throw new InvalidRequestException("Customer Id can't be null/empty");
        }

        if (Objects.isNull(orderRequest.getItems()) || orderRequest.getItems().isEmpty()) {
            throw new InvalidRequestException("Item list is empty. There can't be an order without items.");
        }
    }

    public void validateUpdateOrderRequestFields(OrderRequest orderRequest) throws InvalidRequestException {
        validateNewOrderRequest(orderRequest);
        if (orderRequest.getOrderId() == null) {
            throw new InvalidRequestException(
                    "Order Id is missing, you need to tell me which order am I supposed to update?");
        }
    }
}
