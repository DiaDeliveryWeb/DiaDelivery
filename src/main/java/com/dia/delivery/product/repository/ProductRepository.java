package com.dia.delivery.product.repository;

import com.dia.delivery.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    List<Products> findAllByIdIn(List<Long> productList);
}
