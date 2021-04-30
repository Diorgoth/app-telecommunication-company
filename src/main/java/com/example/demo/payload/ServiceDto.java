package com.example.demo.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {



    private String serviceName;

    private String description;

    private Double price;

    private String ussdCode;

    private String ussDdescription;

    private Integer serviceType;

    private double amount;


}
