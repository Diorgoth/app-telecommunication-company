package com.example.demo.controller;

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.CompanyDto;
import com.example.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController {


    @Autowired
    CompanyService companyService;

    @PostMapping("/add")
    public HttpEntity<?> addCompany(@RequestBody CompanyDto companyDto){

        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);

    }

    @PutMapping("/edit/{id}")
    public HttpEntity<?> editCompany(@PathVariable Integer id,@RequestBody CompanyDto companyDto){

        ApiResponse apiResponse = companyService.editCompany(id, companyDto);

        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


    @GetMapping("/get")
    public HttpEntity<?> getCompany(){

        ApiResponse apiResponse = companyService.getAll();
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @GetMapping("/get/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteCompany(@PathVariable Integer id){

        ApiResponse apiResponse = companyService.deleteOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);

    }

}
