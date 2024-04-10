package com.batch.repository;

import com.batch.entity.DataUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<DataUser, Long>  {
//    void saveAll();

    @Query(value = "select * from users", nativeQuery = true)
    List<DataUser> findAll();
}
