package com.parental.control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ParentalControlApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ParentalControlApplication.class, args);
    }

}
