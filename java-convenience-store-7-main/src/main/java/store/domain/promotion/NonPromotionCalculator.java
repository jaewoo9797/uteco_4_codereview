package store.domain.promotion;

public class NonPromotionCalculator implements PromotionCalculator {
    @Override
    public int calculateNumberOfEventProductQuantity(int orderCount, int buy, int get) {
        return 0;
    }

    @Override
    public boolean inspectionNumberOfEventProduct(int orderCount, int buy, int get) {
        return false;
    }
}
