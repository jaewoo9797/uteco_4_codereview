package store.domain.promotion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NonPromotionCalculatorTest {

    PromotionCalculator promotionCalculator = new NonPromotionCalculator();
    int buy = 0;
    int get = 0;

    @ParameterizedTest(name = "주문 수량 {0}개 : 추가 제공 상품 {1}개 ")
    @CsvSource({
            "1, 0",
            "2, 0",
            "3, 0",
            "4, 0",
            "5, 0",
            "6, 0",
            "7, 0",
            "8, 0",
            "9, 0",
            "10, 0"
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
            "2, false",
            "3, false",
            "4, false",
            "5, false",
            "6, false",
            "7, false",
            "8, false",
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