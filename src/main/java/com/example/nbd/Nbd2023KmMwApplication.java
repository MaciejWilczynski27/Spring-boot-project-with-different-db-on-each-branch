package com.example.nbd;


import com.example.nbd.repositories.ClientRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class Nbd2023KmMwApplication {


    public static void main(String[] args) {
        ClientRepository clientRepository;
        SpringApplication.run(Nbd2023KmMwApplication.class, args);
        
        
    }

}
