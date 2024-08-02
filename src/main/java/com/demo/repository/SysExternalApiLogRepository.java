package com.demo.repository;

import com.demo.entity.SysExternalApiLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysExternalApiLogRepository extends JpaRepository<SysExternalApiLog, Long> {

    List<SysExternalApiLog> findAllByOrderByIdDesc();
}
