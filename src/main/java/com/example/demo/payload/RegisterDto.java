package com.example.demo.payload;

import com.example.demo.entity.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class RegisterDto {



    @Size(min = 3,max = 50)
    private String firstName;


    @Length(min = 3,max = 50)
    private String lastName;



    @Email
    private String email;

    private String password;

    private Integer companyId;

    private Set<Role> roleIdList;

}
