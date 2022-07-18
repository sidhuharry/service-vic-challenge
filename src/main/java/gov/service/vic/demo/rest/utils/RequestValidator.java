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
        Set<Integer> countOfAllItems = new HashSet<>(
                Arrays.asList(burgerCount, friesCount, dessertCount, drinksCount));

        int

        for (AppliedDiscount appliedDiscount : appliedDiscountList) {
            DiscountCode appliedDiscountCode = DiscountCode.valueOf(appliedDiscount.getDiscountCode());
            if (activeBundledItemsDisc.containsKey(appliedDiscountCode)) {
                GroupedItemsDiscount groupedItemsDiscount = activeBundledItemsDisc.get(appliedDiscountCode);
                if (appliedDiscount.getDiscountValue() != groupedItemsDiscount.getDiscountValue()) {
                    invalidAppliedDiscount.add(appliedDiscount);
                }
            } else if (activeDiscOnTotal.containsKey(appliedDiscountCode)) {
                DiscountOnTotal discountOnTotal = activeDiscOnTotal.get(appliedDiscountCode);
                if (discountOnTotal.getSpendingLimit() < orderRequest.getAmount()) {
                    invalidAppliedDiscount.add(appliedDiscount);
                }
            } else {
                // discount code is invalid.
                invalidAppliedDiscount.add(appliedDiscount);
            }
        }

        appliedDiscountList.removeAll(invalidAppliedDiscount);
        orderRequest.setDiscounts(appliedDiscountList);

        orderRequest.setAmount(getFinalAmount(orderRequest));
        return orderRequest;
    }

    private float getFinalAmount(OrderRequest orderRequest) {
        float amountBeforeOffers = 0.0f;
        float discountAmount = 0.0f;
        for (Item item : orderRequest.getItems()) {
            amountBeforeOffers = amountBeforeOffers + (item.getPrice() * item.getQuantity());
        }
        for (AppliedDiscount discount : orderRequest.getDiscounts()) {
            discountAmount = discountAmount + (discount.getDiscountValue() * discount.getQuantity());
        }
        return amountBeforeOffers - discountAmount;
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
