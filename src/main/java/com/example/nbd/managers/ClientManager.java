package com.example.nbd.managers;

import com.example.nbd.model.Address;
import com.example.nbd.model.Client;
import com.example.nbd.model.ClientType;
import com.example.nbd.repositories.AdressRepository;
import com.example.nbd.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ClientManager {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AdressRepository adressRepository;

    @Transactional
    public void addClient(){
        Client client = new Client();
        Address address = new Address();
        address.setCity("lodz");
        address.setStreet("wrubla");
        address.setHouseNumber("2137");
        adressRepository.save(address);

        client.setClientType(ClientType.SILVER);
        client.setAddress(address);
        client.setFirstName("git");
        client.setLastName("dzialajacy");
        //if(clientRepository.exists())
        clientRepository.save(client);
        clientRepository.findAll().forEach(c -> System.out.println(c.toString()));
    }
    @Transactional
    public void deleteClient(Long id) {

    }
    @Transactional
    public void deleteClient(Client client) {

    }

}
