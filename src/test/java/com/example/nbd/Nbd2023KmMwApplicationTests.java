package com.example.nbd;

import com.example.nbd.managers.ClientManager;
import com.example.nbd.model.Client;
import com.example.nbd.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
class Nbd2023KmMwApplicationTests {
    @Autowired
    ClientManager clientManager;


    @Test
    void contextLoads() {
    }
    @Test
    void clientTest(){

        clientManager.addClient();
    }

}
