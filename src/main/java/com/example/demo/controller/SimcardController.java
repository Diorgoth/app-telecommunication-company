package com.example.demo.controller;


import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.SimcardDto;
import com.example.demo.service.SimcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SimcardController {

    @Autowired
    SimcardService simcardService;


    @PostMapping("/addsimcard")
    public HttpEntity<?> addSimcard(@RequestBody SimcardDto simcardDto){

        ApiResponse apiResponse = simcardService.addSimcard(simcardDto);

        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

}
