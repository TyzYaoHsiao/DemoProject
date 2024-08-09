package com.demo.repository;

import com.demo.entity.SysExternalApiLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysExternalApiLogRepository extends JpaRepository<SysExternalApiLog, Long> {

    List<SysExternalApiLog> findAllByOrderByIdDesc();
}
