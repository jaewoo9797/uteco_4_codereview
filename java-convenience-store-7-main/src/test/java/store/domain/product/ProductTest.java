package store.domain.product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.common.QuantityCounter;

class ProductTest {
    private FakeObjectFactory factory;
    private Product product;

    @BeforeEach
    void setUp() {
        factory = FakeObjectFactory.getInstance(); // 싱글톤 인스턴스 가져오기
        product = factory.createProduct(); // fakeProduct 객체 가져오기
    }

    @Test
    @DisplayName("재고 감소 테스트")
    void givenProduct_whenReduceProductQuantity_thenTotalQuantityDecrease() {
        int totalCount =product.getQuantity().getTotalProductQuantity();
        int orderCount = 5;
        QuantityCounter orderQuantity = new QuantityCounter(orderCount);
        product.reduceProductQuantity(orderQuantity);

        assertEquals(totalCount - orderCount, product.getQuantity().getTotalProductQuantity(),
                "주문한 개수만큼 재고가 감소되지 않았습니다.");
    }


    @Test
    @DisplayName("추가 제공 프로모션 검사 테스트")
    void testInspectionCanGetAdditionalPromotionProduct() {
        QuantityCounter orderQuantity = new QuantityCounter(2);
        assertTrue(product.inspectionCanGetAdditionalPromotionProduct(orderQuantity), "추가 제공 프로모션 검사 실패");
    }

    @ParameterizedTest(name = "주문한 상품 개수 {0} ")
    @ValueSource(ints = {0, 1,3,4,6,7,9})
    @DisplayName("추가 제공 프로모션 검사 테스트 실패")
    void testInspectionCanGetAdditionalPromotionProduct2(int orderCount) {
        QuantityCounter orderQuantity = new QuantityCounter(orderCount);
        assertFalse( product.inspectionCanGetAdditionalPromotionProduct(orderQuantity));
    }
}