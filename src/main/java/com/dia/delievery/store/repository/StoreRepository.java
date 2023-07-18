package com.dia.delievery.store.repository;

import com.dia.delievery.store.entity.Stores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Stores, Long> {
    List<Stores> findByCategory(String category);
    Stores findByName(String name);

}
