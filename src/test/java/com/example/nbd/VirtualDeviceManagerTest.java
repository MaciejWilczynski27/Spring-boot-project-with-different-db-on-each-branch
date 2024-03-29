package com.example.nbd;

import com.example.nbd.exceptions.NoMatchingDeviceFoundException;
import com.example.nbd.managers.VirtualDeviceManager;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class VirtualDeviceManagerTest {
    @Autowired
    VirtualDeviceManager virtualDeviceManager;

    private void createVirtualDevices() {
        virtualDeviceManager.addVirtualMachine(8, 8, 512, OperatingSystemType.DEBIAN);
        virtualDeviceManager.addVirtualPhone(4, 4, 128, 782921842);
        virtualDeviceManager.addVirtualDatabaseServer(32, 128, 4096, DatabaseType.POSTGRESQL);
    }
    @Test
    @Transactional
    void findAllVirtualDeviceTest() {
        int virtualDeviceBuffer = virtualDeviceManager.findAllVirtualDevices().size();
        createVirtualDevices();
        Assertions.assertThat(virtualDeviceBuffer + 3 == virtualDeviceManager.findAllVirtualDevices().size()).isTrue();
    }
    @Test
    @Transactional
    void deleteVirtualDeviceTest() {
        createVirtualDevices();
        int virtualDeviceBuffer = virtualDeviceManager.findAllVirtualDevices().size();
        Long bufferedId= virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.deleteVirtualDevice(bufferedId);
        Assertions.assertThat(virtualDeviceManager.findAllVirtualDevices().size() == virtualDeviceBuffer - 1).isTrue();
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId) == null).isTrue();
    }
    @Test
    @Transactional
    void getVirtualDeviceTest() {
        createVirtualDevices();
        Assertions.assertThat(virtualDeviceManager.findAllVirtualDevices().get(0)
                .equals(virtualDeviceManager.getVirtualDeviceById(virtualDeviceManager.findAllVirtualDevices().get(0).getId()))).isTrue();
    }
    @Test
    @Transactional
    void updateVirtualDeviceRamTest() {
        createVirtualDevices();
        Long bufferedId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.updateVirtualDeviceRam(bufferedId, 16);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getRam() == 16).isTrue();
        virtualDeviceManager.updateVirtualDeviceRam(bufferedId, 32);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getRam() == 32).isTrue();
    }
    @Test
    @Transactional
    void updateVirtualDeviceStorageSizeTest() {
        createVirtualDevices();
        Long bufferedId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.updateVirtualDeviceStorageSize(bufferedId, 1024);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getStorageSize() == 1024).isTrue();
        virtualDeviceManager.updateVirtualDeviceStorageSize(bufferedId, 2048);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getStorageSize() == 2048).isTrue();
    }
    @Test
    @Transactional
    void updateVirtualDeviceCpuCoresTest() {
        createVirtualDevices();
        Long bufferedId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.updateVirtualDeviceCpuCores(bufferedId, 16);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getCpuCores() == 16).isTrue();
        virtualDeviceManager.updateVirtualDeviceCpuCores(bufferedId, 32);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getCpuCores() == 32).isTrue();
    }
    @Test
    @Transactional
    void updateVirtualMachineOperatingSystemTypeTest() throws NoMatchingDeviceFoundException {
        createVirtualDevices();
        Long bufferedId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.updateVirtualMachineOperatingSystemType(bufferedId, OperatingSystemType.WINDOWS);
        Assertions.assertThat(virtualDeviceManager.getVirtualMachineOperatingSystemType(bufferedId) == OperatingSystemType.WINDOWS).isTrue();
        virtualDeviceManager.updateVirtualMachineOperatingSystemType(bufferedId, OperatingSystemType.MACOS);
        Assertions.assertThat(virtualDeviceManager.getVirtualMachineOperatingSystemType(bufferedId) ==OperatingSystemType.MACOS).isTrue();
    }
    @Test
    @Transactional
    void updateVirtualDatabaseServerDatabaseTypeTest() throws NoMatchingDeviceFoundException {
        createVirtualDevices();
        Long bufferedId = virtualDeviceManager.findAllVirtualDevices().get(2).getId();
        virtualDeviceManager.updateVirtualDatabaseServerDatabaseType(bufferedId, DatabaseType.MYSQL);
        Assertions.assertThat(virtualDeviceManager.getVirtualDatabaseServerDatabaseType(bufferedId) == DatabaseType.MYSQL).isTrue();
        virtualDeviceManager.updateVirtualDatabaseServerDatabaseType(bufferedId, DatabaseType.ORACLE);
        Assertions.assertThat(virtualDeviceManager.getVirtualDatabaseServerDatabaseType(bufferedId) == DatabaseType.ORACLE).isTrue();
    }
    @Test
    @Transactional
    void updateVirtualPhonePhoneNumberTest() throws NoMatchingDeviceFoundException {
        createVirtualDevices();
        Long bufferedId = virtualDeviceManager.findAllVirtualDevices().get(1).getId();
        virtualDeviceManager.updateVirtualPhonePhoneNumber(bufferedId, 123456789);
        Assertions.assertThat(virtualDeviceManager.getVirtualPhonePhoneNumber(bufferedId) == 123456789).isTrue();
        virtualDeviceManager.updateVirtualPhonePhoneNumber(bufferedId, 987654321);
        Assertions.assertThat(virtualDeviceManager.getVirtualPhonePhoneNumber(bufferedId) == 987654321).isTrue();
    }
    @Test
    @Transactional
    void noMatchingDeviceFoundExceptionTest() {
        createVirtualDevices();
        try {
            virtualDeviceManager.updateVirtualMachineOperatingSystemType(virtualDeviceManager.findAllVirtualDevices().get(2).getId(), OperatingSystemType.WINDOWS);
            Assertions.fail("Exception has not been thrown");
        } catch (NoMatchingDeviceFoundException e) {
            Assertions.assertThat(e.getMessage().equals("No matching device was found!")).isTrue();
        }
        try {
            virtualDeviceManager.updateVirtualDatabaseServerDatabaseType(virtualDeviceManager.findAllVirtualDevices().get(1).getId(), DatabaseType.MYSQL);
            Assertions.fail("Exception has not been thrown");
        } catch (NoMatchingDeviceFoundException e) {
            Assertions.assertThat(e.getMessage().equals("No matching device was found!")).isTrue();
        }
        try {
            virtualDeviceManager.updateVirtualPhonePhoneNumber(virtualDeviceManager.findAllVirtualDevices().get(0).getId(), 123456789);
            Assertions.fail("Exception has not been thrown");
        } catch (NoMatchingDeviceFoundException e) {
            Assertions.assertThat(e.getMessage().equals("No matching device was found!")).isTrue();
        }
    }
}
