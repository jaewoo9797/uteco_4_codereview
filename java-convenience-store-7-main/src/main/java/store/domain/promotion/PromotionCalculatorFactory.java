package store.domain.promotion;

public class PromotionCalculatorFactory {

    public static PromotionCalculator getPromotionCalculator(int buy, int get) {
        if (buy + get == 3) {
            return new TwoPlusOnePromotionCalculator();
        }
        if (buy + get == 2) {
            return new OnePlusOnePromotionCalculator();
        }

        return new NonPromotionCalculator();
    }
}
