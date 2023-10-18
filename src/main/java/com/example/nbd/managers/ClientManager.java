package com.example.nbd.managers;

import com.example.nbd.model.Address;
import com.example.nbd.model.Client;
import com.example.nbd.model.ClientType;
import com.example.nbd.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientManager {
    ClientRepository clientRepository;
    public void addClient(){
        Client client = new Client();
        Address address = new Address();
        address.setCity("lodz");
        address.setStreet("wrubla");
        address.setHouseNumber("2137");
        client.setClientType(ClientType.SILVER);
        client.setAddress(address);
        client.setFirstName("git");
        client.setLastName("dzialajacy");
        clientRepository.save(client);
    }

}
