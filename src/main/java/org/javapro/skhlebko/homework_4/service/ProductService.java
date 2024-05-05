package org.javapro.skhlebko.homework_4.service;

import org.javapro.skhlebko.homework_4.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Long id);

    List<Product> findByUserId(Long userId);

    Product save(Product product);

    Product update(Product product);

    void delete(Long id);
}