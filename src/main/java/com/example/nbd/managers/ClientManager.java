package com.example.nbd.managers;

import com.example.nbd.exceptions.DuplicateRecordException;
import com.example.nbd.model.Address;
import com.example.nbd.model.Client;
import com.example.nbd.model.Rent;
import com.example.nbd.model.enums.ClientType;
import com.example.nbd.repositories.AddressRepository;
import com.example.nbd.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@Transactional
public class ClientManager {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AddressRepository adressRepository;

    public void addClient(String firstName,String lastName, ClientType clientType,
                          String city, String street, String houseNumber) throws DuplicateRecordException {
        Client client = new Client();
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setHouseNumber(houseNumber);
        adressRepository.save(address);
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

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }
    public Client findClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
    public void addRent(Client client, Rent rent) {
      var clientOpt =  clientRepository.findById(client.getId());
      clientOpt.ifPresent(value -> value.getActiveRents().add(rent));
    }

}
