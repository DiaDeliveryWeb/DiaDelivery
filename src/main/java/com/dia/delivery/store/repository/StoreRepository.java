package com.dia.delivery.store.repository;

import com.dia.delivery.store.entity.Stores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Stores, Long> {
    List<Stores> findByCategory(String category);
    Stores findByName(String name);

    // 사용자별 가게 조회
    List<Stores> findByUsersId(Long userId);

}
