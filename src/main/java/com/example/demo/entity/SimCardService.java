package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class SimCardService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private SimCard simCard;

    @ManyToOne
    private Service service;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private  Payment  payments;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<TransHistory> transHistories;

}
