package com.demo.repository;

import com.demo.entity.SysApiLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysApiLogRepository extends JpaRepository<SysApiLog, Long> {

    List<SysApiLog> findAllByOrderByIdDesc();
}
