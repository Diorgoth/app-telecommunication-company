package com.example.demo.config;


import com.example.demo.security.JwtFilter;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthService authService;
    @Autowired
    JwtFilter jwtFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http
                 .csrf().disable()
                 .authorizeRequests()
                 .antMatchers("/api/simcardservice","/api/service","/api/tariff","/api/simcardtariff","/api/auth/register","/api/auth/verifyEmail","/api/auth/login","/api/addtask","/api/giventask","/api/taskstatuschange","/api/add","/api/addsimcard","/api/addcompany","/api/servicetype").permitAll()
                 .anyRequest().authenticated();
         http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();

    }


    @Bean
    public JavaMailSender javaMailSender(){

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("supremediyorbek4013@gmail.com");
        mailSender.setPassword("supreme4013");
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.debug","true");

        return mailSender;

    }

}
