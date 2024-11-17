package store.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.common.QuantityCounter;

class QuantityCounterTest {
//
//    private final QuantityCounter promotionQuantityCounter;
//    private final QuantityCounter nonPromotionQuantityCounter;



    @ParameterizedTest(name = "프로모션 개수{0}로 초기화 시, 초기값 검증")
    @ValueSource(ints = {1, 2, 3, 4})
    @DisplayName("객체_생성_초기값_프로모션_개수_검사_동일테스트")
    void givenCreateCounter_whenInspectionCount_thenEqual(int count) {
        //given
        QuantityCounter counter = new QuantityCounter(count);
    }


}
