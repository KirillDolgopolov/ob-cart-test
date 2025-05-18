package com.onebox.cartservice.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cart {
    private UUID id;
    private Map<Long, Product> products;

    public Cart() {
        this.id = UUID.randomUUID();
        products = new HashMap<Long, Product>();
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
