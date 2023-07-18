package com.dia.delivery.userscrapstore.service;

import com.dia.delivery.store.entity.Stores;
import com.dia.delivery.store.repository.StoreRepository;
import com.dia.delivery.user.entity.Users;
import com.dia.delivery.userscrapstore.entity.UserScrapStore;
import com.dia.delivery.userscrapstore.repository.UserScrapStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserScrapStoreService {
    private final UserScrapStoreRepository userScrapStoreRepository;
    private final StoreRepository storeRepository;

    public void save(Users user, Long storeId) {
        Stores stores = storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다."));
        UserScrapStore userScrapStore = new UserScrapStore(user, stores);
        if (userScrapStoreRepository.findByUsersAndStores(user, stores).isPresent())
            throw new IllegalArgumentException("이미 스크랩한 가게입니다.");
        userScrapStoreRepository.save(userScrapStore);
    }

    public void delete(Users user, Long storeId) {
        Stores stores = storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다."));
        if (userScrapStoreRepository.findByUsersAndStores(user, stores).isEmpty())
            throw new IllegalArgumentException("스크랩하지 않은 가게입니다.");
        userScrapStoreRepository.deleteByUsersAndStores_Id(user, storeId);
    }
}
