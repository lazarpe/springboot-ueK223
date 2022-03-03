package com.example.demo.domain.authority;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
    Authority findByName(String name);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Authority a set a.name = ?2 where a.name = ?1")
    void updateNameByName(String name, String newName);


}
