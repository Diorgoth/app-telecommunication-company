package com.example.demo.controller;

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.SimcardTariffDto;
import com.example.demo.service.SimcardTariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/simcardtariff")
public class SimcardTariffController {
    @Autowired
    SimcardTariffService simcardTariffService;

    @PostMapping
    public HttpEntity<?> simcardTarifftrans(@RequestBody SimcardTariffDto simcardTariffDto){

        ApiResponse apiResponse = simcardTariffService.SimcardTariffTrans(simcardTariffDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

}
