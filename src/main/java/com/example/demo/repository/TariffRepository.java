package com.example.demo.repository;

import com.example.demo.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff,Integer> {
}
