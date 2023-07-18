package com.dia.delievery.productorder.repository;

import com.dia.delievery.productorder.entity.ProductOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrders, Long> {
}
