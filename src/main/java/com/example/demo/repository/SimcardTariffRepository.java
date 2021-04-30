package com.example.demo.repository;

import com.example.demo.entity.SimCardTariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimcardTariffRepository extends JpaRepository<SimCardTariff,Integer> {


}
