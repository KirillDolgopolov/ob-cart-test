package com.onebox.cartservice.service;

import com.onebox.cartservice.DTO.CartDTO;
import com.onebox.cartservice.DTO.ProductDTO;
import com.onebox.cartservice.entity.Cart;
import com.onebox.cartservice.mapper.CartMapper;
import com.onebox.cartservice.mapper.ProductMapper;
import com.onebox.cartservice.repositorty.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Cart service implementation
 *
 * This class is responsible for handling cart operations.
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public Optional<CartDTO> getCartById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Cart ID cannot be null");
        }
        Optional<Cart> responseFromRepository = cartRepository.findById(id);
        if (responseFromRepository.isEmpty()) {
            return Optional.empty();
        } else {
            CartDTO responseFromService = CartMapper.toDTO(responseFromRepository.get());
            return Optional.of(responseFromService);
        }
    }

    @Override
    public CartDTO createCart() {
        Cart newCart = new Cart();
        Cart responseFromRepository = cartRepository.save(newCart);

        return CartMapper.toDTO(responseFromRepository);
    }

    @Override
    public void deleteCartById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Cart ID cannot be null");
        }
        Optional<Cart> responseFromRepository = cartRepository.findById(id);
        if (responseFromRepository.isEmpty()) {
            throw new IllegalArgumentException("Cart not found with this ID");
        }
        cartRepository.deleteById(id);
    }

    @Override
    public CartDTO addProductToCart(UUID cartId, ProductDTO productDTO) {
        if (cartId == null || this.isValidProductDTO(productDTO) == false) {
            throw new IllegalArgumentException("Incorrect cart ID or product DTO");
        }
        Cart responseFromRepository =
                cartRepository.addProductToCart(cartId, ProductMapper.toEntity(productDTO));
        CartDTO responseFromService = CartMapper.toDTO(responseFromRepository);
        return responseFromService;
    }

    /**
     * Check if product DTO is valid
     *
     * @param productDTO the product DTO
     * @return true if is valid
     */
    private boolean isValidProductDTO(ProductDTO productDTO) {
        if (productDTO == null) {
            return false;
        } else if (productDTO.getId() == null ||
                productDTO.getDescription() == null ||
                productDTO.getAmount() < 1) {
            return false;
        } else {
            return true;
        }
    }

}
