package com.dia.delivery.product.repository;

import com.dia.delivery.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products,Long> {
}
