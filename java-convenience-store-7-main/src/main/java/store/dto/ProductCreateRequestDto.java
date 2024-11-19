package store.dto;

import store.domain.product.PromotionType;

public record ProductCreateRequestDto(String name, int price, int quantity, PromotionType promotionType) {
}
