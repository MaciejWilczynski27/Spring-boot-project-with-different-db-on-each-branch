package com.example.nbd.managers.virtualdevicemanagers;

import com.example.nbd.exceptions.NoMatchingDeviceFoundException;
import com.example.nbd.managers.virtualdevicemanagers.VMDecorator;
import com.example.nbd.managers.virtualdevicemanagers.VirtualDeviceManager;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;
import com.example.nbd.model.virtualdevices.VirtualDatabaseServer;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.model.virtualdevices.VirtualMachine;
import com.example.nbd.model.virtualdevices.VirtualPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class RedisVirtualDeviceManager extends VMDecorator {

    private final VirtualDeviceManager virtualDeviceManager;
    @Override
    @CacheEvict(value = "virtualDevices",allEntries = true)
    @CachePut(value = "virtualDevice",key = "#result.id")
    public VirtualPhone addVirtualPhone(int cpuCores, int ram, int storageSize, int number) {
        return virtualDeviceManager.addVirtualPhone(cpuCores, ram, storageSize, number);
    }

    @Override
    @CacheEvict(value = "virtualDevices",allEntries = true)
    @CachePut(value = "virtualDevice",key = "#result.id")
    public VirtualDatabaseServer addVirtualDatabaseServer(int cpuCores, int ram, int storageSize, DatabaseType databaseType) {
        return virtualDeviceManager.addVirtualDatabaseServer(cpuCores, ram, storageSize, databaseType);
    }

    @Override
    @CacheEvict(value = "virtualDevices",allEntries = true)
    @CachePut(value = "virtualDevice",key = "#result.id")
    public VirtualMachine addVirtualMachine(int cpuCores, int ram, int storageSize, OperatingSystemType operatingSystemType) {
        return virtualDeviceManager.addVirtualMachine(cpuCores, ram, storageSize, operatingSystemType);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "virtualDevices",allEntries = true),
            @CacheEvict(value = "virtualDevice",key = "#id")
    })
    public void deleteVirtualDevice(String id) {
        virtualDeviceManager.deleteVirtualDevice(id);
    }

    @Override
    @CacheEvict(value = "virtualDevices",allEntries = true)
    @CachePut(value = "virtualDevice",key = "#result.id")
    public VirtualDevice updateVirtualDeviceRam(String id, int ram) {
        return virtualDeviceManager.updateVirtualDeviceRam(id, ram);
    }

    @Override
    @CacheEvict(value = "virtualDevices",allEntries = true)
    @CachePut(value = "virtualDevice",key = "#result.id")
    public VirtualDevice updateVirtualDeviceStorageSize(String id, int storageSize) {
        return virtualDeviceManager.updateVirtualDeviceStorageSize(id, storageSize);
    }

    @Override
    @CacheEvict(value = "virtualDevices",allEntries = true)
    @CachePut(value = "virtualDevice",key = "#result.id")
    public VirtualDevice updateVirtualDeviceCpuCores(String id, int cpuCores) {
        return virtualDeviceManager.updateVirtualDeviceCpuCores(id, cpuCores);
    }

    @Override
    @CacheEvict(value = "virtualDevices",allEntries = true)
    @CachePut(value = "virtualDevice",key = "#result.id")
    public VirtualMachine updateVirtualMachineOperatingSystemType(String id, OperatingSystemType operatingSystemType) throws NoMatchingDeviceFoundException {
        return virtualDeviceManager.updateVirtualMachineOperatingSystemType(id, operatingSystemType);
    }

    @Override
    @CacheEvict(value = "virtualDevices",allEntries = true)
    @CachePut(value = "virtualDevice",key = "#result.id")
    public VirtualDatabaseServer updateVirtualDatabaseServerDatabaseType(String id, DatabaseType databaseType) throws NoMatchingDeviceFoundException {
        return virtualDeviceManager.updateVirtualDatabaseServerDatabaseType(id, databaseType);
    }

    @Override
    @CacheEvict(value = "virtualDevices",allEntries = true)
    @CachePut(value = "virtualDevice",key = "#result.id")
    public VirtualPhone updateVirtualPhonePhoneNumber(String id, int phoneNumber) throws NoMatchingDeviceFoundException {
        return virtualDeviceManager.updateVirtualPhonePhoneNumber(id, phoneNumber);
    }



    @Override
    @Cacheable(value = "virtualDevices")
    public List<VirtualDevice> findAllVirtualDevices() {
        return virtualDeviceManager.findAllVirtualDevices();
    }

    @Override
    @Cacheable(value = "virtualDevices",key = "#id")
    public VirtualDevice getVirtualDeviceById(String id) {
        return virtualDeviceManager.getVirtualDeviceById(id);
    }
}
