package store.domain.product;

import store.common.ErrorMessage;
import store.common.QuantityCounter;
import store.domain.promotion.PromotionCalculator;

public class PromotionTypeInfo {
    private final String name;
    private final QuantityCounter buy;
    private final QuantityCounter get;
    private final PromotionCalculator promotionCalculator;

    public PromotionTypeInfo(String name, QuantityCounter buy, QuantityCounter get,
                             PromotionCalculator promotionCalculator) {
        validateInfo(name);
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.promotionCalculator = promotionCalculator;
    }

    private void validateInfo(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.PROMOTION_TYPE_INFO_SHOULD_NOT_NULL.getMessage());
        }
    }

    public int calculateNumberOfProduct(QuantityCounter orderCount) {
        return promotionCalculator.calculateNumberOfEventProductQuantity(orderCount.getQuantity(), buy.getQuantity(),
                get.getQuantity());
    }

    public boolean inspectAdditionalNumberOfEventProductFroFree(QuantityCounter orderCount) {
        return promotionCalculator.inspectionAdditionalNumberOfEventProductForFree(orderCount.getQuantity(),
                buy.getQuantity(), get.getQuantity());
    }

    public String getName() {
        return name;
    }

    public int getPromotionBuyGet() {
        return buy.calculatePlusQuantityCount(get);
    }

    public boolean nameEqualsNull(String name) {
        return name.equals("null");
    }
}
