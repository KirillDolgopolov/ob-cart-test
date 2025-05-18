package com.onebox.cartservice.service;

import com.onebox.cartservice.DTO.CartDTO;
import com.onebox.cartservice.DTO.ProductDTO;

import java.util.Optional;
import java.util.UUID;

public interface CartService {

    Optional<CartDTO> getCartById(UUID id);
    CartDTO createCart();
    void deleteCartById(UUID id);

    CartDTO addProductToCart(UUID cartId, ProductDTO productDTO);

}
