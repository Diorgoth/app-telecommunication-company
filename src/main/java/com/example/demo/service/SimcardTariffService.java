package com.example.demo.service;


import com.example.demo.entity.*;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.SimcardTariffDto;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SimcardTariffService {

    @Autowired
    SimcardRepository simcardRepository;
    @Autowired
    SimcardTariffRepository simcardTariffRepository;
    @Autowired
    TransHistoryRepository transHistoryRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    TariffRepository tariffRepository;
    @Autowired
    ServiceTypeRepository serviceTypeRepository;



public ApiResponse SimcardTariffTrans(SimcardTariffDto simcardTariffDto){

    User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    for (Role role : userDetails.getRoles()) {

        if (role.getRoleName().name().equals("CUSTOMER")) {

            Optional<SimCard> optionalSimCard = simcardRepository.findById(simcardTariffDto.getSimCardId());
            if (!optionalSimCard.isPresent()){
                return new ApiResponse("Such Simcard not found",false);
            }

            User user = optionalSimCard.get().getUser();
            if (!user.getEmail().equals(userDetails.getEmail())){
                return new ApiResponse("You are only able activate new tariff to your own simcard ",false);
            }

            Optional<Tariff> optionalTariff = tariffRepository.findById(simcardTariffDto.getTariffId());
            if (!optionalTariff.isPresent()){
                return new ApiResponse("Such Tariff not found",false);
            }

            Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(simcardTariffDto.getServiceType());
            if (!optionalServiceType.isPresent()){
                return new ApiResponse("Such Service type not found",false);
            }


            if (optionalSimCard.get().getBalance() < optionalTariff.get().getPrice()){
                return new ApiResponse("Not enough balance to activate this Tariff",false);
            }


            Payment payment = new Payment();
            payment.setPaymentAmount(simcardTariffDto.getPaymentAmount());
            payment.setPaymentType(simcardTariffDto.getPaymentType());
            payment.setUser(userDetails);

            SimCard simcard = optionalSimCard.get();

           double balance;
                  balance  = simcard.getBalance() - optionalTariff.get().getPrice();
                  simcard.setBalance(balance);



            TransHistory transHistory = new TransHistory();
            transHistory.setUser(userDetails);
            transHistory.setAmount(simcardTariffDto.getPaymentAmount());
            transHistory.setServiceType(optionalServiceType.get());

            List<TransHistory> transHistories = new ArrayList<>();
            transHistories.add(transHistory);



            SimCardTariff simCardTariff = new SimCardTariff();
            simCardTariff.setSimCard(optionalSimCard.get());
            simCardTariff.setTariff(optionalTariff.get());
            simCardTariff.setStatus(true);
            simCardTariff.setPayment(payment);
            simCardTariff.setTransHistories(transHistories);
            simcardTariffRepository.save(simCardTariff);

            return new ApiResponse("Tariff activated",true);

        }
    }
    return new ApiResponse("You are not eligible for this action",false);
}

}
