package pl.edu.pk.proj.event;

import pl.edu.pk.proj.model.Product;

public class ProductCreatedEvent {

    private final Product product;

    public ProductCreatedEvent(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
