package store.repository;

import java.util.Optional;
import store.domain.product.PromotionType;

public interface PromotionTypeRepository {

    PromotionType savePromotionType(PromotionType promotionType);

    Optional<PromotionType> findPromotionTypeByName(String promotionTypeName);

}
