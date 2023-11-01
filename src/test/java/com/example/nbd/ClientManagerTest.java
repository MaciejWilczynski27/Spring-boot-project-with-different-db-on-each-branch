package com.example.nbd;

import com.example.nbd.exceptions.DuplicateRecordException;
import com.example.nbd.managers.ClientManager;
import com.example.nbd.model.enums.ClientType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ClientManagerTest {
    @Autowired
    ClientManager clientManager;

    private void addClients() throws DuplicateRecordException {
        clientManager.addClient("Mariusz","Pudzianowski", ClientType.GOLD,"Lodz","Aleje Politechniki","4/7");
        clientManager.addClient("Adam","Małysz",ClientType.BRONZE,"Warszawa","Nowy Swiat","7");

    }
    @Test
    @Transactional
    void cantAddClientWithSameNameAndSurnameAndAddressTest() throws DuplicateRecordException {
        addClients();
        try {
            clientManager.addClient("Mariusz","Pudzianowski", ClientType.GOLD,"Lodz","Aleje Politechniki","4/7");
            Assertions.fail("Exception was not caught");
        } catch (DuplicateRecordException e){
            Assertions.assertThat(e.getMessage().equals("Record already exists")).isTrue();
        }
    }
    @Test
    @Transactional
    void addClientTest() throws DuplicateRecordException {
        int buffer = clientManager.findAllClients().size();
        addClients();
        Assertions.assertThat(clientManager.findAllClients().size() == buffer + 2).isTrue();
    }

    @Test
    @Transactional
    void deleteClientTest() throws DuplicateRecordException {
        addClients();
        int buffer = clientManager.findAllClients().size();
        String idBuffer = clientManager.findAllClients().get(0).getId();
        clientManager.deleteClient(idBuffer);
        Assertions.assertThat(clientManager.findAllClients().size() == buffer - 1).isTrue();
        Assertions.assertThat(clientManager.findClientById(idBuffer) == null).isTrue();
    }
    @Test
    @Transactional
    void findClientTest() throws DuplicateRecordException {
        addClients();
        Assertions.assertThat(clientManager.findAllClients().get(0)
                .equals(clientManager.findClientById(clientManager.findAllClients().get(0).getId()))).isTrue();
    }
    @Test
    @Transactional
    void updateClientTypeTest() throws DuplicateRecordException {
        addClients();
        String idBuffer = clientManager.findAllClients().get(0).getId();
        clientManager.updateClientType(idBuffer,ClientType.DIAMOND);
        Assertions.assertThat(clientManager.findClientById(idBuffer).getClientType().equals(ClientType.DIAMOND)).isTrue();
        clientManager.updateClientType(idBuffer,ClientType.BRONZE);
        Assertions.assertThat(clientManager.findClientById(idBuffer).getClientType().equals(ClientType.BRONZE)).isTrue();
    }
    @Test
    @Transactional
    void updateFirstNameAndLastNameTest() throws DuplicateRecordException {
        addClients();
        String idBuffer = clientManager.findAllClients().get(0).getId();
        clientManager.updateClientfirstNameAndLastName(idBuffer,"Jan","Kowalski");
        Assertions.assertThat(clientManager.findClientById(idBuffer).getFirstName().equals("Jan")).isTrue();
        Assertions.assertThat(clientManager.findClientById(idBuffer).getLastName().equals("Kowalski")).isTrue();
        clientManager.updateClientfirstNameAndLastName(idBuffer,"Mariusz","Pudzianowski");
        Assertions.assertThat(clientManager.findClientById(idBuffer).getFirstName().equals("Mariusz")).isTrue();
        Assertions.assertThat(clientManager.findClientById(idBuffer).getLastName().equals("Pudzianowski")).isTrue();
    }
    @Test
    @Transactional
    void updateAddressTest() throws DuplicateRecordException {
        addClients();
        String idBuffer = clientManager.findAllClients().get(0).getId();
        clientManager.updateClientAddress(idBuffer,"Warszawa","Nowy Swiat","7");
        Assertions.assertThat(clientManager.findClientById(idBuffer).getAddress().getCity().equals("Warszawa")).isTrue();
        Assertions.assertThat(clientManager.findClientById(idBuffer).getAddress().getStreet().equals("Nowy Swiat")).isTrue();
        Assertions.assertThat(clientManager.findClientById(idBuffer).getAddress().getHouseNumber().equals("7")).isTrue();
        clientManager.updateClientAddress(idBuffer,"Lodz","Aleje Politechniki","2/7");
        Assertions.assertThat(clientManager.findClientById(idBuffer).getAddress().getCity().equals("Lodz")).isTrue();
        Assertions.assertThat(clientManager.findClientById(idBuffer).getAddress().getStreet().equals("Aleje Politechniki")).isTrue();
        Assertions.assertThat(clientManager.findClientById(idBuffer).getAddress().getHouseNumber().equals("2/7")).isTrue();
    }
}
