package store.common;

public class QuantityCounter {

    private int quantity;

    public QuantityCounter(int quantity) {
        validateQuantityLessThanZero(quantity);
        this.quantity = quantity;
    }

    public void decreaseQuantity(int quantity) {
        if (this.quantity < quantity) {
            throw new IllegalArgumentException(ErrorMessage.QUANTITY_IS_LESS_THEN_ZERO.getMessage());
        }
        this.quantity -= quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    private void validateQuantityLessThanZero(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(ErrorMessage.QUANTITY_IS_LESS_THEN_ZERO.getMessage());
        }
    }

}
