package store.domain.product;

public class ProductInfo {
    private final String name;
    private final int price;

    public ProductInfo(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
