package store.domain.product;

import java.time.LocalDate;

public class PromotionType {
    private final PromotionTypeInfo promotionTypeInfo;
    private final PromotionDuration promotionDuration;

    public PromotionType(PromotionTypeInfo promotionTypeInfo, PromotionDuration promotionDuration) {
        this.promotionTypeInfo = promotionTypeInfo;
        this.promotionDuration = promotionDuration;
    }

    public boolean isWithinPromotionPeriod(LocalDate orderDate) {
        return promotionDuration.isWithinPromotionPeriod(orderDate);
    }
}
