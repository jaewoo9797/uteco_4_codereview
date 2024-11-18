package store.domain.product;

import java.time.LocalDate;
import store.common.QuantityCounter;
import store.domain.promotion.TwoPlusOnePromotionCalculator;

public class FakeObjectFactory {


    // 싱글톤 인스턴스
    private static final FakeObjectFactory INSTANCE = new FakeObjectFactory();

    // private 생성자
    private FakeObjectFactory() {}

    // 싱글톤 인스턴스 반환 메서드
    public static FakeObjectFactory getInstance() {
        return INSTANCE;
    }

    // ProductInfo 생성
    public ProductInfo createProductInfo() {
        return new ProductInfo("MD추천", 1000);
    }

    // Quantity 생성 (매번 새로운 객체 반환)
    public Quantity createQuantity() {
        QuantityCounter promotionQuantityCounter = new QuantityCounter(10);
        QuantityCounter nonPromotionQuantityCounter = new QuantityCounter(10);
        return new Quantity(promotionQuantityCounter, nonPromotionQuantityCounter);
    }

    // PromotionTypeInfo 생성
    public PromotionTypeInfo createPromotionTypeInfo() {
        return new PromotionTypeInfo("MD추천", new QuantityCounter(2), new QuantityCounter(1),
                new TwoPlusOnePromotionCalculator());
    }

    // PromotionDuration 생성
    public PromotionDuration createPromotionDuration() {
        return new PromotionDuration(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10));
    }

    // PromotionType 생성
    public PromotionType createPromotionType() {
        return new PromotionType(createPromotionTypeInfo(), createPromotionDuration());
    }

    // Product 생성 (매번 새로운 객체 반환)
    public Product createProduct() {
        return new Product(createProductInfo(), createQuantity(), createPromotionType());
    }
}
