package com.ecom.ecommerce.Repository;

import com.ecom.ecommerce.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    boolean existsByName(String name);
}
