package org.javapro.skhlebko.homework_4.model;

import java.util.Objects;

public class Product {
    private Long id;
    private Long accountId;
    private Double balance;
    private String productType;
    private Long userId;


    public Product(Long id, Long accountId, Double balance, String productType, Long userId) {
        this.id = id;
        this.accountId = accountId;
        this.balance = balance;
        this.productType = productType;
        this.userId = userId;
    }


    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Double getBalance() {
        return balance;
    }

    public String getProductType() {
        return productType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(accountId, product.accountId) &&
                Objects.equals(balance, product.balance) &&
                Objects.equals(productType, product.productType) &&
                Objects.equals(userId, product.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, balance, productType, userId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", balance=" + balance +
                ", productType='" + productType + '\'' +
                ", userId=" + userId + // Добавлен вывод userId
                '}';
    }
}