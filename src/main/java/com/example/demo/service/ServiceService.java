package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.ServiceDto;
import com.example.demo.repository.DetailRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.ServiceTypeRepository;
import com.example.demo.repository.USSDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    @Autowired
    ServiceTypeRepository serviceTypeRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    DetailRepository detailRepository;
    @Autowired
    USSDRepository ussdRepository;


    public ApiResponse addService(ServiceDto serviceDto){

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role : userDetails.getRoles()) {

            if (role.getRoleName().name().equals("SERVICE_MANAGER")) {

                Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(serviceDto.getServiceType());
                if (!optionalServiceType.isPresent()){
                    return new ApiResponse("Such Service type not found",false);
                }


                USSD ussd = new USSD();
                ussd.setDescription(serviceDto.getUssDdescription());
                ussd.setUssdCode(serviceDto.getUssdCode());

                List<USSD> ussdList = new ArrayList<>();
                ussdList.add(ussd);


                Detail detail = new Detail();
                detail.setServiceType(optionalServiceType.get());
                detail.setAmount(serviceDto.getAmount());
                List<Detail> detailList = new ArrayList<>();
                detailList.add(detail);

                com.example.demo.entity.Service service = new com.example.demo.entity.Service();
                service.setServiceName(serviceDto.getServiceName());
                service.setDescription(serviceDto.getDescription());
                service.setPrice(serviceDto.getPrice());
                service.setUssdList(ussdList);
                service.setDetails(detailList);

                serviceRepository.save(service);

                return new ApiResponse("New service added",true);

            }
        }
        return new ApiResponse("You are not eligible for this action",false);
    }


    public ApiResponse editService(Integer id,ServiceDto serviceDto){

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role : userDetails.getRoles()) {

            if (role.getRoleName().name().equals("SERVICE_MANAGER")) {

                Optional<com.example.demo.entity.Service> optionalService = serviceRepository.findById(id);
                if (!optionalService.isPresent()){
                    return new ApiResponse("Such service not found",false);
                }

                Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(serviceDto.getServiceType());
                if (!optionalServiceType.isPresent()){
                    return new ApiResponse("Such Service type not found",false);
                }


                com.example.demo.entity.Service service = optionalService.get();
                service.setServiceName(serviceDto.getServiceName());
                service.setDescription(serviceDto.getDescription());
                service.setPrice(serviceDto.getPrice());


                USSD ussd = new USSD();
                ussd.setDescription(serviceDto.getUssDdescription());
                ussd.setUssdCode(serviceDto.getUssdCode());

                List<USSD> ussdList = service.getUssdList();
                ussdList.add(ussd);


                Detail detail = new Detail();
                detail.setServiceType(optionalServiceType.get());
                detail.setAmount(serviceDto.getAmount());
                List<Detail> detailList = service.getDetails();
                detailList.add(detail);

                service.setUssdList(ussdList);
                service.setDetails(detailList);



                serviceRepository.save(service);

                return new ApiResponse("Service edited",true);


            }
        }
        return new ApiResponse("You are not eligible for this action",false);
    }

}
