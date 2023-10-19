package com.example.nbd.managers;

import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;
import com.example.nbd.model.virtualdevices.VirtualDatabaseServer;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.model.virtualdevices.VirtualMachine;
import com.example.nbd.model.virtualdevices.VirtualPhone;
import com.example.nbd.repositories.VirtualDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class VirtualDeviceManager {
    @Autowired
    VirtualDeviceRepository virtualDeviceRepository;

    public void addVirtualPhone(int cpuCores,int ram, int storageSize, int number) {
        VirtualPhone virtualPhone = new VirtualPhone();
        virtualPhone.setPhoneNumber(number);
        addVirtualDevice(virtualPhone,cpuCores,ram,storageSize);
    }
    public void addVirtualDatabaseServer(int cpuCores,int ram, int storageSize,DatabaseType databaseType) {
        VirtualDatabaseServer virtualDatabaseServer = new VirtualDatabaseServer();
        virtualDatabaseServer.setDatabase(databaseType);
        addVirtualDevice(virtualDatabaseServer,cpuCores,ram,storageSize);
    }
    public void addVirtualMachine(int cpuCores,int ram, int storageSize, OperatingSystemType operatingSystemType) {
        VirtualMachine virtualMachine = new VirtualMachine();
        virtualMachine.setOperatingSystemType(operatingSystemType);
        addVirtualDevice(virtualMachine,cpuCores, ram, storageSize);
    }
    private void addVirtualDevice(VirtualDevice virtualDevice,int cpuCores,int ram, int storageSize){
        virtualDevice.setCpuCores(cpuCores);
        virtualDevice.setRam(ram);
        virtualDevice.setRented(false);
        virtualDevice.setStorageSize(storageSize);
        virtualDeviceRepository.save(virtualDevice);
    }
    public void deleteVirtualDevice(Long id) {
        virtualDeviceRepository.deleteById(id);
    }

    public void deleteVirtualDevice(VirtualDevice virtualDevice) {
        virtualDeviceRepository.delete(virtualDevice);
    }
    public List<VirtualDevice> findAllVirtualDevices() {
        return virtualDeviceRepository.findAll();
    }
    public VirtualDevice getVirtualDeviceById(Long id) {
        return virtualDeviceRepository.findById(id).orElse(null);
    }

}
