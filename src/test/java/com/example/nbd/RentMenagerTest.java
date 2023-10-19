package com.example.nbd;

import com.example.nbd.exceptions.ClientCantRentException;
import com.example.nbd.exceptions.DeviceAlreadyRentedException;
import com.example.nbd.exceptions.DuplicateRecordException;
import com.example.nbd.managers.ClientManager;
import com.example.nbd.managers.RentManager;
import com.example.nbd.managers.VirtualDeviceManager;
import com.example.nbd.model.enums.ClientType;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class RentMenagerTest {
    @Autowired
    ClientManager clientManager;
    @Autowired
    VirtualDeviceManager virtualDeviceManager;
    @Autowired
    RentManager rentManager;


    @Test
    void contextLoads() {
    }
    @Test
    @Transactional
    void rentTest() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientCantRentException {
        int bufferedRents = rentManager.findAllRents().size();
        clientManager.addClient("Mariusz","Pudzianowski", ClientType.BRONZE,"Lodz","Aleje Politechniki","4/7");
        clientManager.addClient("Adam","Małysz",ClientType.BRONZE,"Warszawa","Nowy Swiat","7");
        virtualDeviceManager.addVirtualMachine(8,8,512, OperatingSystemType.DEBIAN);
        virtualDeviceManager.addVirtualPhone(4,4,128,782921842);
        virtualDeviceManager.addVirtualDatabaseServer(32,128,4096, DatabaseType.POSTGRESQL);
        Long bufferedDeviceId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        rentManager.startRent(clientManager.findAllClients().get(0),virtualDeviceManager.getVirtualDeviceById(bufferedDeviceId));
        Assertions.assertThat(rentManager.findAllRents().get(0).equals(rentManager.findRentById(rentManager.findAllRents().get(0).getRentId()))).isTrue();
        Assertions.assertThat(clientManager.findAllClients().get(0).getActiveRents().size() == 1).isTrue();
        try {
            rentManager.startRent(clientManager.findAllClients().get(1), virtualDeviceManager.getVirtualDeviceById(bufferedDeviceId));
            Assertions.fail("Exception has not been thrown");
        } catch (DeviceAlreadyRentedException e) {
            Assertions.assertThat(e.getMessage().equals("Device is already rented")).isTrue();
        }
        Assertions.assertThat(rentManager.findAllRents().size() == bufferedRents + 1).isTrue();
        int clientBuffer = clientManager.findAllClients().get(0).getActiveRents().size();
        rentManager.endRent(clientManager.findAllClients().get(0).getActiveRents().get(0));
        Assertions.assertThat(clientBuffer + 1 == clientManager.findAllClients().get(0).getActiveRents().size());
        Long tempId = clientManager.findAllClients().get(0).getId();
        clientManager.findClientById(tempId).setActive(false);
        try {
            rentManager.startRent(clientManager.findClientById(tempId), virtualDeviceManager.getVirtualDeviceById(bufferedDeviceId));
            Assertions.fail("Exception has not been thrown");
        } catch (ClientCantRentException e) {
            Assertions.assertThat(e.getMessage().equals("Client is inactive or has too many rents")).isTrue();
        }
        clientManager.findClientById(tempId).setActive(true);
        rentManager.startRent(clientManager.findClientById(tempId),virtualDeviceManager.findAllVirtualDevices().get(0));
        try {
            rentManager.startRent(clientManager.findClientById(tempId), virtualDeviceManager.findAllVirtualDevices().get(1));
            Assertions.fail("Exception has not been thrown");
        } catch (ClientCantRentException e) {
            Assertions.assertThat(e.getMessage().equals("Client is inactive or has too many rents")).isTrue();
        }
    }
}
