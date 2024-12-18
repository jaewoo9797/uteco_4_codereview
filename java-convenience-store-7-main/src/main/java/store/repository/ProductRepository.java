package store.repository;

import java.util.Map;
import java.util.Optional;
import store.domain.product.Product;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findByName(String name);

    Map<String, Product> getStore();
}
