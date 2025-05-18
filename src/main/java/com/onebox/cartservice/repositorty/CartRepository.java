package com.onebox.cartservice.repositorty;

import com.onebox.cartservice.entity.Cart;
import com.onebox.cartservice.entity.Product;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository {

    Optional<Cart> findById(UUID id);
    Cart save(Cart cart);
    void deleteById(UUID id);

    Cart addProductToCart(UUID cartId, Product product);
}
