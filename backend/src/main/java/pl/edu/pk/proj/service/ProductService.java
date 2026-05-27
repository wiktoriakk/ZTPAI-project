package pl.edu.pk.proj.service;

import jakarta.persistence.EntityNotFoundException;
import pl.edu.pk.proj.event.ProductCreatedEvent;
import pl.edu.pk.proj.event.ProductDeletedEvent;
import pl.edu.pk.proj.model.Product;
import pl.edu.pk.proj.repository.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public ProductService(ProductRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
    }

    public Product create(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        Product saved = repository.save(product);
        eventPublisher.publishEvent(new ProductCreatedEvent(saved));
        return saved;
    }

    public void delete(Long id) {
        repository.deleteById(id);
        eventPublisher.publishEvent(new ProductDeletedEvent(id));
    }
}
