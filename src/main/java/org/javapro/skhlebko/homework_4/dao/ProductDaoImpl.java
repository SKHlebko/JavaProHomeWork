package org.javapro.skhlebko.homework_4.dao;

import lombok.extern.slf4j.Slf4j;
import org.javapro.skhlebko.homework_4.exception.ProductNotFoundException;
import org.javapro.skhlebko.homework_4.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class ProductDaoImpl implements ProductDao {

    private static final String FIND_BY_ID_SQL = "SELECT * FROM products WHERE id = :id";
    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM products WHERE user_id = :userId";
    private static final String INSERT_SQL = "INSERT INTO products (account_id, balance, product_type, user_id) VALUES (:accountId, :balance, :productType, :userId) RETURNING id";
    private static final String UPDATE_SQL = "UPDATE products SET account_id = :accountId, balance = :balance, product_type = :productType WHERE id = :id";
    private static final String DELETE_SQL = "DELETE FROM products WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserDao userDao;

    @Autowired
    public ProductDaoImpl(NamedParameterJdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    private final RowMapper<Product> rowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getLong("account_id"),
            rs.getDouble("balance"),
            rs.getString("product_type"),
            rs.getLong("user_id")
    );

    @Override
    public Optional<Product> findById(Long id) {
        try {
            log.info("Finding product by ID: {}", id);
            SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, parameters, rowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Product not found for ID: {}", id, e);
            throw new ProductNotFoundException("Product not found for ID: " + id);
        }
    }

    @Override
    public List<Product> findByUserId(Long userId) {
        log.info("Finding products by user ID: {}", userId);
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("userId", userId);
        return jdbcTemplate.query(FIND_BY_USER_ID_SQL, parameters, rowMapper);
    }

    @Override
    public Product save(Product product) {
        Optional<?> userExists = userDao.findById(product.getUserId());
        if (userExists.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + product.getUserId() + " does not exist.");
        }

        log.info("Saving product: {}", product);
        Map<String, Object> params = new HashMap<>();
        params.put("accountId", product.getAccountId());
        params.put("balance", product.getBalance());
        params.put("productType", product.getProductType());
        params.put("userId", product.getUserId());

        Long id = jdbcTemplate.queryForObject(INSERT_SQL, params, Long.class);
        product.setId(id);
        return product;
    }

    @Override
    public Product update(Product product) {
        log.info("Updating product: {}", product);
        Map<String, Object> params = new HashMap<>();
        params.put("accountId", product.getAccountId());
        params.put("balance", product.getBalance());
        params.put("productType", product.getProductType());
        params.put("id", product.getId());

        jdbcTemplate.update(UPDATE_SQL, params);
        return product;
    }

    @Override
    public Long delete(Long id) {
        findById(id);
        log.info("Deleting product by ID: {}", id);
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
        jdbcTemplate.update(DELETE_SQL, parameters);
        return id;
    }
}