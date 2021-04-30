package com.example.demo.entity;

import com.example.demo.entity.CustomerType.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 50)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private CustomerType customerType;

}
