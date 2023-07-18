package com.dia.delievery.store.repository;

import com.dia.delievery.store.dto.StoreResponseDto;
import com.dia.delievery.store.entity.Stores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Stores, Long> {
    Stores findByName(String name);

    void deleteByName(String name);

    List<Stores> findAllByCategory(String name);
}
