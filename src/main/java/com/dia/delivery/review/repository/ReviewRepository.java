package com.dia.delivery.review.repository;

import com.dia.delivery.review.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {
}