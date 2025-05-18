package com.onebox.cartservice.mapper;

import com.onebox.cartservice.DTO.ProductDTO;
import com.onebox.cartservice.entity.Product;

/**
 * Mapper class to convert Product and ProductDTO objects.
 */
public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setDescription(product.getDescription());
        productDTO.setAmount(product.getAmount());
        return productDTO;
    }

    public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        if (productDTO == null) {
            return null;
        }
        product.setId(productDTO.getId());
        product.setDescription(productDTO.getDescription());
        product.setAmount(productDTO.getAmount());
        return product;
    }
}
