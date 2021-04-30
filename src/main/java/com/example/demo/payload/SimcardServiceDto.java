package com.example.demo.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardServiceDto {



    private Integer simCardId;

    private Integer serviceId;

    private boolean status;

    private Date startDate;

    private Date endDate;

    private String paymentType;

    private double paymentAmount;

    private Integer serviceType;


}
