package com.dia.delivery.store.repository;

import com.dia.delivery.store.entity.Stores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Stores, Long> {
    Stores findByName(String name);

    void deleteByName(String name);

    List<Stores> findAllByCategory(String name);
}
