package com.example.demo.controller;

import com.example.demo.entity.ServiceType;
import com.example.demo.payload.ApiResponse;
import com.example.demo.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servicetype")
public class ServiceTypeController {
    @Autowired
    ServiceTypeService serviceTypeService;


    @PostMapping
    public HttpEntity<?> addServiceType(@RequestBody ServiceType serviceType){
        ApiResponse apiResponse = serviceTypeService.addServiceType(serviceType);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


    @PutMapping("/{id}")
    public HttpEntity<?> editServiceType(@PathVariable Integer id,@RequestBody ServiceType serviceType){
        ApiResponse apiResponse = serviceTypeService.editServiceType(id, serviceType);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        ApiResponse apiResponse = serviceTypeService.getAll();
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteServiceType(@PathVariable Integer id){
        ApiResponse apiResponse = serviceTypeService.deleteServiceType(id);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

}
