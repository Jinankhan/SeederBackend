package com.seeder.cashkickservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class CashkickServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CashkickServiceApplication.class, args);
    }

}