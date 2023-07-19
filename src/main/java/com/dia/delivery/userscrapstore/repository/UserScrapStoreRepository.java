package com.dia.delivery.userscrapstore.repository;

import com.dia.delivery.store.entity.Stores;
import com.dia.delivery.user.entity.Users;
import com.dia.delivery.userscrapstore.entity.UserScrapStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserScrapStoreRepository extends JpaRepository<UserScrapStore, Long> {
    void deleteByUsersAndStores_Id(Users users, Long storeId);

    Optional<UserScrapStore> findByUsersAndStores(Users user, Stores stores);
}