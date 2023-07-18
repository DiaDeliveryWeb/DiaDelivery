package com.dia.delievery.product.repository;

import com.dia.delievery.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {

    List<Products> findAllByIdIn(List<Long> productList);
}
