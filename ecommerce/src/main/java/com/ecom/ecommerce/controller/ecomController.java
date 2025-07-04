package com.ecom.ecommerce.controller;

import com.ecom.ecommerce.Entity.Products;
import com.ecom.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/products")
public class ecomController {

    @Autowired
    private ProductService productService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/")
    public ResponseEntity<?> saveProduct(@RequestBody Products products) {
        if (!StringUtils.hasText(products.getName()) ||
                products.getNumber() == null ||
                !StringUtils.hasText(products.getImage())) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Please provide all fields"));
        }

        try{
            Products savedProduct = productService.saveProduct(products);
           return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("Server error", e.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProductById(id);
            return ResponseEntity.ok("Product with id "+id+" deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Server error", e.getMessage()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Products> products = productService.getProducts();
            return ResponseEntity.ok(products);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Server error", e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Products product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Server error", e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestBody Products updatedProduct){
        try {
            Products product = productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(product);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Server error", e.getMessage()));
        }
    }

}
