package pl.edu.pk.proj.repository;

import pl.edu.pk.proj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> { }
