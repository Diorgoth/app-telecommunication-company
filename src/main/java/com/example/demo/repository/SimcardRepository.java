package com.example.demo.repository;

import com.example.demo.entity.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimcardRepository extends JpaRepository<SimCard,Integer> {
}
