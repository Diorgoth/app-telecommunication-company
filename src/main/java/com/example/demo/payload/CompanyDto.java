package com.example.demo.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {




    private String name;


    private String district;


    private String street;


    private String region;


    private Integer parentId;


}
