package com.onebox.cartservice.repositorty;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.onebox.cartservice.entity.Cart;
import com.onebox.cartservice.entity.Product;
import com.onebox.cartservice.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Cart repository implementation
 * <p>
 * This class is responsible for handling cart like a database.
 */
@Repository
public class CartRepositoryImpl implements CartRepository {

    private static final Cache<UUID, Cart> cacheRepository;

    // We have a unique static map-type object for all possible instances of CartRepositoryImpl
    static {
        cacheRepository = CacheBuilder.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();
    }


    public CartRepositoryImpl() {
    }


    @Override
    public Optional<Cart> findById(UUID id) {
        if (cacheRepository.getIfPresent(id) == null) {
            return Optional.empty();
        } else {
            return Optional.of(cacheRepository.getIfPresent(id));
        }
    }

    @Override
    public Cart save(Cart cart) {
        cacheRepository.put(cart.getId(), cart);
        return cart;
    }

    @Override
    public void deleteById(UUID id) {
        cacheRepository.invalidate(id);
    }

    @Override
    public Cart addProductToCart(UUID cartId, Product product) {
        Cart cart = cacheRepository.getIfPresent(cartId);

        if (cart == null) {
            throw new CustomException("Cart not found with this ID", HttpStatus.NOT_FOUND);
        } else {
            Map<Long, Product> products = cart.getProducts();
            if (products.get(product.getId()) == null) {
                products.put(product.getId(), product);
            } else {
                Product existingProduct = products.get(product.getId());
                existingProduct.setAmount(existingProduct.getAmount() + product.getAmount());
            }
        }
        return cart;
    }

}
