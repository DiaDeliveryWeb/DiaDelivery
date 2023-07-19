package com.dia.delivery.productorder.repository;

import com.dia.delivery.productorder.entity.ProductOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrders, Long> {
}