package store.service;

import java.util.List;
import store.common.ErrorMessage;
import store.common.QuantityCounter;
import store.domain.product.Product;
import store.dto.OrderRequestDto;
import store.repository.ProductRepository;

public class ProductManager {

    private final ProductRepository productRepository;

    public ProductManager(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void checkQuantityForOrders(List<OrderRequestDto> orders) {
        for (OrderRequestDto order : orders) {
            Product product = productRepository.findByName(order.productName())
                    .orElseThrow(() -> new IllegalArgumentException(
                            ErrorMessage.NOT_EXIST_PRODUCT.getMessage()));
            QuantityCounter orderQuantity = new QuantityCounter(order.quantity());
            product.checkQuantityForOrder(orderQuantity);
        }
    }

    public void checkPromotionQuantityCanProcess(OrderRequestDto order) {
        Product product = productRepository.findByName(order.productName()).get();

    }
}
