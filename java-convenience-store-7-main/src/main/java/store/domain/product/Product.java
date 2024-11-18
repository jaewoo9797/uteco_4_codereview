package store.domain.product;

import store.common.QuantityCounter;

public class Product {

    private final ProductInfo productInfo;
    private final Quantity promotionQuantity;
    private final PromotionType promotionType;

    public Product(ProductInfo productInfo, Quantity promotionQuantity, PromotionType promotionType) {
        this.productInfo = productInfo;
        this.promotionQuantity = promotionQuantity;
        this.promotionType = promotionType;
    }

    public void reduceProductQuantity(QuantityCounter quantityCounter) {
        promotionQuantity.reduceOrderQuantity(quantityCounter);
    }
}
