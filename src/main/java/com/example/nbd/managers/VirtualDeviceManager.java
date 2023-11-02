package com.example.nbd.managers;

import com.example.nbd.exceptions.NoMatchingDeviceFoundException;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;
import com.example.nbd.model.virtualdevices.VirtualDatabaseServer;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.model.virtualdevices.VirtualMachine;
import com.example.nbd.model.virtualdevices.VirtualPhone;
import com.example.nbd.repositories.VirtualDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ)
@RequiredArgsConstructor
public class VirtualDeviceManager {

    private final VirtualDeviceRepository virtualDeviceRepository;

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
        virtualDevice.setStorageSize(storageSize);
        virtualDeviceRepository.save(virtualDevice);
    }
    public void deleteVirtualDevice(String id) {
        virtualDeviceRepository.deleteById(id);
    }


    public void updateVirtualDeviceRam(String id,int ram) {
        virtualDeviceRepository.findById(id).ifPresent(virtualDevice -> virtualDevice.setRam(ram));
    }
    public void updateVirtualDeviceStorageSize(String id,int storageSize) {
        virtualDeviceRepository.findById(id).ifPresent(virtualDevice -> virtualDevice.setStorageSize(storageSize));
    }
    public void updateVirtualDeviceCpuCores(String id,int cpuCores) {
        virtualDeviceRepository.findById(id).ifPresent(virtualDevice -> virtualDevice.setCpuCores(cpuCores));
    }
    public void updateVirtualMachineOperatingSystemType(String id,OperatingSystemType operatingSystemType) throws NoMatchingDeviceFoundException {
        VirtualDevice virtualDevice = virtualDeviceRepository.findById(id).orElse(null);
        if(virtualDevice != null) {
            if(virtualDevice instanceof VirtualMachine) {
                ((VirtualMachine) virtualDevice).setOperatingSystemType(operatingSystemType);
                return;
            }
        }
        throw new NoMatchingDeviceFoundException();

    }
    public void updateVirtualDatabaseServerDatabaseType(String id,DatabaseType databaseType) throws NoMatchingDeviceFoundException {
        VirtualDevice virtualDevice = virtualDeviceRepository.findById(id).orElse(null);
        if(virtualDevice != null) {
            if(virtualDevice instanceof VirtualDatabaseServer) {
                ((VirtualDatabaseServer) virtualDevice).setDatabase(databaseType);
                return;
            }
        }
        throw new NoMatchingDeviceFoundException();
    }
    public void updateVirtualPhonePhoneNumber(String id,int phoneNumber) throws NoMatchingDeviceFoundException {
        VirtualDevice virtualDevice = virtualDeviceRepository.findById(id).orElse(null);
        if(virtualDevice != null) {
            if(virtualDevice instanceof VirtualPhone) {
                ((VirtualPhone) virtualDevice).setPhoneNumber(phoneNumber);
                return;
            }
        }
        throw new NoMatchingDeviceFoundException();
    }
    public OperatingSystemType getVirtualMachineOperatingSystemType(String id) throws NoMatchingDeviceFoundException {
        VirtualDevice virtualDevice = virtualDeviceRepository.findById(id).orElse(null);
        if (virtualDevice != null) {
            if (virtualDevice instanceof VirtualMachine) {
                return ((VirtualMachine) virtualDevice).getOperatingSystemType();
            }
        }
        throw new NoMatchingDeviceFoundException();
    }
    public DatabaseType getVirtualDatabaseServerDatabaseType(String id) throws NoMatchingDeviceFoundException {
        VirtualDevice virtualDevice = virtualDeviceRepository.findById(id).orElse(null);
        if(virtualDevice != null) {
            if(virtualDevice instanceof VirtualDatabaseServer) {
                return ((VirtualDatabaseServer) virtualDevice).getDatabase();
            }
        }
        throw new NoMatchingDeviceFoundException();
    }
    public int getVirtualPhonePhoneNumber(String id) throws NoMatchingDeviceFoundException {
        VirtualDevice virtualDevice = virtualDeviceRepository.findById(id).orElse(null);
        if(virtualDevice != null) {
            if(virtualDevice instanceof VirtualPhone) {
                return ((VirtualPhone) virtualDevice).getPhoneNumber();
            }
        }
        throw new NoMatchingDeviceFoundException();
    }
    public List<VirtualDevice> findAllVirtualDevices() {
        return virtualDeviceRepository.findAll();
    }
    public VirtualDevice getVirtualDeviceById(String id) {
        return virtualDeviceRepository.findById(id).orElse(null);
    }

}
