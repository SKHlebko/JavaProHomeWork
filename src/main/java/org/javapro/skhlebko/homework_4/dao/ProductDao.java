package org.javapro.skhlebko.homework_4.dao;

import org.javapro.skhlebko.homework_4.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> findById(Long id);

    List<Product> findByUserId(Long userId);

    Product save(Product product);

    Product update(Product product);

    Long delete(Long id);
}