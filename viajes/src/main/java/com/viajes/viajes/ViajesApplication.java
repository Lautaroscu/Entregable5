package com.viajes.viajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ViajesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ViajesApplication.class, args);
    }
}
