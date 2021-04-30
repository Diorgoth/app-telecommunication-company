package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.TariffDto;
import com.example.demo.repository.DetailRepository;
import com.example.demo.repository.ServiceTypeRepository;
import com.example.demo.repository.TariffRepository;
import com.example.demo.repository.USSDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TariffService {

    @Autowired
    ServiceTypeRepository serviceTypeRepository;
    @Autowired
    DetailRepository detailRepository;
    @Autowired
    USSDRepository ussdRepository;
    @Autowired
    TariffRepository tariffRepository;



    public ApiResponse addTariff(TariffDto tariffDto){
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role : userDetails.getRoles()) {

            if (role.getRoleName().name().equals("TARIFF_MANAGER")) {

                Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(tariffDto.getServiceType());
                if (!optionalServiceType.isPresent()){
                    return new ApiResponse("Such Service type not found",false);
                }

                USSD ussd = new USSD();
                ussd.setDescription(tariffDto.getUssDdescription());
                ussd.setUssdCode(tariffDto.getUssdCode());

                List<USSD> ussdList = new ArrayList<>();
                ussdList.add(ussd);


                Detail detail = new Detail();
                detail.setServiceType(optionalServiceType.get());
                detail.setAmount(tariffDto.getAmount());
                List<Detail> detailList = new ArrayList<>();
                detailList.add(detail);

                Tariff tariff = new Tariff();
                tariff.setTariffType(tariffDto.getTariffType());
                tariff.setDescription(tariffDto.getDescription());
                tariff.setPrice(tariffDto.getPrice());
                tariff.setTitle(tariffDto.getTitle());
                tariff.setDetails(detailList);
                tariff.setUssds(ussdList);

                tariffRepository.save(tariff);

                return new ApiResponse("Tariff added",true);


            }
        }
        return new ApiResponse("You are not eligible for this action",false);
    }



    public  ApiResponse editTariff(Integer id,TariffDto tariffDto){
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role : userDetails.getRoles()) {

            if (role.getRoleName().name().equals("TARIFF_MANAGER")) {

                Optional<Tariff> optionalTariff = tariffRepository.findById(id);
                if (!optionalTariff.isPresent()){
                    return new ApiResponse("Such tariff not found",false);
                }

                Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(tariffDto.getServiceType());
                if (!optionalServiceType.isPresent()){
                    return new ApiResponse("Such Service type not found",false);
                }

                USSD ussd = new USSD();
                ussd.setDescription(tariffDto.getUssDdescription());
                ussd.setUssdCode(tariffDto.getUssdCode());

                List<USSD> ussdList = optionalTariff.get().getUssds();
                ussdList.add(ussd);


                Detail detail = new Detail();
                detail.setServiceType(optionalServiceType.get());
                detail.setAmount(tariffDto.getAmount());
                List<Detail> detailList = optionalTariff.get().getDetails();
                detailList.add(detail);

                Tariff tariff = optionalTariff.get();
                tariff.setTariffType(tariffDto.getTariffType());
                tariff.setDescription(tariffDto.getDescription());
                tariff.setPrice(tariffDto.getPrice());
                tariff.setTitle(tariffDto.getTitle());
                tariff.setDetails(detailList);
                tariff.setUssds(ussdList);

                tariffRepository.save(tariff);

                return new ApiResponse("Tariff edited",true);

            }
        }
        return new ApiResponse("You are not eligible for this action",false);
    }



    public ApiResponse getAll(){
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role : userDetails.getRoles()) {

            if (role.getRoleName().name().equals("TARIFF_MANAGER")) {

                List<Tariff> tariffList = tariffRepository.findAll();
                return new ApiResponse("Here's Tariffs",true,tariffList);

            }
        }
        return new ApiResponse("You are not eligible for this action",false);
    }



    public ApiResponse deleteTariff(Integer id){
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role : userDetails.getRoles()) {

            if (role.getRoleName().name().equals("TARIFF_MANAGER")) {

                Optional<Tariff> optionalTariff = tariffRepository.findById(id);
                if (!optionalTariff.isPresent()){
                    return new ApiResponse("Such Tariff not found",false);
                }

                tariffRepository.deleteById(id);

                return new ApiResponse("Tariff deleted",true);

            }
        }
        return new ApiResponse("You are not eligible for this action",false);
    }

}
