package com.example.nbd;

import com.example.nbd.managers.virtualdevicemanagers.RedisAndMongoVirtualDeviceManager;
import com.example.nbd.managers.virtualdevicemanagers.VirtualDeviceManager;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class LostConnectionVirtualDeviceTest {
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

    @Test
    @Transactional
    void findAllVirtualDeviceTestLostRedis() {
        Mockito.verify(virtualDeviceRepository,Mockito.times(0)).findAllVirtualDevices();
        int virtualDeviceBuffer = virtualDeviceManager.findAllVirtualDevices().size();
        createVirtualDevices();
        virtualDeviceManager.findAllVirtualDevices();
        virtualDeviceManager.findAllVirtualDevices();
        virtualDeviceManager.findAllVirtualDevices();
        virtualDeviceManager.findAllVirtualDevices();
        Mockito.verify(virtualDeviceRepository,Mockito.times(5)).findAllVirtualDevices();
        Assertions.assertThat( virtualDeviceManager.findAllVirtualDevices()).hasSize(virtualDeviceBuffer + 3);
        Mockito.verify(virtualDeviceRepository,Mockito.times(6)).findAllVirtualDevices();
    }
}
