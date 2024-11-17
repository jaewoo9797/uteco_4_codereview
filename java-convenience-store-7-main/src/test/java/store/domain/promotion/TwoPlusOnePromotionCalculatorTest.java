package store.domain.promotion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TwoPlusOnePromotionCalculatorTest {

    PromotionCalculator promotionCalculator = new TwoPlusOnePromotionCalculator();
    int buy = 2;
    int get = 1;

    @ParameterizedTest(name = "주문 수량 {0}개 : 추가 제공 상품 {1}개 ")
    @CsvSource({
            "1, 0",
            "2, 0",
            "3, 1",
            "4, 1",
            "5, 1",
            "6, 2",
            "7, 2",
            "8, 2",
            "9, 3",
            "10, 3"
    })
    @DisplayName("상품개수에 따른 프로모션 상품 제공 개수")
    void givenOrderQuantity_whenCalculateEventProductQuantity_thenCorrect(int orderQuantity, int bonusProductQuantity) {
        int bonusCount =
                promotionCalculator.calculateNumberOfEventProductQuantity(orderQuantity, buy, get);
        assertEquals(bonusProductQuantity, bonusCount);
    }

    @ParameterizedTest(name = "주문한 상품 개수 {0} : 추가로 상품을 무료로 제공 받을 수 있는지 여부  {1}")
    @CsvSource({
            "1, false",
            "2, true",
            "3, false",
            "4, false",
            "5, true",
            "6, false",
            "7, false",
            "8, true",
            "9, false",
            "10, false"
    })
    @DisplayName("제공되는 프로모션에 맞게 추가로 제공받을 수 있는지 여부")
    void givenOrderQuantity_whenInspectionNumberOfEventProduct_thenCheckCorrectQuantity(int orderQuantity,
                                                                                        boolean isCorrect) {
        boolean isCanAdditionalProduct = promotionCalculator.inspectionAdditionalNumberOfEventProductForFree(
                orderQuantity, buy, get);
        assertEquals(isCorrect, isCanAdditionalProduct);
    }
}