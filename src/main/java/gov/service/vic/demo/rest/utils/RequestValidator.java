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
import java.util.function.BiPredicate;

@Service public class RequestValidator {

    @Qualifier("ActiveBundledItemsDiscounts")
    private Map<DiscountCode, GroupedItemsDiscount> activeBundledItemsDisc;

    @Qualifier("ActiveDiscountOnTotal")
    private Map<DiscountCode, DiscountOnTotal> activeDiscOnTotal;

    BiPredicate<Item, ItemType> itemTypeMatcher = (item, itemType) -> itemType.equals(
            ItemType.valueOf(item.getItemType()));

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

        int friesCount = orderRequest.getItems().stream().filter(item -> itemTypeMatcher.test(item, ItemType.FRIES))
                .mapToInt(Item::getQuantity).sum();

        int burgerCount = orderRequest.getItems().stream().filter(item -> itemTypeMatcher.test(item, ItemType.BURGER))
                .mapToInt(Item::getQuantity).sum();

        int dessertCount = orderRequest.getItems().stream().filter(item -> itemTypeMatcher.test(item, ItemType.DESSERT))
                .mapToInt(Item::getQuantity).sum();

        int drinksCount = orderRequest.getItems().stream().filter(item -> itemTypeMatcher.test(item, ItemType.DRINK))
                .mapToInt(Item::getQuantity).sum();

        float discountAmount = 0.0f;

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
            AppliedDiscount appliedDiscount = AppliedDiscount.builder()
                    .discountValue(
                            activeBundledItemsDisc.get(DiscountCode.BURGER_FRY_WITH_DRINK_MEAL).getDiscountValue())
                    .quantity(countForOfferBurgerFriesWithDrink)
                    .discountCode(DiscountCode.BURGER_FRY_WITH_DRINK_MEAL.toString()).desc("Burger fries with drink")
                    .build();
            actualAppliedDiscountList.add(appliedDiscount);
            discountAmount += (appliedDiscount.getDiscountValue() * countForOfferBurgerFriesWithDrink);
        }

        if (countForOfferBurgerFriesDrinkWithDessert > 0) {
            AppliedDiscount appliedDiscount = AppliedDiscount.builder().discountValue(
                            activeBundledItemsDisc.get(DiscountCode.BURGER_FRY_DRINK_WITH_DESSERT_MEAL).getDiscountValue())
                    .quantity(countForOfferBurgerFriesDrinkWithDessert)
                    .discountCode(DiscountCode.BURGER_FRY_DRINK_WITH_DESSERT_MEAL.toString())
                    .desc("Burger fry drink with dessert").build();

            actualAppliedDiscountList.add(appliedDiscount);
            discountAmount += (appliedDiscount.getDiscountValue() * countForOfferBurgerFriesDrinkWithDessert);
        }

        float totalAfterApplyingDiscounts = orderRequest.getAmountBeforeDiscounts() - discountAmount;
        orderRequest.setDiscounts(actualAppliedDiscountList);
        orderRequest = updateRequestWithDiscountOnTotal(totalAfterApplyingDiscounts, orderRequest);
        return orderRequest;
    }

    private OrderRequest updateRequestWithDiscountOnTotal(float totalAmountAfterOtherDiscounts,
                                                          OrderRequest orderRequest) {
        float finalAmount = totalAmountAfterOtherDiscounts;
        List<AppliedDiscount> appliedDiscountsOnTotal = new ArrayList<>();
        for (Map.Entry<DiscountCode, DiscountOnTotal> discountCodeDiscountOnTotalEntry : activeDiscOnTotal.entrySet()) {
            if (totalAmountAfterOtherDiscounts > discountCodeDiscountOnTotalEntry.getValue().getSpendingLimit()) {
                AppliedDiscount appliedDiscount = AppliedDiscount.builder()
                        .discountValue(discountCodeDiscountOnTotalEntry.getValue().getDiscPercentage()).quantity(1)
                        .discountCode(discountCodeDiscountOnTotalEntry.getKey().toString())
                        .desc("Total more than 30$").build();

                finalAmount = calculateAmountAfterPercentageDiscount(finalAmount,
                                                                     discountCodeDiscountOnTotalEntry.getValue()
                                                                             .getDiscPercentage());
                appliedDiscountsOnTotal.add(appliedDiscount);
            }
            orderRequest.getDiscounts().addAll(appliedDiscountsOnTotal);
            orderRequest.setAmount(finalAmount);
        }
        return orderRequest;
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
