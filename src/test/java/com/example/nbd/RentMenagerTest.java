package com.example.nbd;

import com.example.nbd.exceptions.*;
import com.example.nbd.managers.ClientManager;
import com.example.nbd.managers.RentManager;
import com.example.nbd.managers.virtualdevicemanagers.VirtualDeviceManager;
import com.example.nbd.model.enums.ClientType;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    private void createTestingData() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, ClientIsNotActiveException {
        clientManager.addClient("Mariusz","Pudzianowski", ClientType.BRONZE,"Lodz","Aleje Politechniki","4/7");
        clientManager.addClient("Adam","Małysz",ClientType.BRONZE,"Warszawa","Nowy Swiat","7");
        clientManager.addClient("Kamil","Stoch", ClientType.GOLD,"Pabianice","Jana Pawla 2","21");
        virtualDeviceManager.addVirtualMachine(8,8,512, OperatingSystemType.DEBIAN);
        virtualDeviceManager.addVirtualPhone(4,4,128,782921842);
        virtualDeviceManager.addVirtualDatabaseServer(32,128,4096, DatabaseType.POSTGRESQL);
        String bufferedDeviceId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        rentManager.startRent(clientManager.findAllClients().get(0),virtualDeviceManager.getVirtualDeviceById(bufferedDeviceId), LocalDateTime.now(),LocalDateTime.now().plusWeeks(2L));
        rentManager.startRent(clientManager.findAllClients().get(1),virtualDeviceManager.getVirtualDeviceById(bufferedDeviceId), LocalDateTime.now().minusDays(5),LocalDateTime.now().minusDays(1));
    }

    @Test
    @Transactional
    void findAllRentsTest() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, ClientIsNotActiveException {
        Integer bufferedRents = rentManager.findAllRents().size();
        createTestingData();
        Assertions.assertThat(rentManager.findAllRents().get(0).equals(rentManager.findRentById(rentManager.findAllRents().get(0).getRentId()))).isTrue();
        Assertions.assertThat(rentManager.findAllRents().size() == bufferedRents + 2).isTrue();
    }

    @Test
    @Transactional
    void clientHasTooManyRentsExceptionTest() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, ClientIsNotActiveException {
        createTestingData();
        try {
            rentManager.startRent(clientManager.findAllClients().get(0),virtualDeviceManager.findAllVirtualDevices().get(0),LocalDateTime.now().plusWeeks(3L),LocalDateTime.now().plusWeeks(4L));
            Assertions.fail("Exception has not been thrown");
        } catch (ClientHasTooManyRentsException e) {
            Assertions.assertThat(e.getMessage().equals("Client has too many rents")).isTrue();
        } catch (DeviceAlreadyRentedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Transactional
    void endRentTest() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, ClientIsNotActiveException {
        createTestingData();
        String bufferedRentId = rentManager.findAllRents().get(0).getRentId();
        rentManager.endRent(rentManager.findAllRents().get(0));
        Assertions.assertThat(rentManager.findRentById(bufferedRentId).getEndLocalDateTime() != null).isTrue();
    }
    @Test
    @Transactional
    void clientIsNotActiveExceptionTest() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, ClientIsNotActiveException {
        createTestingData();
        String bufferedDeviceId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        String tempId = clientManager.findAllClients().get(0).getId();
        clientManager.setActive(tempId,false);
        try {
            rentManager.startRent(clientManager.findClientById(tempId), virtualDeviceManager.getVirtualDeviceById(bufferedDeviceId),LocalDateTime.now(),LocalDateTime.now().plusWeeks(2L));
            Assertions.fail("Exception has not been thrown");
        } catch (ClientIsNotActiveException e) {
            Assertions.assertThat(e.getMessage().equals("Client is not active")).isTrue();
        }
    }
    @Test
    @Transactional
    public void deviceAlreadyRentedExceptionTest() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, ClientIsNotActiveException {
        createTestingData();
        String bufferedDeviceId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        try {
            rentManager.startRent(clientManager.findAllClients().get(2),virtualDeviceManager.getVirtualDeviceById(bufferedDeviceId),LocalDateTime.now().minusDays(1L),LocalDateTime.now().plusWeeks(1L));
            Assertions.fail("Exception has not been thrown");
        } catch (DeviceAlreadyRentedException e) {
            Assertions.assertThat(e.getMessage().equals("Device is already rented")).isTrue();
        }
    }

    @Test
    @Transactional
    public void deleteRentTest() throws DuplicateRecordException, ClientIsNotActiveException, InvalidDatesException, DeviceAlreadyRentedException, ClientHasTooManyRentsException {
        createTestingData();
        String bufferedRentId = rentManager.findAllRents().get(0).getRentId();
        rentManager.deleteRent(bufferedRentId);
        Assertions.assertThat(rentManager.findRentById(bufferedRentId) == null).isTrue();
    }
    @Test
    @Transactional
    public void updateEndLocalDateTimeTest() throws DuplicateRecordException, DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, ClientIsNotActiveException {
        createTestingData();
        String bufferedRentId = rentManager.findAllRents().get(0).getRentId();
        String bufferedRentId2 = rentManager.findAllRents().get(1).getRentId();
        var bufferedNewLocalDateTime = LocalDateTime.now().plusWeeks(3L);
        rentManager.updateEndLocalDateTime(bufferedRentId,bufferedNewLocalDateTime);
        Assertions.assertThat(rentManager.findRentById(bufferedRentId).getEndLocalDateTime().truncatedTo(ChronoUnit.MILLIS)
                .isEqual(bufferedNewLocalDateTime.truncatedTo(ChronoUnit.MILLIS))).isTrue();
        try{
            rentManager.updateEndLocalDateTime(bufferedRentId2,LocalDateTime.now().plusWeeks(1L));
            Assertions.fail("Exception has not been thrown");
        } catch (DeviceAlreadyRentedException e) {
            Assertions.assertThat(e.getMessage().equals("Device is already rented")).isTrue();
        }
    }
    @Test
    @Transactional
    void invalidDatesExceptionTest() throws DeviceAlreadyRentedException, ClientHasTooManyRentsException, DuplicateRecordException, InvalidDatesException, ClientIsNotActiveException {
        createTestingData();
        try {
            rentManager.startRent(clientManager.findAllClients().get(0),virtualDeviceManager.findAllVirtualDevices().get(0),LocalDateTime.now(),LocalDateTime.now().minusWeeks(1L));
            Assertions.fail("Exception has not been thrown");
        } catch (InvalidDatesException e) {
            Assertions.assertThat(e.getMessage().equals("The dates are invalid")).isTrue();
        }
    }
}
