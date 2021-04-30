package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SimCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 13,max = 13)
    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private double balance;

    @ManyToOne(cascade = CascadeType.ALL)
    private Passport passport;

    @ManyToOne
    private Company company;

    public void setPhoneNumber(String phoneNumber) {
        String code = "+998";

        if (phoneNumber.substring(0,2).equals("93") || phoneNumber.substring(0,2).equals("94"))
            this.phoneNumber = code+phoneNumber;
    }
}
