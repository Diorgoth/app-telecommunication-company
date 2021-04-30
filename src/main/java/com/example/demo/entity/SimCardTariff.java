package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class SimCardTariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private SimCard simCard;

    @ManyToOne
    private Tariff tariff;

    @Column(nullable = false)
    private boolean status;

    @ManyToOne(cascade = CascadeType.ALL)
    private  Payment payment;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<TransHistory> transHistories;

}
