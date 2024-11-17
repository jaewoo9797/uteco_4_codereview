package store.domain;

import store.common.ErrorMessage;
import store.common.QuantityCounter;

public class Quantity {

    private final QuantityCounter promotionQuantity;
    private final QuantityCounter nonPromotionQuantity;

    public Quantity(QuantityCounter promotionQuantity, QuantityCounter nonPromotionQuantity) {
        this.promotionQuantity = promotionQuantity;
        this.nonPromotionQuantity = nonPromotionQuantity;
    }

    public Quantity(QuantityCounter nonPromotionQuantity) {
        this(new QuantityCounter(0), nonPromotionQuantity);
    }

    public void addNonPromotionQuantity(QuantityCounter quantity) {
        nonPromotionQuantity.increaseQuantity(quantity.getQuantity());
    }

    public void reduceOrderQuantity(int orderQuantity) {
        inspectionOrderCount(orderQuantity);
        if (orderQuantity > promotionQuantity.getQuantity()) {
            int remainOrderQuantity = orderQuantity - promotionQuantity.getQuantity();
            promotionQuantity.decreaseQuantity(promotionQuantity.getQuantity());
            nonPromotionQuantity.decreaseQuantity(remainOrderQuantity);
            return;
        }
        promotionQuantity.decreaseQuantity(orderQuantity);
    }

    public void inspectionOrderCount(int quantity) {
        if (totalProductQuantity() < quantity) {
            throw new IllegalArgumentException(
                    ErrorMessage.INSUFFICIENT_INVENTORY_NUMBER_PURCHASED.getMessage());
        }
    }

    private int totalProductQuantity() {
        return promotionQuantity.getQuantity() + nonPromotionQuantity.getQuantity();
    }

    public int getPromotionQuantity() {
        return promotionQuantity.getQuantity();
    }

    public int getNonPromotionQuantity() {
        return nonPromotionQuantity.getQuantity();
    }
}
