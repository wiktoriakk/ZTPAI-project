package pl.edu.pk.proj;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pk.proj.model.Product;
import pl.edu.pk.proj.repository.ProductRepository;
import pl.edu.pk.proj.service.ProductService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService productService;

    // Happy path

    @Test
    void shouldReturnProductWhenFound() {
        // Given
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(3000.0);

        when(repository.findById(1L)).thenReturn(Optional.of(product));

        // When
        Product result = productService.getById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void shouldSaveAndReturnProductWithId() {
        // Given
        Product input = new Product();
        input.setName("Phone");
        input.setPrice(2000.0);

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Phone");
        saved.setPrice(2000.0);

        when(repository.save(any(Product.class))).thenReturn(saved);

        // When
        Product result = productService.create(input);

        // Then
        assertNotNull(result.getId());
        assertEquals("Phone", result.getName());
        verify(repository, times(1)).save(any(Product.class));
    }

    // Przypadek negatywny

    @Test
    void shouldThrowEntityNotFoundExceptionWhenProductMissing() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class,
                () -> productService.getById(99L));
    }

    // Walidacja biznesowa

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        // Given
        Product product = new Product();
        product.setName(null);

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> productService.create(product));

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        // Given
        Product product = new Product();
        product.setName("");

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> productService.create(product));

        verify(repository, never()).save(any());
    }
}
