package pl.edu.pk.proj.service;

import jakarta.persistence.EntityNotFoundException;
import pl.edu.pk.proj.model.Product;
import pl.edu.pk.proj.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
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
        return repository.save(product);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
