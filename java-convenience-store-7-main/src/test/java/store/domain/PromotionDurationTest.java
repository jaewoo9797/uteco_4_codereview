package store.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionDurationTest {

    private PromotionDuration promotionDuration;

    @BeforeEach
    void init() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        promotionDuration = new PromotionDuration(startDate, endDate);
    }

    @Test
    @DisplayName("이벤트 기간 내의 날짜 검사 - 시작일 포함")
    void shouldReturnTrueWhenOrderDateIsOnStartDate() {
        LocalDate orderDate = LocalDate.of(2024, 1, 1);
        assertTrue(() -> promotionDuration.isWithinPromotionPeriod(orderDate));
    }

    @Test
    @DisplayName("이벤트 기간 내의 날짜 검사 - 종료일 포함")
    void shouldReturnTrueWhenOrderDateIsOnEndDate() {
        LocalDate orderDate = LocalDate.of(2024, 1, 10);
        assertTrue(promotionDuration.isWithinPromotionPeriod(orderDate), "종료일이 포함되지 않았습니다.");
    }

    @Test
    @DisplayName("이벤트 기간 내의 날짜 검사 - 중간 날짜")
    void shouldReturnTrueWhenOrderDateIsWithinPeriod() {
        LocalDate orderDate = LocalDate.of(2024, 1, 5);
        assertTrue(promotionDuration.isWithinPromotionPeriod(orderDate), "이벤트 기간 내의 날짜가 포함되지 않았습니다.");
    }

    @Test
    @DisplayName("이벤트 기간 외의 날짜 검사 - 시작일 이전")
    void shouldReturnFalseWhenOrderDateIsBeforeStartDate() {
        LocalDate orderDate = LocalDate.of(2023, 12, 31);
        assertFalse(promotionDuration.isWithinPromotionPeriod(orderDate), "시작일 이전의 날짜가 포함되었습니다.");
    }

    @Test
    @DisplayName("이벤트 기간 외의 날짜 검사 - 종료일 이후")
    void shouldReturnFalseWhenOrderDateIsAfterEndDate() {
        LocalDate orderDate = LocalDate.of(2024, 1, 11);
        assertFalse(promotionDuration.isWithinPromotionPeriod(orderDate), "종료일 이후의 날짜가 포함되었습니다.");
    }

    @Test
    @DisplayName("종료일이 시작일보다 이전일 때 예외 발생")
    void shouldThrowExceptionWhenEndDateIsBeforeStartDate() {
        LocalDate startDate = LocalDate.of(2024, 1, 10);
        LocalDate endDate = LocalDate.of(2024, 1, 1);

        assertThrows(
                IllegalArgumentException.class,
                () -> new PromotionDuration(startDate, endDate),
                "종료일이 시작일보다 이전일 때 예외가 발생하지 않았습니다."
        );
    }
}