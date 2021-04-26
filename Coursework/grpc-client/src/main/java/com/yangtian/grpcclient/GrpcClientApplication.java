package com.yangtian.grpcclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class GrpcClientApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }

}
