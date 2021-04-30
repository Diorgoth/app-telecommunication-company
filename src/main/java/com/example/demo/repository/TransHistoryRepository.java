package com.example.demo.repository;

import com.example.demo.entity.TransHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransHistoryRepository extends JpaRepository<TransHistory,Integer> {
}
