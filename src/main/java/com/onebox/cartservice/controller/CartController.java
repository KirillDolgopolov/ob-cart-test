package com.onebox.cartservice.controller;

import com.onebox.cartservice.DTO.CartDTO;
import com.onebox.cartservice.DTO.ProductDTO;
import com.onebox.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * Cart controller
 *
 * This class is responsible for handling requests related to cart operations.
 */
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    /**
     * Test endpoint
     *
     * @return String with test message
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Test endpoint is working", HttpStatus.OK);
    }

    /**
     * Get cart by id
     *
     * @param cartId the id of the cart
     * @return the cart
     */
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable UUID cartId) {
        Optional<CartDTO> result = cartService.getCartById(cartId);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create card
     *
     * @return Card DTO with id
     */
    @PostMapping("/")
    public ResponseEntity<CartDTO> createCart() {
        CartDTO result = cartService.createCart();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Delete cart by id
     *
     * @param cartId
     * @return String with deleted cart id
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable UUID cartId) {
        cartService.deleteCartById(cartId);
        return ResponseEntity.ok(
                String.format("Cart was deleted by id: %s", cartId));
    }

    /**
     * Add product to cart
     *
     * @param cartId     the id of the cart
     * @param productDTO the DTO of the product
     * @return the cart with the products
     */
    @PostMapping("/{cartId}/products/")
    public ResponseEntity<CartDTO> addProductToCart(
            @PathVariable UUID cartId, @RequestBody ProductDTO productDTO) {
        CartDTO result = cartService.addProductToCart(cartId, productDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
