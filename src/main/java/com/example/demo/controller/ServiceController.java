package com.example.demo.controller;

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.ServiceDto;
import com.example.demo.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    @Autowired
    ServiceService service;

    @PostMapping
    public HttpEntity<?> addService(@RequestBody ServiceDto serviceDto){

        ApiResponse apiResponse = service.addService(serviceDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);

    }

    @PutMapping("/{id}")
    public HttpEntity<?> editService(@PathVariable Integer id, @RequestBody ServiceDto serviceDto){
        ApiResponse apiResponse = service.editService(id, serviceDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }



}
