package store.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import store.common.ErrorMessage;
import store.common.QuantityCounter;
import store.domain.product.Product;
import store.domain.product.PromotionType;
import store.dto.ProductCreateRequestDto;

public class ProductInMemoryRepository implements ProductRepository {
    private static final String PRODUCTS_DIRECTORY = "src/main/resources/products.md";
    private final Map<String, Product> store = new HashMap<>();
    private final PromotionTypeRepository promotionTypeRepository;

    public ProductInMemoryRepository(PromotionTypeRepository promotionTypeRepository) {
        this.promotionTypeRepository = promotionTypeRepository;
        initStore();
    }

    @Override
    public Product save(Product product) {
        store.put(product.getProductName(), product);
        return product;
    }

    @Override
    public Optional<Product> findByName(String name) {
        return Optional.ofNullable(store.get(name));
    }

    private void initStore() {
        try (var lines = Files.lines(Paths.get(PRODUCTS_DIRECTORY))) {
            lines.skip(1)
                    .forEach(this::processLine);
        } catch (IOException e) {
            throw new RuntimeException("[ERROR] 파일 읽기 오류: " + PRODUCTS_DIRECTORY, e);
        }
    }

    private void processLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException(ErrorMessage.FILE_FORMAT_ERROR.getMessage());
        }
        try {
            String name = parts[0].trim();
            int price = Integer.parseInt(parts[1].trim());
            int quantity = Integer.parseInt(parts[2].trim());
            String typeName = parts[3].trim();

            PromotionType promotionType = promotionTypeRepository.findPromotionTypeByName(typeName)
                    .orElseThrow(IllegalArgumentException::new);

            createOrUpdateProduct(new ProductCreateRequestDto(name, price, quantity, promotionType));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.FILE_FORMAT_ERROR.getMessage());
        }
    }

    private void createOrUpdateProduct(ProductCreateRequestDto dto) {
        Optional<Product> existingProduct = findByName(dto.name());
        PromotionType promotionType = dto.promotionType();

        // (프로모션 저장) 프로모션 상품이 있을 경우
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.addNonPromotionProductQuantity(dto.quantity());
            return;
        }

        // (논 프로모션 저장) 논 프로모션 상품이 등록될 경우
        if (promotionType.nameEqualsNull(dto.promotionType())) {
            QuantityCounter quantityCounter = new QuantityCounter(dto.quantity());
            save(new Product(dto, quantityCounter));
            return;
        }
        // (프로모션 저장) 프로모션 상품이 없을 경우
        Product product = new Product(dto);
        save(product);
    }
}
