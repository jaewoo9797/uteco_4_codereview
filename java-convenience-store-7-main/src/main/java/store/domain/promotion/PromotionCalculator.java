package store.domain.promotion;

public interface PromotionCalculator {

    int calculateNumberOfEventProductQuantity(int orderCount, int buy, int get);

    boolean inspectionAdditionalNumberOfEventProductForFree(int orderCount, int buy, int get);
}
