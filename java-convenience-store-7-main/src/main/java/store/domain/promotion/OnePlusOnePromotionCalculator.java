package store.domain.promotion;

public class OnePlusOnePromotionCalculator implements PromotionCalculator {
    @Override
    public int calculateNumberOfEventProductQuantity(int orderCount, int buy, int get) {
        return orderCount / (buy + get) * get;
    }

    @Override
    public boolean inspectionAdditionalNumberOfEventProductForFree(int orderCount, int buy, int get) {
        return orderCount % (buy + get) == 1;
    }

}
