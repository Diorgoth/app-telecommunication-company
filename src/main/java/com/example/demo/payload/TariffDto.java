package com.example.demo.payload;

import com.example.demo.entity.CustomerType.CustomerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffDto {


    private CustomerType tariffType;// Tariflar ham ikki turga bo`linadi: 1  Jismoniy shaxslar uchun;2 Yuridik shaxslar uchun.


    private String title;


    private String description;


    private double price;


    private String ussdCode;


    private String ussDdescription;


    private Integer serviceType;


    private double amount;



}
