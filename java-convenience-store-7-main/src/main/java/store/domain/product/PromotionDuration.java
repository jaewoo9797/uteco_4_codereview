package store.domain.product;

import java.time.LocalDate;

public class PromotionDuration {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public PromotionDuration(LocalDate startDate, LocalDate endDate) {
        validatePromotionDuration(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isWithinPromotionPeriod(LocalDate orderDate) {
        return !orderDate.isBefore(startDate) && !orderDate.isAfter(endDate);
    }

    private void validatePromotionDuration(LocalDate promotionStartDate, LocalDate promotionEndDate) {
        if (promotionEndDate.isBefore(promotionStartDate)) {
            throw new IllegalArgumentException();
        }
    }
}
