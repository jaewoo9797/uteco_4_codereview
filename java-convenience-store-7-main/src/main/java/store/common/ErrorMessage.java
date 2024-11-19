package store.common;

public enum ErrorMessage {
    INSUFFICIENT_INVENTORY_NUMBER_PURCHASED("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해주세요."),
    QUANTITY_IS_LESS_THEN_ZERO("재고는 0 미만으로 감소할 수 없습니다."),
    PROMOTION_TYPE_INFO_SHOULD_NOT_NULL("프로모션 타입 정보는 필수정보입니다."),
    FILE_FORMAT_ERROR("파일 오류."),
    FILE_FORMAT_DATA_FORMAT_ERROR("파일 데이터 형식 오류"),
    INPUT_ERROR("입력 형식이 잘못되었습니다. 다시 입력해주세요."),
    NOT_EXIST_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),

    ;

    private final String message;

    private ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return String.format("[ERROR] %s", message);
    }
}
