package com.example.demo.service;


import com.example.demo.entity.Address;
import com.example.demo.entity.Company;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.CompanyDto;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;


    public ApiResponse addCompany(CompanyDto companyDto){

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role:userDetails.getRoles()){
           if (role.getLevel().equals(1)){
               Company company = new Company();

               if (companyDto.getParentId() != null) {
                   Optional<Company> optionalParentCompany = companyRepository.findById(companyDto.getParentId());
                   if (!optionalParentCompany.isPresent())
                       return new ApiResponse("Parent Company id was not found!", false);

                   company.setParentId(optionalParentCompany.get());
               }


               Address address = new Address();
               address.setStreet(companyDto.getStreet());
               address.setDistrict(companyDto.getDistrict());
               address.setRegion(companyDto.getRegion());


               company.setAddress(address);
               company.setName(companyDto.getName());
               companyRepository.save(company);
               return new ApiResponse("Company added",true);
           }
       }

        return new ApiResponse("You are not eligible for this action",false);

    }


    public ApiResponse editCompany(Integer id,CompanyDto companyDto){

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role: userDetails.getRoles()){
            if (role.getLevel().equals(1)){

                Optional<Company> optionalCompany = companyRepository.findById(id);
                if (!optionalCompany.isPresent()){
                    return new ApiResponse("Such company not found",false);
                }

                Company company = optionalCompany.get();

                Address address = company.getAddress();

                address.setRegion(companyDto.getRegion());
                address.setStreet(companyDto.getStreet());
                address.setDistrict(companyDto.getDistrict());

                company.setName(companyDto.getName());

                companyRepository.save(company);

                return new ApiResponse("Company edited",true);

            }
        }
    return new ApiResponse("You are not eligible for this action",false);

    }


    public ApiResponse getAll(){

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role: userDetails.getRoles()){

            if (role.getLevel().equals(1)){

                List<Company> companyList = companyRepository.findAll();

                return new ApiResponse("Here companies",true,companyList);

            }

        }

        return new ApiResponse("You are not eligible for this action",false);

    }

    public ApiResponse getOne(Integer id){

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role: userDetails.getRoles()) {

            if (role.getLevel().equals(1)) {

                Optional<Company> optionalCompany = companyRepository.findById(id);
                if (!optionalCompany.isPresent()){
                    return new ApiResponse("Company not found",false);
                }

            }

        }
        return new ApiResponse("You are not eligible for this action",false);

    }


    public ApiResponse deleteOne(Integer id){

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role: userDetails.getRoles()) {

            if (role.getLevel().equals(1)) {

                Optional<Company> optionalCompany = companyRepository.findById(id);
                if (!optionalCompany.isPresent()){
                    return new ApiResponse("Company not found",false);
                }

                companyRepository.deleteById(id);
                return new ApiResponse("Company deleted",true);

            }
        }
        return new ApiResponse("You are not eligible for this action",false);

    }
}
