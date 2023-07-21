package com.dia.delivery.order.repository;

import com.dia.delivery.order.entity.Orders;
import com.dia.delivery.user.entity.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderNum(String orderNum);

    List<Orders> findAllByUser(Users user);

    List<Orders> findAllByOwner(Users user);
}