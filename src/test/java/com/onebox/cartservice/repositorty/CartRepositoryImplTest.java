package com.onebox.cartservice.repositorty;

import com.onebox.cartservice.entity.Cart;
import com.onebox.cartservice.entity.Product;
import com.onebox.cartservice.exception.CustomException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CartRepositoryImplTest {

    @Test
    void shouldReturnCart_whenCartIsSaved() {
        Cart testCart = new Cart();
        CartRepository cartRepository = new CartRepositoryImpl();
        Cart savedCart = cartRepository.save(testCart);
        assertEquals(testCart, savedCart);
    }


    @Test
    void shouldReturnCart_whenCartExists() {
        Cart testCart = new Cart();
        UUID testCartId = testCart.getId();
        UUID randomCartId = UUID.randomUUID();

        CartRepository cartRepository = new CartRepositoryImpl();
        cartRepository.save(testCart);

        Optional<Cart> cart = cartRepository.findById(testCartId);
        assertTrue(cart.isPresent());
        assertEquals(testCartId, cart.get().getId());
        assertNotEquals(randomCartId, cart.get().getId());

        Optional<Cart> emptyCart = cartRepository.findById(randomCartId);
        assertFalse(emptyCart.isPresent());

    }

    @Test
    void shouldNotHave_whenRemoved() {
        Cart testCart = new Cart();
        UUID testCartId = testCart.getId();

        CartRepository cartRepository = new CartRepositoryImpl();
        cartRepository.save(testCart);

        Optional<Cart> cartBeforeDelete = cartRepository.findById(testCartId);
        assertTrue(cartBeforeDelete.isPresent());

        cartRepository.deleteById(testCartId);

        Optional<Cart> cartAfterDelete = cartRepository.findById(testCartId);
        assertFalse(cartAfterDelete.isPresent());
    }

    @Test
    void shoudAddProductToCart_whenProductIsAdded() {
        Cart testCart = new Cart();
        UUID testCartId = testCart.getId();

        CartRepository cartRepository = new CartRepositoryImpl();
        cartRepository.save(testCart);

        Product testProduct = new Product(1, "Test Product", 10);
        cartRepository.addProductToCart(testCartId, testProduct);

        Optional<Cart> cartAfterAddProduct = cartRepository.findById(testCartId);
        assertTrue(cartAfterAddProduct.isPresent());
        assertTrue(cartAfterAddProduct.get().getProducts().containsValue(testProduct));
    }

    @Test
    void shouldUpdateProductAmount_whenProductAlreadyExists() {
        Cart testCart = new Cart();
        UUID testCartId = testCart.getId();

        CartRepository cartRepository = new CartRepositoryImpl();
        cartRepository.save(testCart);

        Product testProduct = new Product(1L, "Test Product", 10);
        cartRepository.addProductToCart(testCartId, testProduct);

        // Add the same product again with a different amount
        Product updatedProduct = new Product(1L, "Test Product", 5);
        cartRepository.addProductToCart(testCartId, updatedProduct);

        Optional<Cart> cartAfterAddProduct = cartRepository.findById(testCartId);
        assertTrue(cartAfterAddProduct.isPresent());
        assertEquals(15, cartAfterAddProduct.get().getProducts().get(1L).getAmount());
    }

    @Test
    void shouldThrowException_whenCartNotFound() {
        CartRepository cartRepository = new CartRepositoryImpl();
        UUID randomCartId = UUID.randomUUID();
        Product testProduct = new Product(1, "Test Product", 10);

        assertThrows(CustomException.class, () -> {
            cartRepository.addProductToCart(randomCartId, testProduct);
        });
    }


}