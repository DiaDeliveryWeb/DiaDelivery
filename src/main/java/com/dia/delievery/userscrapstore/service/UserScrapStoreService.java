package com.dia.delievery.userscrapstore.service;

import com.dia.delievery.user.entity.Users;
import com.dia.delievery.userscrapstore.repository.UserScrapStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserScrapStoreService {
    private UserScrapStoreRepository userScrapStoreRepository;

    public void save(Users user, Long storeId) {

    }
}
