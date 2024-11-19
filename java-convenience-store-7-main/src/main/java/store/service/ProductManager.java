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

    public Product findProduct(OrderRequestDto orderRequestDto) {
        return productRepository.findByName(orderRequestDto.productName()).get();
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

    public boolean checkPromotionQuantityCanProcess(OrderRequestDto order) {
        Product product = productRepository.findByName(order.productName()).orElseThrow();
        return product.checkPromotionQuantityCanProcess(new QuantityCounter(order.quantity()));
    }
}
