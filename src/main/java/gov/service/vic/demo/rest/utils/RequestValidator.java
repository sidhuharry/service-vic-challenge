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
                friesCount += item.getQuantity();
            }
            if (ItemType.BURGER.equals(ItemType.valueOf(item.getItemType()))) {
                burgerCount += item.getQuantity();
            }
            if (ItemType.DESSERT.equals(ItemType.valueOf(item.getItemType()))) {
                dessertCount += item.getQuantity();
            }
            if (ItemType.DRINK.equals(ItemType.valueOf(item.getItemType()))) {
                drinksCount += item.getQuantity();
            }
        }

        // unique number of each items and smallest of those will be what discount if applicable
        int countForOfferBurgerFriesDrinkWithDessert = Collections.min(new HashSet<>(
                Arrays.asList(burgerCount, friesCount, drinksCount, dessertCount)));

        burgerCount = burgerCount - countForOfferBurgerFriesDrinkWithDessert;

        friesCount = friesCount - countForOfferBurgerFriesDrinkWithDessert;

        drinksCount = drinksCount - countForOfferBurgerFriesDrinkWithDessert;

        dessertCount = dessertCount - countForOfferBurgerFriesDrinkWithDessert;

        int countForOfferBurgerFriesWithDrink = Collections.min(new HashSet<>(
                Arrays.asList(burgerCount, friesCount, drinksCount)));

        List<AppliedDiscount> actualAppliedDiscountList = new ArrayList<>();

        if (countForOfferBurgerFriesWithDrink > 0) {
            AppliedDiscount appliedDiscount = new AppliedDiscount();
            appliedDiscount.setDiscountValue(
                    activeBundledItemsDisc.get(DiscountCode.BURGER_FRY_WITH_DRINK_MEAL).getDiscountValue());
            appliedDiscount.setQuantity(countForOfferBurgerFriesWithDrink);
            appliedDiscount.setDesc("Burger fry with drink");
            actualAppliedDiscountList.add(appliedDiscount);
            discountAmount += (appliedDiscount.getDiscountValue() * countForOfferBurgerFriesWithDrink);
        }

        if (countForOfferBurgerFriesDrinkWithDessert > 0) {
            AppliedDiscount appliedDiscount = new AppliedDiscount();
            appliedDiscount.setDiscountValue(
                    activeBundledItemsDisc.get(DiscountCode.BURGER_FRY_DRINK_WITH_DESSERT_MEAL).getDiscountValue());
            appliedDiscount.setQuantity(countForOfferBurgerFriesDrinkWithDessert);
            appliedDiscount.setDesc("Burger fry drink with dessert");
            actualAppliedDiscountList.add(appliedDiscount);
            discountAmount += (appliedDiscount.getDiscountValue() * countForOfferBurgerFriesDrinkWithDessert);
        }

        float totalAfterApplyingDiscounts = orderRequest.getAmountBeforeDiscounts() - discountAmount;
        float finalAmount = getDiscountOnTotal(totalAfterApplyingDiscounts);
        orderRequest.setAmount(finalAmount);
        orderRequest.setDiscounts(actualAppliedDiscountList);
        return orderRequest;
    }

    private float getDiscountOnTotal(float totalAmountAfterOtherDiscounts) {
        for (Map.Entry<DiscountCode, DiscountOnTotal> discountCodeDiscountOnTotalEntry : activeDiscOnTotal.entrySet()) {
            if (totalAmountAfterOtherDiscounts > discountCodeDiscountOnTotalEntry.getValue().getSpendingLimit()) {
                return calculateAmountAfterPercentageDiscount(totalAmountAfterOtherDiscounts,
                                                              discountCodeDiscountOnTotalEntry.getValue()
                                                                      .getDiscPercentage());
            }
        }
        return totalAmountAfterOtherDiscounts;
    }

    private float calculateAmountAfterPercentageDiscount(float amountBeforeDisc, int percentage) {
        float percentageInFloat = (float) percentage / 100;
        return amountBeforeDisc - (amountBeforeDisc * percentageInFloat);
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

        orderRequest.getItems().forEach(item -> {
            if (item.getItemType() == null || item.getPrice() == 0 || item.getItemName() == null) {
                throw new InvalidRequestException("Item fields are not set properly");
            }
        });


        orderRequest.getDiscounts().forEach(discount -> {
            if (discount.getDiscountCode() == null || discount.getDiscountValue() == 0) {
                throw new InvalidRequestException("Discount fields are not set properly");
            }
        });
    }

    public void validateUpdateOrderRequestFields(OrderRequest orderRequest) throws InvalidRequestException {
        validateNewOrderRequest(orderRequest);
        if (orderRequest.getOrderId() == null) {
            throw new InvalidRequestException(
                    "Order Id is missing, you need to tell me which order am I supposed to update?");
        }
    }
}
