package com.batch.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository {
    void saveAll();
}
