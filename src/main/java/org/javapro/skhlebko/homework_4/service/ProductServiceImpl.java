package org.javapro.skhlebko.homework_4.service;

import lombok.RequiredArgsConstructor;
import org.javapro.skhlebko.homework_4.dao.ProductDao;
import org.javapro.skhlebko.homework_4.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findByUserId(Long userId) {
        return productDao.findByUserId(userId);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    @Transactional
    public Product update(Product product) {
        return productDao.update(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productDao.delete(id);
    }
}