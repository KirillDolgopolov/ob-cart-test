package com.onebox.cartservice.controller;

import com.onebox.cartservice.DTO.CartDTO;
import com.onebox.cartservice.DTO.ProductDTO;
import com.onebox.cartservice.entity.Cart;
import com.onebox.cartservice.entity.Product;
import com.onebox.cartservice.mapper.CartMapper;
import com.onebox.cartservice.mapper.ProductMapper;
import com.onebox.cartservice.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;
    @InjectMocks
    private CartController cartController;

    @Test
    void shouldReturnNotFound_whenGetByIdGetsNotExistingId() {
        UUID id = UUID.randomUUID();
        when(cartService.getCartById(id)).thenReturn(Optional.empty());
        HttpStatusCode httpStatusCode = cartController.getCartById(id).getStatusCode();

        assertEquals(HttpStatus.NOT_FOUND, httpStatusCode);
    }

    @Test
    void shouldReturnFound_whenGetByIdGetsExistingId() {
        Cart cart = new Cart();
        UUID cartId = cart.getId();
        when(cartService.getCartById(cartId)).thenReturn(Optional.of(CartMapper.toDTO(cart)));
        ResponseEntity<CartDTO> result = cartController.getCartById(cartId);

        assertEquals(HttpStatus.FOUND, result.getStatusCode());
        assertEquals(result.getBody().getId(), cartId);
    }

    @Test
    void shouldReturnCreated_whenCreateCart() {
        Cart cart = new Cart();
        when(cartService.createCart()).thenReturn(CartMapper.toDTO(cart));
        ResponseEntity<CartDTO> result = cartController.createCart();

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(result.getBody().getId(), cart.getId());
    }

    @Test
    void shouldReturnStringWithId_whenDeleteCart() {
        UUID idToDelete = UUID.randomUUID();
        ResponseEntity<String> result = cartController.deleteCart(idToDelete);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody().contains(idToDelete.toString()));
    }

    @Test
    void shouldReturnCartWithProduct_whenAddProductToCart() {
        Cart cartToAddProduct = new Cart();
        UUID cartId = cartToAddProduct.getId();
        Product productToAdd = new Product(1L, "Test Product", 10);
        cartToAddProduct.getProducts().put(productToAdd.getId(), productToAdd);

        when(cartService.addProductToCart(eq(cartId), any(ProductDTO.class))).thenReturn(
                CartMapper.toDTO(cartToAddProduct));

        ResponseEntity<CartDTO> result =
                cartController.addProductToCart(cartId, ProductMapper.toDTO(productToAdd));

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(cartId, result.getBody().getId());
        assertEquals(productToAdd.getId(), result.getBody().getProducts().get(productToAdd.getId()).getId());
    }

    @Test
    void test() {
        ResponseEntity<String> result = cartController.test();
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}