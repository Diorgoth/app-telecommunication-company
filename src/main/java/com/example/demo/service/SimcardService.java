package com.example.demo.service;


import com.example.demo.entity.*;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.SimcardDto;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.PassportRepository;
import com.example.demo.repository.SimcardRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class SimcardService {

    @Autowired
    SimcardRepository simcardRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PassportRepository passportRepository;



    public   ApiResponse addSimcard(SimcardDto simcardDto){

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role:userDetails.getRoles()){
            if (role.getRoleName().name().equals("PHONE_NUMBER_MANAGER")){

                Optional<User> optionalUser = userRepository.findById(simcardDto.getUserId());
                if (!optionalUser.isPresent()){
                    return new ApiResponse("Such User not found",false);
                }


                Optional<Company> optionalCompany = companyRepository.findById(simcardDto.getCompanyId());
                if (!optionalCompany.isPresent()){
                    return new ApiResponse("Such company not found",false);
                }

                if (!userDetails.getCompany().equals(optionalCompany.get())){
                    return new ApiResponse("You are not member of this company",false);
                }

                Passport passport = new Passport();
                passport.setCustomerType(simcardDto.getCustomerType());
                passport.setFirstName(simcardDto.getFirstName());
                passport.setLastName(simcardDto.getLastName());



                SimCard simCard = new SimCard();
                simCard.setPhoneNumber(simcardDto.getPhoneNumber());
                simCard.setBalance(simcardDto.getBalance());
                simCard.setUser(optionalUser.get());
                simCard.setCompany(optionalCompany.get());
                simCard.setPassport(passport);

                simcardRepository.save(simCard);

                return new ApiResponse("Simcard added",true);

            }
        }

        return new ApiResponse("You are not eligible for this action",false);

    }

}
