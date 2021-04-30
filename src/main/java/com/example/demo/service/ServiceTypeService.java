package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.ServiceType;
import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceTypeService {

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    public ApiResponse addServiceType(ServiceType serviceType){
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role : userDetails.getRoles()){

            if (role.getRoleName().name().equals("SERVICE_MANAGER")){


                serviceTypeRepository.save(serviceType);

                  return new ApiResponse("Service type added",true);

            }
        }
        return new ApiResponse("You are not eligible for this action",false);

    }


    public ApiResponse editServiceType(Integer id,ServiceType serviceType){
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role : userDetails.getRoles()) {

            if (role.getRoleName().name().equals("SERVICE_MANAGER")) {

                Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(id);
                if (!optionalServiceType.isPresent()){
                    return new ApiResponse("Such service not found",false);
                }

                ServiceType serviceType1 = optionalServiceType.get();
                serviceType1.setDescription(serviceType.getDescription());
                serviceType1.setName(serviceType.getName());
                return new ApiResponse("Service type edited",true);

            }
        }
        return new ApiResponse("You are not eligible for this action",false);
    }


    public ApiResponse getAll(){

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role : userDetails.getRoles()) {

            if (role.getRoleName().name().equals("SERVICE_MANAGER")) {

                List<ServiceType> serviceTypes = serviceTypeRepository.findAll();

                return new ApiResponse("Here service types",true,serviceTypes);

            }
        }
        return new ApiResponse("You are not eligible for this action",false);
    }


    public ApiResponse deleteServiceType(Integer id){

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (Role role : userDetails.getRoles()) {

            if (role.getRoleName().name().equals("SERVICE_MANAGER")) {

                Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(id);
                if (!optionalServiceType.isPresent()){
                    return new ApiResponse("Such Service not find",false);
                }

                serviceTypeRepository.deleteById(id);
                return new ApiResponse("Service type deleted",true);

            }
        }
        return new ApiResponse("You are not eligible for this action",false);
    }

}
