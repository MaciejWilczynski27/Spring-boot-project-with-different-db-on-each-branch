package com.example.nbd;

import com.example.nbd.exceptions.NoMatchingDeviceFoundException;
import com.example.nbd.managers.virtualdevicemanagers.RedisAndMongoVirtualDeviceManager;
import com.example.nbd.managers.virtualdevicemanagers.RedisVirtualDeviceManager;
import com.example.nbd.managers.virtualdevicemanagers.VirtualDeviceManager;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;

import com.example.nbd.model.virtualdevices.VirtualDatabaseServer;
import com.example.nbd.model.virtualdevices.VirtualMachine;
import com.example.nbd.model.virtualdevices.VirtualPhone;
import com.example.nbd.repositories.VirtualDeviceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class VirtualDeviceManagerTest {
    @Autowired
    RedisAndMongoVirtualDeviceManager virtualDeviceManager;
    @Autowired
    CacheManager cacheManager;

    @SpyBean
    VirtualDeviceManager virtualDeviceRepository;


    private void createVirtualDevices() {
        virtualDeviceManager.addVirtualMachine(8, 8, 512, OperatingSystemType.DEBIAN);
        virtualDeviceManager.addVirtualPhone(4, 4, 128, 782921842);
        virtualDeviceManager.addVirtualDatabaseServer(32, 128, 4096, DatabaseType.POSTGRESQL);
    }
    @BeforeEach
    public void clearCache() {
        cacheManager.getCache("virtualDevices").clear();
        cacheManager.getCache("virtualDevice").clear();
    }
    @Test
    @Transactional
    void findAllVirtualDeviceTest() {
        int virtualDeviceBuffer = virtualDeviceManager.findAllVirtualDevices().size();
        createVirtualDevices();
        virtualDeviceManager.findAllVirtualDevices();
        virtualDeviceManager.findAllVirtualDevices();
        virtualDeviceManager.findAllVirtualDevices();
        virtualDeviceManager.findAllVirtualDevices();
        Mockito.verify(virtualDeviceRepository,Mockito.times(2)).findAllVirtualDevices();
        Assertions.assertThat( virtualDeviceManager.findAllVirtualDevices()).hasSize(virtualDeviceBuffer + 3);
    }
    @Test
    @Transactional
    void deleteVirtualDeviceTest() {
        createVirtualDevices();
        int virtualDeviceBuffer = virtualDeviceManager.findAllVirtualDevices().size();
        Mockito.verify(virtualDeviceRepository,Mockito.times(1)).findAllVirtualDevices();
        String bufferedId= virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        Mockito.verify(virtualDeviceRepository,Mockito.times(1)).findAllVirtualDevices();
        virtualDeviceManager.deleteVirtualDevice(bufferedId);
        Assertions.assertThat(virtualDeviceManager.findAllVirtualDevices().size() == virtualDeviceBuffer - 1).isTrue();
        Mockito.verify(virtualDeviceRepository,Mockito.times(2)).findAllVirtualDevices();
        Assertions.assertThatThrownBy(() ->virtualDeviceManager.getVirtualDeviceById(bufferedId)).isInstanceOf(IllegalArgumentException.class);
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
        String bufferedId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.updateVirtualDeviceRam(bufferedId, 16);
        Mockito.verify(virtualDeviceRepository,Mockito.times(0)).getVirtualDeviceById(bufferedId);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getRam() == 16).isTrue();
        Mockito.verify(virtualDeviceRepository,Mockito.times(1)).getVirtualDeviceById(bufferedId);
        virtualDeviceManager.updateVirtualDeviceRam(bufferedId, 32);
        Mockito.verify(virtualDeviceRepository,Mockito.times(1)).getVirtualDeviceById(bufferedId);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getRam() == 32).isTrue();
    }
    @Test
    @Transactional
    void updateVirtualDeviceStorageSizeTest() {

        createVirtualDevices();
        String bufferedId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.updateVirtualDeviceStorageSize(bufferedId, 1024);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getStorageSize() == 1024).isTrue();
        virtualDeviceManager.updateVirtualDeviceStorageSize(bufferedId, 2048);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getStorageSize() == 2048).isTrue();
    }
    @Test
    @Transactional
    void updateVirtualDeviceCpuCoresTest() {
        createVirtualDevices();
        String bufferedId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.updateVirtualDeviceCpuCores(bufferedId, 16);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getCpuCores() == 16).isTrue();
        virtualDeviceManager.updateVirtualDeviceCpuCores(bufferedId, 32);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId).getCpuCores() == 32).isTrue();
    }
    @Test
    @Transactional
    void updateVirtualMachineOperatingSystemTypeTest() throws NoMatchingDeviceFoundException {
        createVirtualDevices();
        String bufferedId = virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.updateVirtualMachineOperatingSystemType(bufferedId, OperatingSystemType.WINDOWS);
        Assertions.assertThat(((VirtualMachine) virtualDeviceManager.getVirtualDeviceById(bufferedId)).getOperatingSystemType().equals(OperatingSystemType.WINDOWS)).isTrue();
        virtualDeviceManager.updateVirtualMachineOperatingSystemType(bufferedId, OperatingSystemType.MACOS);
        Assertions.assertThat(((VirtualMachine) virtualDeviceManager.getVirtualDeviceById(bufferedId)).getOperatingSystemType().equals(OperatingSystemType.MACOS)).isTrue();
    }
    @Test
    @Transactional
    void updateVirtualDatabaseServerDatabaseTypeTest() throws NoMatchingDeviceFoundException {
        createVirtualDevices();
        String bufferedId = virtualDeviceManager.findAllVirtualDevices().get(2).getId();
        virtualDeviceManager.updateVirtualDatabaseServerDatabaseType(bufferedId, DatabaseType.MYSQL);
        Assertions.assertThat(((VirtualDatabaseServer) virtualDeviceManager.getVirtualDeviceById(bufferedId)).getDatabase().equals(DatabaseType.MYSQL)).isTrue();
        virtualDeviceManager.updateVirtualDatabaseServerDatabaseType(bufferedId, DatabaseType.ORACLE);
        Assertions.assertThat(((VirtualDatabaseServer) virtualDeviceManager.getVirtualDeviceById(bufferedId)).getDatabase().equals(DatabaseType.ORACLE)).isTrue();
    }
    @Test
    @Transactional
    @Profile(value = "Test")
    void updateVirtualPhonePhoneNumberTest() throws NoMatchingDeviceFoundException {
        createVirtualDevices();
        String bufferedId = virtualDeviceManager.findAllVirtualDevices().get(1).getId();
        virtualDeviceManager.updateVirtualPhonePhoneNumber(bufferedId, 123456789);
        Assertions.assertThat(((VirtualPhone)virtualDeviceManager.getVirtualDeviceById(bufferedId)).getPhoneNumber() == 123456789).isTrue();
        virtualDeviceManager.updateVirtualPhonePhoneNumber(bufferedId, 987654321);
        Assertions.assertThat(((VirtualPhone)virtualDeviceManager.getVirtualDeviceById(bufferedId)).getPhoneNumber() == 987654321).isTrue();
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
