package com.onebox.cartservice.mapper;

import com.onebox.cartservice.DTO.CartDTO;
import com.onebox.cartservice.entity.Cart;

/**
 * Mapper class to convert Cart and CartDTO objects.
 */
public class CartMapper {

    /**
     * Convert Cart to CartDTO.
     *
     * @param cart
     * @return
     */
    public static CartDTO toDTO(Cart cart) {
        if (cart == null) {
            return null;
        }
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setProducts(cart.getProducts());
        return cartDTO;
    }

    /**
     * Convert CartDTO to Cart.
     *
     * @param cartDTO
     * @return
     */
    public static Cart toEntity(CartDTO cartDTO) {
        if (cartDTO == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setProducts(cartDTO.getProducts());
        return cart;
    }
}
