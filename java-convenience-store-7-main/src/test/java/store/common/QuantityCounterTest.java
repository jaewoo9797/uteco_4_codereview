package store.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityCounterTest {

    @ParameterizedTest(name = "재고 {0}개")
    @ValueSource(ints = {0, 1, 2})
    @DisplayName("재고객체생성_생성된재고검사_재고개수동일")
    void givenCreateQuantityCounter_whenCheckQuantity_thenCorrect(int givenQuantity) {
        //given, when
        QuantityCounter quantityCounter = new QuantityCounter(givenQuantity);
        int createdQuantity = quantityCounter.getQuantity();

        //then
        assertEquals(givenQuantity, createdQuantity);
    }

    @Test
    @DisplayName("재고객체_생성시0미만_재고부족에러")
    void givenCreateQuantityUnder_thenThrowException() {
        //given
        int quantity = -1;

        //when, then
        assertThrows(IllegalArgumentException.class, () -> new QuantityCounter(quantity));
    }

    @Test
    @DisplayName("생성된재고_주문수량재고감소_주문수량만큼재고감소검사")
    void givenCreateQuantity_whenDecreaseQuantity_thenCorrectQuantity() {
        //given
        int createdQuantity = 10;
        QuantityCounter quantityCounter = new QuantityCounter(createdQuantity);

        //when
        int decreaseQuantity = 5;
        quantityCounter.decreaseQuantity(decreaseQuantity);

        //then
        int resultQuantity = createdQuantity - decreaseQuantity;
        assertEquals(resultQuantity, quantityCounter.getQuantity());
    }

    @Test
    @DisplayName("재고생성_재고수량보다_많은_주문수량_재고부족에러")
    void givenCreateQuantity_whenDecreaseQuantityMoreThanStoredQuantity_thenThrowException() {
        //given
        int quantity = 10;
        int decreaseQuantity = 11;
        QuantityCounter quantityCounter = new QuantityCounter(quantity);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> quantityCounter.decreaseQuantity(decreaseQuantity));
    }

    @Test
    @DisplayName("재고생성_재고수량증가_추가된재고만큼수량동일")
    void givenCreateQuantity_whenIncreaseQuantity_thenCorrectQuantity() {
        //given
        int quantity = 10;
        QuantityCounter increaseQuantity = new QuantityCounter(10);
        QuantityCounter quantityCounter = new QuantityCounter(quantity);

        //when
        quantityCounter.increaseQuantity(increaseQuantity);

        //then
        int resultQuantity = quantity + increaseQuantity.getQuantity();
        assertEquals(resultQuantity, quantityCounter.getQuantity());
    }

    @ParameterizedTest(name = "재고 10개와 재고 {0}개 : 재고의 합 계산")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    @DisplayName("기존 재고의 개수와 특정 개수의 합을 계산")
    void givenCreateQuantityCounter_whenPlusQuantityCounter_thenCorrectResultQuantityCounter(int givenQuantity) {
        //given
        int initialQuantity = 10;
        QuantityCounter quantityCounter = new QuantityCounter(initialQuantity);
        QuantityCounter plusQuantityCounter = new QuantityCounter(givenQuantity);

        //when, then
        assertEquals(initialQuantity + givenQuantity, quantityCounter.calculatePlusQuantityCount(plusQuantityCounter));
    }

    @ParameterizedTest(name = "재고 10개와 재고 {0}개 : 재고의 차 계산")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    @DisplayName("기존 재고의 개수와 특정 개수의 차를 계산")
    void givenCreateQuantityCounter_whenMinusQuantityCounter_thenCorrectResultQuantityCounter(int givenQuantity) {
        //given
        int initialQuantity = 10;
        QuantityCounter quantityCounter = new QuantityCounter(initialQuantity);
        QuantityCounter minusQuantityCounter = new QuantityCounter(givenQuantity);

        //when, then
        assertEquals(initialQuantity - givenQuantity, quantityCounter.calculateMinusQuantityCount(minusQuantityCounter));
    }

    @ParameterizedTest(name = "재고 5개와 재고 {0}개와 기존 재고의 차")
    @ValueSource(ints = {6, 7, 8, 9, 10})
    @DisplayName("기존 재고의 개수와 특정 개수의 차를 계산")
    void givenCreateQuantityCounter_whenMoreThanMinusQuantityCounter_thenThrowError(int givenQuantity) {
        //given
        int initialQuantity = 5;
        QuantityCounter quantityCounter = new QuantityCounter(initialQuantity);
        QuantityCounter minusQuantityCounter = new QuantityCounter(givenQuantity);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> quantityCounter.calculateMinusQuantityCount(minusQuantityCounter));
    }
}
