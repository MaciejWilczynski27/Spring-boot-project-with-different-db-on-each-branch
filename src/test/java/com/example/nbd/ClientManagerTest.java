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
    @Test
    @Transactional
    void basicTest() throws DuplicateRecordException {
        int clientBuffer = clientManager.findAllClients().size();
        clientManager.addClient("Mariusz","Pudzianowski", ClientType.GOLD,"Lodz","Aleje Politechniki","4/7");
        try {
            clientManager.addClient("Mariusz","Pudzianowski", ClientType.GOLD,"Lodz","Aleje Politechniki","4/7");
            Assertions.fail("Exception was not caught");
        } catch (DuplicateRecordException e){
            Assertions.assertThat(e.getMessage().equals("Record already exists")).isTrue();
        }
        Assertions.assertThat(clientManager.findAllClients().size() == clientBuffer + 1).isTrue();
        clientManager.addClient("Adam","Małysz",ClientType.BRONZE,"Warszawa","Nowy Swiat","7");
        Assertions.assertThat(clientBuffer + 2 == clientManager.findAllClients().size()).isTrue();
        Assertions.assertThat(clientManager.findAllClients().get(0)
                .equals(clientManager.findClientById(clientManager.findAllClients().get(0).getId()))).isTrue();
        Long bufferedId = clientManager.findAllClients().get(0).getId();
        clientManager.deleteClient(bufferedId);
        Assertions.assertThat(clientBuffer + 1 == clientManager.findAllClients().size()).isTrue();
        Assertions.assertThat(clientManager.findClientById(bufferedId) == null).isTrue();
    }
}
