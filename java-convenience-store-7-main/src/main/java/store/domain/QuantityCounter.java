package store.domain;

public class QuantityCounter {
    private int promotionQuantity;
    private int nonPromotionQuantity;

    public QuantityCounter(int promotionQuantity, int nonPromotionQuantity) {
        this.promotionQuantity = promotionQuantity;
        this.nonPromotionQuantity = nonPromotionQuantity;
    }

    public QuantityCounter(int promotionQuantity) {
        this(promotionQuantity, 0);
    }

    public void reduceOrderQuantity(int orderQuantity) {
        if (orderQuantity > promotionQuantity) {
            int remainOrderQuantity = orderQuantity - promotionQuantity;
            promotionQuantity = 0;
            nonPromotionQuantity -= remainOrderQuantity;
            return;
        }
        promotionQuantity -= orderQuantity;
    }

    public void inspectionOrderCount(int quantity) {
        if (promotionQuantity + nonPromotionQuantity < quantity) {
            throw new IllegalArgumentException();
        }
    }
}
