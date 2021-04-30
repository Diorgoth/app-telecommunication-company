package com.example.demo.payload;


import com.example.demo.entity.CustomerType.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardDto {

    private String phoneNumber;


    private UUID userId;


    private Double balance;


    private String firstName;


    private String lastName;


    private CustomerType customerType;

    private Integer companyId;
}
