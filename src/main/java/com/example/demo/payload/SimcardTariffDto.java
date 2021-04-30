package com.example.demo.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardTariffDto {


    private Integer simCardId;


    private Integer tariffId;


    private boolean status;


    private String paymentType;


    private double paymentAmount;


    private Integer serviceType;




}
