package com.example.demo.controller;

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.SimcardServiceDto;
import com.example.demo.service.SimcardServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/simcardservice")
public class SimcardServiceController {

    @Autowired
    SimcardServiceService service;


    @PostMapping
    public HttpEntity<?> simcardServiceTrans(@RequestBody SimcardServiceDto serviceDto){
        ApiResponse apiResponse = service.SimcardServiceTrans(serviceDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


}
