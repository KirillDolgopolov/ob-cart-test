package com.onebox.cartservice.DTO;

import com.onebox.cartservice.entity.Product;

import java.util.Map;
import java.util.UUID;

public class CartDTO {
    UUID id;
    Map<Long, Product> products;

    public CartDTO() {
    }

    public CartDTO(UUID id, Map<Long, Product> products) {
        this.id = id;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Map<Long, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, Product> products) {
        this.products = products;
    }
}
