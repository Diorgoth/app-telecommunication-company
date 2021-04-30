package com.example.demo.controller;

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.TariffDto;
import com.example.demo.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tariff")
public class TariffController {
    @Autowired
    TariffService tariffService;


    @PostMapping
    public HttpEntity<?> addTariff(@RequestBody TariffDto tariffDto){

        ApiResponse apiResponse = tariffService.addTariff(tariffDto);

        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);

    }

    @PutMapping("/{id}")
    public HttpEntity<?> editTariff(@PathVariable Integer id,@RequestBody TariffDto tariffDto){

        ApiResponse apiResponse = tariffService.editTariff(id, tariffDto);

        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);

    }

    @GetMapping
    public HttpEntity<?> getAll(){

        ApiResponse apiResponse = tariffService.getAll();

        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);

    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTariff(@PathVariable Integer id){

        ApiResponse apiResponse = tariffService.deleteTariff(id);

        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);

    }


}
