package com.example.demo.entity;

import com.example.demo.entity.CustomerType.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private CustomerType tariffType;// Tariflar ham ikki turga bo`linadi: 1) Jismoniy shaxslar uchun;2) Yuridik shaxslar uchun.

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @OneToMany(cascade = CascadeType.ALL)
    private List<USSD> ussds;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Detail> details;

}
