package com.example.nbd.managers;

import com.example.nbd.exceptions.DuplicateRecordException;
import com.example.nbd.model.Address;
import com.example.nbd.model.Client;
import com.example.nbd.model.Rent;
import com.example.nbd.model.enums.ClientType;
import com.example.nbd.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ)
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
        client.setClientType(clientType);
        client.setAddress(address);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setActive(true);
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
      clientOpt.ifPresent(value -> value.getActiveRents().add(rent.getRentId()));
    }
    public void updateClientfirstNameAndLastName(String id, String firstName, String lastName) {
        var clientOpt = clientRepository.findById(id);
        clientOpt.ifPresent(value -> {
            value.setFirstName(firstName);
            value.setLastName(lastName);
        });
    }
    public void updateClientAddress(String id, String city, String street, String houseNumber) {
        var clientOpt = clientRepository.findById(id);
        clientOpt.ifPresent(value -> {
            value.getAddress().setCity(city);
            value.getAddress().setStreet(street);
            value.getAddress().setHouseNumber(houseNumber);
        });
    }
    public void updateClientType(String id, ClientType clientType) {
        var clientOpt = clientRepository.findById(id);
        clientOpt.ifPresent(value -> value.setClientType(clientType));
    }


}
