package com.dia.delievery.product.repository;

import com.dia.delievery.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products,Long> {
}
