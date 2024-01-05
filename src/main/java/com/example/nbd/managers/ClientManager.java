package com.example.nbd.managers;

import com.example.nbd.exceptions.DuplicateRecordException;
import com.example.nbd.model.Address;
import com.example.nbd.model.Client;
import com.example.nbd.model.Rent;
import com.example.nbd.model.enums.ClientType;
import com.example.nbd.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.CassandraConnectionFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientManager {


    private final ClientRepository clientRepository;


    public void addClient(String firstName,String lastName, ClientType clientType,
                          String city, String street, String houseNumber) throws DuplicateRecordException {
        Client client = new Client();
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setHouseNumber(houseNumber);
        client.setId(UUID.randomUUID().toString());
        client.setClientType(clientType);
        client.setAddress(address);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setActive(true);
        client.setActiveRents(new ArrayList<>());
        if(clientRepository
                .existsByFirstNameAndLastNameAndAddress_CityAndAddress_StreetAndAddress_HouseNumber(client.getFirstName()
                ,client.getLastName()
                ,client.getAddress().getCity()
                ,client.getAddress().getStreet()
                ,client.getAddress().getHouseNumber())){
            throw new DuplicateRecordException();
        }
        clientRepository.save(client);
    }

    public void deleteClient(String id) {
        clientRepository.deleteById(id);
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }
    public Client findClientById(String id) {
        return clientRepository.findById(id).orElse(null);
    }
    public void addRent(Client client, Rent rent) {
      var clientOpt =  clientRepository.findById(client.getId());
      clientOpt.ifPresent(value -> {
          value.getActiveRents().add(rent.getRentId());
          clientRepository.save(value);
      });
    }
    public void updateClientfirstNameAndLastName(String id, String firstName, String lastName) {
        var clientOpt = clientRepository.findById(id);
        clientOpt.ifPresent(value -> {
            value.setFirstName(firstName);
            value.setLastName(lastName);
            clientRepository.save(value);
        });
    }
    public void updateClientAddress(String id, String city, String street, String houseNumber) {
        var clientOpt = clientRepository.findById(id);
        clientOpt.ifPresent(value -> {
            value.getAddress().setCity(city);
            value.getAddress().setStreet(street);
            value.getAddress().setHouseNumber(houseNumber);
            clientRepository.save(value);
        });
    }
    public void updateClientType(String id, ClientType clientType) {
        var clientOpt = clientRepository.findById(id);
        clientOpt.ifPresent(value -> {
            value.setClientType(clientType);
            clientRepository.save(value);
        });
    }
    public void setActive(String id, boolean active) {
        var clientOpt = clientRepository.findById(id);
        clientOpt.ifPresent(value -> {
            value.setActive(active);
            clientRepository.save(value);
        });
    }


}
