package store.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.common.ErrorMessage;
import store.common.QuantityCounter;

class QuantityTest {

    private QuantityCounter promotionQuantityCounter;
    private QuantityCounter nonPromotionQuantityCounter;
    private Quantity quantity;

    @BeforeEach
    void init() {
        promotionQuantityCounter = new QuantityCounter(10);
        nonPromotionQuantityCounter = new QuantityCounter(10);
        quantity = new Quantity(promotionQuantityCounter, nonPromotionQuantityCounter);
    }

    @Test
    @DisplayName("재고 객체 생성 시 초기값 검증")
    void shouldCreateQuantityWithGivenCounters() {
        assertAll(
                () -> assertEquals(10, quantity.getPromotionQuantity(), "프로모션 재고가 일치하지 않습니다."),
                () -> assertEquals(10, quantity.getNonPromotionQuantity(), "논 프로모션 재고가 일치하지 않습니다.")
        );
    }

    @Test
    @DisplayName("논 프로모션 재고만 생성 시, 프로모션 재고는 0이어야 함")
    void shouldInitializePromotionQuantityToZeroWhenOnlyNonPromotionQuantityIsGiven() {
        Quantity nonPromotionOnlyQuantity = new Quantity(nonPromotionQuantityCounter);
        assertEquals(0, nonPromotionOnlyQuantity.getPromotionQuantity(), "프로모션 재고가 0이 아닙니다.");
    }

    @Test
    @DisplayName("논 프로모션 재고 추가 시, 논 프로모션 재고 증가")
    void shouldIncreaseNonPromotionQuantityWhenAdded() {
        int initialNonPromotionQuantity = quantity.getNonPromotionQuantity();
        int addedQuantity = 5;
        QuantityCounter addQuantityCounter = new QuantityCounter(addedQuantity);

        quantity.addNonPromotionQuantity(addQuantityCounter);

        assertEquals(initialNonPromotionQuantity + addedQuantity, quantity.getNonPromotionQuantity(),
                "논 프로모션 재고가 올바르게 증가하지 않았습니다.");
    }

    @ParameterizedTest(name = "프로모션 재고 감소 테스트: {0}개 감소")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("프로모션 재고 감소 테스트")
    void shouldReducePromotionQuantity(int decreaseQuantity) {
        int initialPromotionQuantity = quantity.getPromotionQuantity();

        quantity.reduceOrderQuantity(decreaseQuantity);

        assertEquals(initialPromotionQuantity - decreaseQuantity, quantity.getPromotionQuantity(),
                "프로모션 재고가 올바르게 감소하지 않았습니다.");
    }

    @ParameterizedTest(name = "프로모션 재고 초과 시 논 프로모션 재고 감소 테스트 : {0}개 감소")
    @ValueSource(ints = {11, 12, 13, 14, 15, 20})
    @DisplayName("프로모션 개수 초과 시 논 프로모션 재고도 감소")
    void givenQuantityWhenDecreaseOverPromotionQuantityThenNonPromotionQuantityDecreaseQuantity(int decreaseQuantity) {
        //given
        int initialPromotionQuantity = quantity.getPromotionQuantity();
        int initialNonPromotionQuantity = quantity.getNonPromotionQuantity();

        //when
        quantity.reduceOrderQuantity(decreaseQuantity);

        //then
        assertAll(
                () -> assertEquals(0, quantity.getPromotionQuantity(), "프로모션 재고가 올바르게 감소되지 않았습니다."),
                () -> assertEquals(initialNonPromotionQuantity - (decreaseQuantity - initialPromotionQuantity),
                        quantity.getNonPromotionQuantity(), "논 프로모션 재고가 올바르게 감소되지 않았습니다.")
        );
    }

    @ParameterizedTest(name = "총 재고 부족 시 예외 발생 테스트 : {0}개 주문")
    @ValueSource(ints = {21, 22, 23, 24, 25})
    @DisplayName("총 재고 부족 시 예외 발생")
    void givenQuantityWhenDecreaseOverTotalQuantityThenThrowError(int decreaseQuantity) {
        var illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> quantity.reduceOrderQuantity(decreaseQuantity), "재고가 부족할 때 예외가 발생하지 않았습니다.");

        assertEquals(ErrorMessage.INSUFFICIENT_INVENTORY_NUMBER_PURCHASED.getMessage(), illegalArgumentException.getMessage());
    }
}
