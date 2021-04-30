package com.example.demo.service;


import com.example.demo.entity.Company;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.RegisterDto;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtProvider;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CompanyRepository companyRepository;
   static User userDetails;
    public ApiResponse registerUser(RegisterDto registerDto) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         if (authentication.getPrincipal().equals("anonymousUser") ){

            boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());

            if (existsByEmail) {

                return new ApiResponse("Bunday email allaqachon mavjud", false);

            }


            User user = new User();
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setEmail(registerDto.getEmail());
            user.setEmailCode(UUID.randomUUID().toString());
            user.setRoles(registerDto.getRoleIdList());
            userRepository.save(user);
            //EMAILGA YUBORISH METHODINI CHIQARYAPMIZ
            sendEmail(user.getEmail(), user.getEmailCode());
            return new ApiResponse("Muvafaqqiyatli ro'yxatdan o'tdingiz. Akkountning aktivlashtirilishi uchun emailigizni tasdiqlang", true);

        }

            userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


            Set<Role> roles = userDetails.getRoles();
            for (Role roleadder : roles) {

                for (Role role : registerDto.getRoleIdList()) {

                    if (roleadder.getLevel() > role.getLevel()) {

                        return new ApiResponse("Uzur Siz o'zinigizdan katta lavozimdagi user qo'sha olmaysiz", false);

                    } else if (roleadder.getLevel() < role.getLevel()){

                        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());

                        if (existsByEmail) {

                            return new ApiResponse("Bunday email allaqachon mavjud", false);

                        }

                        Optional<Company> optionalCompany = companyRepository.findById(registerDto.getCompanyId());
                        if (!optionalCompany.isPresent()){
                            return new ApiResponse("No such company",false);
                        }

                        User user = new User();
                        user.setFirstName(registerDto.getFirstName());
                        user.setCompany(optionalCompany.get());
                        user.setLastName(registerDto.getLastName());
                        user.setEmail(registerDto.getEmail());
                        user.setEmailCode(UUID.randomUUID().toString());
                        user.setRoles(registerDto.getRoleIdList());
                        userRepository.save(user);
                        //EMAILGA YUBORISH METHODINI CHIQARYAPMIZ
                        sendEmail(user.getEmail(), user.getEmailCode());
                        return new ApiResponse("Muvafaqqiyatli ro'yxatdan o'tdingiz. Akkountning aktivlashtirilishi uchun emailigizni tasdiqlang", true);

                    }

                }

            }

        return new ApiResponse("Role qo'shilmadi",false);

        }



    public Boolean sendEmail(String sendingEmail, String emailCode){

        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom("supremediyorbek4013@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Akkountni tasdiqlash");
            mailMessage.setText("<a href='http://localhost:8091/api/auth/verifyEmail?emailCode="+emailCode+"&email="+sendingEmail+"'>Tasdiqlang</a>");
            javaMailSender.send(mailMessage);
            return true;

        }catch (Exception e){
            System.out.println(e);
            return false;
        }

    }


    public ApiResponse verifyEmail(String emailCode, String email, LoginDto loginDto){

        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);

        if (optionalUser.isPresent()){

            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
            userRepository.save(user);

            return new ApiResponse("Akkount tasdiqlandi",true);

        }

        return new ApiResponse("Akkount allaqachon tasdiqlangan",false);

    }

    public ApiResponse login(LoginDto loginDto){

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()
            ));

            User user = (User) authentication.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(),user.getRoles());
            return new ApiResponse("Token",true,token);



        }catch (BadCredentialsException badCredentialsException){
            System.out.println(badCredentialsException);
         return new ApiResponse("Parol yoki login xato",false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        Optional<User> optionalUser = userRepository.findByEmail(username);
//        if (optionalUser.isPresent())
//            return optionalUser.get();
//            throw new UsernameNotFoundException(username+"topilmadi");

        return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username+"topilmadi"));
    }


    private ApiResponse editUser(UUID id, RegisterDto registerDto){

        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()){
            return new ApiResponse("User not found",false);
        }

        userDetails = (User) SecurityContextHolder.getContext().getAuthentication();
        Set<Role> roles = userDetails.getRoles();
        for (Role role:roles){
            for (Role role1:optionalUser.get().getRoles()){
                if (role.getLevel() <=role1.getLevel()){
                    User user = optionalUser.get();
                    user.setPassword(registerDto.getPassword());
                    user.setEmail(registerDto.getEmail());
                    user.setFirstName(registerDto.getFirstName());
                    user.setLastName(registerDto.getLastName());
                    return new ApiResponse("User edited",true);
                }
            }
        }

        return new ApiResponse("You are not eligible for this action",false);

    }

    private ApiResponse getUser(UUID id){

        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()){
            return new ApiResponse("User not found",false);
        }

        userDetails = (User) SecurityContextHolder.getContext().getAuthentication();
        Set<Role> roles = userDetails.getRoles();
        for (Role role:roles) {
            for (Role role1 : optionalUser.get().getRoles()) {
                if (role.getLevel() <= role1.getLevel()) {

                    return new ApiResponse("Here's user:",true,optionalUser.get());

                }
            }
        }

        return new ApiResponse("You are not eligible for this action",false);

    }
}
