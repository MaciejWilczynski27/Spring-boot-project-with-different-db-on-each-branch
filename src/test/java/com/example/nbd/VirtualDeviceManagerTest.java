package com.example.nbd;

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
    @Test
    @Transactional
    void basicTest() {
        int virtualDeviceBuffer = virtualDeviceManager.findAllVirtualDevices().size();
        virtualDeviceManager.addVirtualMachine(8, 8, 512, OperatingSystemType.DEBIAN);
        virtualDeviceManager.addVirtualPhone(4, 4, 128, 782921842);
        virtualDeviceManager.addVirtualDatabaseServer(32, 128, 4096, DatabaseType.POSTGRESQL);
        Assertions.assertThat(virtualDeviceBuffer + 3 == virtualDeviceManager.findAllVirtualDevices().size()).isTrue();
        virtualDeviceManager.deleteVirtualDevice(virtualDeviceManager.findAllVirtualDevices().get(0));
        Assertions.assertThat(virtualDeviceBuffer + 2 == virtualDeviceManager.findAllVirtualDevices().size()).isTrue();
        Assertions.assertThat(virtualDeviceManager.findAllVirtualDevices().get(0)
                .equals(virtualDeviceManager.getVirtualDeviceById(virtualDeviceManager.findAllVirtualDevices().get(0).getId()))).isTrue();
        Long bufferedId= virtualDeviceManager.findAllVirtualDevices().get(0).getId();
        virtualDeviceManager.deleteVirtualDevice(bufferedId);
        Assertions.assertThat(virtualDeviceManager.getVirtualDeviceById(bufferedId) == null).isTrue();
    }
}
