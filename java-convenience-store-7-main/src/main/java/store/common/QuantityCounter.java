package store.common;

public class QuantityCounter {

    private int quantity;

    public QuantityCounter(int quantity) {
        validateQuantityLessThanZero(quantity);
        this.quantity = quantity;
    }

    public void increaseQuantity(QuantityCounter quantity) {
        this.quantity += quantity.getQuantity();
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

    public int calculatePlusQuantityCount(QuantityCounter quantityCounter) {
        return this.quantity + quantityCounter.getQuantity();
    }

    public int calculateMinusQuantityCount(QuantityCounter quantityCounter) {
        if (this.quantity <  quantityCounter.getQuantity()) {
            throw new IllegalArgumentException(ErrorMessage.QUANTITY_IS_LESS_THEN_ZERO.getMessage());
        }
        return this.quantity - quantityCounter.getQuantity();
    }

    public boolean checkQuantityMoreThan(QuantityCounter quantityCounter) {
        return this.quantity >= quantityCounter.getQuantity();
    }
}
