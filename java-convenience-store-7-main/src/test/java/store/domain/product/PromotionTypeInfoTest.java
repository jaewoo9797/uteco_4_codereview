package store.domain.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import store.common.QuantityCounter;
import store.domain.promotion.PromotionCalculator;
import store.domain.promotion.TwoPlusOnePromotionCalculator;

class PromotionTypeInfoTest {
    private static final String NAME = "MD추천";
    private static final QuantityCounter BUY = new QuantityCounter(2);
    private static final QuantityCounter GET = new QuantityCounter(1);
    private PromotionCalculator promotionCalculator;
    private PromotionTypeInfo promotionTypeInfo;

    @BeforeEach
    void setUp() {
        promotionCalculator = new TwoPlusOnePromotionCalculator();
        promotionTypeInfo = new PromotionTypeInfo(NAME, BUY, GET, promotionCalculator);
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름이 null 또는 빈 문자열일 때 객체 생성 시 에러 발생")
    void givenNameIsNullOrEmpty_whenCreatedPromotionTypeInfo_thenThrowException(String promotionTypeName) {
        // when, then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PromotionTypeInfo(promotionTypeName,BUY,GET,promotionCalculator),
                "이름이 null 또는 빈 문자열일 때 에러가 발생하지 않았습니다.");
    }

    // 프로모션에 따른 계산된 결과를 테스트
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
    void givenPromotionTypeInfo_whenCalculateNumberOfCount_thenCorrect(int orderCount, int bonusQuantity) {
        // when
        int bonus = promotionTypeInfo.calculateNumberOfProduct(new QuantityCounter(orderCount));
        
        //then
        Assertions.assertEquals(bonusQuantity, bonus);
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
    void givenPromotionTypeInfo_whenInspectAdditionalNumberOfEventProductFroFree_thenCheckCorrect(int orderCount, boolean isForFree) {
        boolean isCanAdditionalProductForFree = promotionTypeInfo.inspectAdditionalNumberOfEventProductFroFree(new QuantityCounter(orderCount));
        Assertions.assertEquals(isForFree, isCanAdditionalProductForFree);
    }
}
