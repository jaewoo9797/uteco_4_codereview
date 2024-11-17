package store.domain.product;

import store.common.ErrorMessage;
import store.domain.promotion.PromotionCalculator;

public class PromotionTypeInfo {
    private final String name;
    private final int buy;
    private final int get;
    private final PromotionCalculator promotionCalculator;

    public PromotionTypeInfo(String name, int buy, int get, PromotionCalculator promotionCalculator) {
        validateInfo();
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.promotionCalculator = promotionCalculator;
    }

    private void validateInfo() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.PROMOTION_TYPE_INFO_SHOULD_NOT_NULL.getMessage());
        }
    }

    public int calculateNumberOfProduct(int orderCount) {
        return promotionCalculator.calculateNumberOfEventProductQuantity(orderCount, buy, get);
    }

    public boolean calculateNumberOfEventProductQuantity(int orderCount) {
        return promotionCalculator.inspectionAdditionalNumberOfEventProductForFree(orderCount, buy, get);
    }
}
