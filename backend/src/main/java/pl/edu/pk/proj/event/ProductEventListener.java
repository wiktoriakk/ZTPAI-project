package pl.edu.pk.proj.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {

    private static final Logger log = LoggerFactory.getLogger(ProductEventListener.class);

    @EventListener
    public void onProductCreated(ProductCreatedEvent event) {
        log.info("Product created: id={}, name={}, price={}",
                event.getProduct().getId(),
                event.getProduct().getName(),
                event.getProduct().getPrice());
    }

    @EventListener
    public void onProductDeleted(ProductDeletedEvent event) {
        log.info("Product deleted: id={}", event.getProductId());
    }
}
