package store.common;

public enum ErrorMessage {
    INSUFFICIENT_INVENTORY_NUMBER_PURCHASED("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해주세요."),
    QUANTITY_IS_LESS_THEN_ZERO("재고는 0 미만으로 감소할 수 없습니다."),
    PROMOTION_TYPE_INFO_SHOULD_NOT_NULL("프로모션 타입 정보는 필수정보입니다."),
    ;

    private final String message;

    private ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return String.format("[ERROR] %s", message);
    }
}
