package com.onebox.cartservice.service;

import com.onebox.cartservice.DTO.CartDTO;
import com.onebox.cartservice.DTO.ProductDTO;
import com.onebox.cartservice.entity.Cart;
import com.onebox.cartservice.entity.Product;
import com.onebox.cartservice.mapper.CartMapper;
import com.onebox.cartservice.mapper.ProductMapper;
import com.onebox.cartservice.repositorty.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private CartServiceImpl cartService;

    @Captor
    private ArgumentCaptor<Cart> cartCaptor;

    @Test
    void shouldThrowException_whenGetByIdGetsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> cartService.getCartById(null));
    }

    @Test
    void shouldReturnEmptyOptional_whenGetByIdGetsNotExistingId() {
        UUID id = UUID.randomUUID();
        Optional<Cart> emptyOptional = Optional.empty();
        when(cartRepository.findById(id)).thenReturn(emptyOptional);

        Optional<CartDTO> result = cartService.getCartById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnCorrectlyCart_whenGetById() {
        Cart cart = new Cart();
        UUID cartId = cart.getId();
        CartDTO expectedCartDTO = CartMapper.toDTO(cart);
        Optional<CartDTO> expectedCartDtoOptional = Optional.of(expectedCartDTO);

        Optional<Cart> optionalCart = Optional.of(cart);
        when(cartRepository.findById(cartId)).thenReturn(optionalCart);

        Optional<CartDTO> result = cartService.getCartById(cartId);

        assertTrue(result.isPresent());
        assertEquals(expectedCartDtoOptional.get().getId(), result.get().getId());
    }

    @Test
    void shouldReturnCart_whenCreateCart() {
        when(cartRepository.save(any(Cart.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CartDTO result = cartService.createCart();

        // we catch the new card which is created by service. So we can know their id;
        verify(cartRepository).save(cartCaptor.capture());
        Cart capturedCart = cartCaptor.getValue();

        assertEquals(capturedCart.getId(), result.getId());
    }


    @Test
    void shouldThrowException_whenDeleteByIdGetsNull() {

        assertThrows(IllegalArgumentException.class,
                () -> cartService.deleteCartById(null));
    }

    @Test
    void shouldThrowException_whenDeleteByIdGetsNotExistingId() {
        UUID id = UUID.randomUUID();
        Optional<Cart> emptyOptional = Optional.empty();
        when(cartRepository.findById(id)).thenReturn(emptyOptional);

        assertThrows(IllegalArgumentException.class,
                () -> cartService.deleteCartById(id));
    }

    @Test
    void shouldDeleteCart_whenDeleteById() {
        UUID id = UUID.randomUUID();
        Cart cart = new Cart();
        Optional<Cart> optionalCart = Optional.of(cart);
        when(cartRepository.findById(id)).thenReturn(optionalCart);

        assertDoesNotThrow(() -> cartService.deleteCartById(id));
        // we called deleteById and findById methods correctly
        verify(cartRepository).findById(id);
        verify(cartRepository).deleteById(id);
    }

    @Test
    void shouldAddProductToCart_whenValidInput() {
        ProductDTO productDTO = new ProductDTO(1L, "Product_1", 1);
        Product productEntity = ProductMapper.toEntity(productDTO);

        Cart updatedCart = new Cart();
        UUID cartId = updatedCart.getId();
        updatedCart.getProducts().put(1L, productEntity);

        when(cartRepository.addProductToCart(eq(cartId), any(Product.class))).thenReturn(updatedCart);

        CartDTO result = cartService.addProductToCart(cartId, productDTO);

        assertEquals(cartId, result.getId());
        assertTrue(result.getProducts().containsKey(1L));
        assertEquals(1L, result.getProducts().get(1L).getId());
    }

    @Test
    void shouldThrowException_whenAddProductToCartWithNullId() {
        assertThrows(IllegalArgumentException.class,
                () -> cartService.addProductToCart(null, new ProductDTO()));
    }

    @Test
    void shouldThrowException_whenAddProductToCartWithNullProductDTO() {
        assertThrows(IllegalArgumentException.class,
                () -> cartService.addProductToCart(UUID.randomUUID(), null));
    }

    @Test
    void shouldThrowException_whenAddProductToCartWithInvalidProductDTO() {
        ProductDTO invalidProductDTO = new ProductDTO(1L, null, 0);
        assertThrows(IllegalArgumentException.class,
                () -> cartService.addProductToCart(UUID.randomUUID(), invalidProductDTO));
    }
}