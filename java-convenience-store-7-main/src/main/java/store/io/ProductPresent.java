package store.io;

import store.repository.ProductRepository;

public class ProductPresent {

    private final ProductRepository productRepository;

    public ProductPresent(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void printProductInfo() {
        productRepository.getStore().values().forEach(System.out::println);
    }

    public static void printGreeting() {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
    }

    public static void printRequireProductNameAndQuantity() {
        System.out.println("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2], [감자칩-1]");
    }

    public static void printWhetherReceiveAdditionalProduct(String productName, int additionalQuantity) {
        System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)", productName, additionalQuantity);
    }

    public static void printFullPaymentDueToLackOfProduct(String productName, int quantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)", productName, quantity);
    }

    public static void printMembershipDiscountApply() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
    }

    public void printReceiptInfo() {

    }

    public static void printAdditionalOrder() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
    }
}
