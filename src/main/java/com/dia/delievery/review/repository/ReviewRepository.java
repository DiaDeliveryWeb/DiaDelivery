package com.dia.delievery.review.repository;

import com.dia.delievery.review.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {
}
