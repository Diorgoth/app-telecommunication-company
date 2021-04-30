
package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.SimcardServiceDto;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimcardServiceService {

    @Autowired
    SimcardRepository simcardRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    TransHistoryRepository transHistoryRepository;
    @Autowired
    ServiceTypeRepository serviceTypeRepository;
    @Autowired
    SimcardServiceRepository simcardServiceRepository;

    public ApiResponse SimcardServiceTrans(SimcardServiceDto serviceDto){

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role : userDetails.getRoles()) {

            if (role.getRoleName().name().equals("CUSTOMER")) {

                Optional<SimCard> optionalSimCard = simcardRepository.findById(serviceDto.getSimCardId());
                if (!optionalSimCard.isPresent()){
                    return new ApiResponse("Such Simcard not found",false);
                }

                User user = optionalSimCard.get().getUser();
                if (!user.getEmail().equals(userDetails.getEmail())){
                    return new ApiResponse("You are only able activate new tariff to your own simcard ",false);
                }


                Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(serviceDto.getServiceType());
                if (!optionalServiceType.isPresent()){
                    return new ApiResponse("Such Service type not found",false);
                }

                Optional<com.example.demo.entity.Service> optionalService = serviceRepository.findById(serviceDto.getServiceId());
                if (!optionalService.isPresent()){
                    return new ApiResponse("Such service not found",false);
                }

                if (optionalSimCard.get().getBalance() < optionalService.get().getPrice()){
                    return new ApiResponse("Not enough balance to activate this Service",false);
                }


                Payment payment = new Payment();
                payment.setPaymentAmount(serviceDto.getPaymentAmount());
                payment.setPaymentType(serviceDto.getPaymentType());
                payment.setUser(userDetails);

                SimCard simcard = optionalSimCard.get();

                double balance;
                balance  = simcard.getBalance() - optionalService.get().getPrice();
                simcard.setBalance(balance);



                TransHistory transHistory = new TransHistory();
                transHistory.setUser(userDetails);
                transHistory.setAmount(serviceDto.getPaymentAmount());
                transHistory.setServiceType(optionalServiceType.get());

                List<TransHistory> transHistories = new ArrayList<>();
                transHistories.add(transHistory);


                SimCardService simCardService = new SimCardService();
                simCardService.setService(optionalService.get());
                simCardService.setStartDate(serviceDto.getStartDate());
                simCardService.setEndDate(serviceDto.getEndDate());
                simCardService.setPayments(payment);
                simCardService.setSimCard(optionalSimCard.get());
                simCardService.setStatus(serviceDto.isStatus());
                simCardService.setTransHistories(transHistories);

                simcardServiceRepository.save(simCardService);

                return new ApiResponse("New service activated",true);

            }
        }
        return new ApiResponse("You are not eligible for this action",false);
    }

}
