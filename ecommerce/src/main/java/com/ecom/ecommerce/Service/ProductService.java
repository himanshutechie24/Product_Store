package com.ecom.ecommerce.Service;

import com.ecom.ecommerce.Entity.Products;
import com.ecom.ecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Products saveProduct(Products products) {
        boolean exists = productRepository.existsByName(products.getName());

        if (exists) {
            throw new IllegalArgumentException("Product with name '" + products.getName() + "' already exists.");
        }

        return productRepository.save(products);
    }

    public List<Products> getProducts() {
           List<Products> products = productRepository.findAll();

            if (products.isEmpty()) {
                throw new NoSuchElementException("No products found in the database.");
            }

            return products;
        }



    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById(id);
    }

    public Products getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product with ID " + id + " not found."));
    }

    public Products updateProduct(Long id, Products updatedProduct) {
        Products existing = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with ID " + id + " not found."));

        if (StringUtils.hasText(updatedProduct.getName())) {
            existing.setName(updatedProduct.getName());
        }

        if (updatedProduct.getNumber() != null) {
            existing.setNumber(updatedProduct.getNumber());
        }

        if (StringUtils.hasText(updatedProduct.getImage())) {
            existing.setImage(updatedProduct.getImage());
        }

        return productRepository.save(existing);
    }
}
