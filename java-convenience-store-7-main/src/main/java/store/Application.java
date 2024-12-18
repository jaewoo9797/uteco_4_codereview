package store;

import java.util.List;
import store.dto.OrderRequestDto;
import store.io.InputView;
import store.io.ProductPresent;
import store.repository.ProductInMemoryRepository;
import store.repository.ProductRepository;
import store.repository.PromotionTypeRepository;
import store.repository.PromotionTypeRepositoryInMemory;
import store.service.ProductManager;

public class Application {
    private static final PromotionTypeRepository typeRepo = new PromotionTypeRepositoryInMemory();
    private static final ProductRepository productRepository = new ProductInMemoryRepository(typeRepo);
    private static final ProductPresent productPresent = new ProductPresent(productRepository);
    private static final ProductManager productManager = new ProductManager(productRepository);
    private static final InputView inputView = new InputView(productManager);

    public static void main(String[] args) {

        Application application = new Application();
        do {
            ProductPresent.printGreeting();
            productPresent.printProductInfo();

            List<OrderRequestDto> orders = application.inputOrder();

        } while (inputView.enterAdditionalOrder());
    }

    private List<OrderRequestDto> inputOrder() {
        while(true) {
            try {
                var orderRequestDtos = inputView.enterOrder();
                inputView.checkQuantityForOrders(orderRequestDtos);
                return orderRequestDtos;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
