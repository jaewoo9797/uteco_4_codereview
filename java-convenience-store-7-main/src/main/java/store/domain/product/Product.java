package store.domain.product;

import store.common.QuantityCounter;

public class Product {

    private final ProductInfo productInfo;
    private final Quantity quantity;
    private final PromotionType promotionType;

    public Product(ProductInfo productInfo, Quantity quantity, PromotionType promotionType) {
        this.productInfo = productInfo;
        this.quantity = quantity;
        this.promotionType = promotionType;
    }

    public void reduceProductQuantity(QuantityCounter quantityCounter) {
        quantity.reduceOrderQuantity(quantityCounter);
    }

    // 프로모션 상품에 추가 제공받을 수 있는지 여부를 검사한다.
    public boolean inspectionCanGetAdditionalPromotionProduct(QuantityCounter orderQuantity) {
        return promotionType.inspectionCanGetAdditionalPromotionProduct(orderQuantity);
    }

    // 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 개수를 계산한다.
    public int calculatePaidWithoutPromotionQuantity(QuantityCounter orderQuantity) {
        int givenProduct = promotionType.getAdditionalPromotionProduct(quantity.getPromotionQuantityCounter());
        int buyGet = promotionType.getPromotionBuyGet();
        return orderQuantity.calculateMinusQuantityCount(new QuantityCounter(givenProduct * buyGet));
    }

    // 프로모션 재고가 주문 재고를 처리할 수 있는지 검사한다.
    public boolean checkPromotionQuantityCanProcess(QuantityCounter orderQuantity) {
        return quantity.checkPromotionQuantityCanProcess(orderQuantity);
    }

    // 추가 제공받는 상품의 수를 계산
    public int getAdditionalPromotionProductQuantity(QuantityCounter orderQuantity) {
        return promotionType.getAdditionalPromotionProduct(orderQuantity);
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }
}
