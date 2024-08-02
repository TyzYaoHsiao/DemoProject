package com.demo.repository;

import com.demo.entity.AdmUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmUserRepository extends JpaRepository<AdmUser, Long> {

    AdmUser findByUserId(String userId);
}
