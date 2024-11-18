package store.domain.product;

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
        nonPromotionQuantity.increaseQuantity(quantity);
    }

    public void reduceOrderQuantity(QuantityCounter orderQuantity) {
        inspectionOrderCount(orderQuantity);
        if (orderQuantity.getQuantity() > promotionQuantity.getQuantity()) {
            int remainOrderQuantity = orderQuantity.calculateMinusQuantityCount(promotionQuantity);
            promotionQuantity.decreaseQuantity(promotionQuantity.getQuantity());
            nonPromotionQuantity.decreaseQuantity(remainOrderQuantity);
            return;
        }
        promotionQuantity.decreaseQuantity(orderQuantity.getQuantity());
    }

    public void inspectionOrderCount(QuantityCounter quantity) {
        if (totalProductQuantity() < quantity.getQuantity()) {
            throw new IllegalArgumentException(
                    ErrorMessage.INSUFFICIENT_INVENTORY_NUMBER_PURCHASED.getMessage());
        }
    }

    private int totalProductQuantity() {
        return promotionQuantity.calculatePlusQuantityCount(nonPromotionQuantity);
    }

    public int getPromotionQuantity() {
        return promotionQuantity.getQuantity();
    }

    public int getNonPromotionQuantity() {
        return nonPromotionQuantity.getQuantity();
    }
}
