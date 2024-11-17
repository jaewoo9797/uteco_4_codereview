package store.common;

public enum ErrorMessage {
    INSUFFICIENT_INVENTORY_NUMBER_PURCHASED("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해주세요."),
    ;

    private final String message;

    private ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return String.format("[ERROR] %s", message);
    }
}
