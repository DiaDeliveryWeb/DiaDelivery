package com.dia.delievery.order.repository;

import com.dia.delievery.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderNum(String orderNum);
}
