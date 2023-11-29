package com.example.nbd.managers.virtualdevicemanagers;

import com.example.nbd.exceptions.NoMatchingDeviceFoundException;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;
import com.example.nbd.model.virtualdevices.VirtualDatabaseServer;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.model.virtualdevices.VirtualMachine;
import com.example.nbd.model.virtualdevices.VirtualPhone;
import com.example.nbd.repositories.VirtualDeviceRepository;
import com.mongodb.MongoTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ,rollbackFor = MongoTimeoutException.class)
@RequiredArgsConstructor
public class VirtualDeviceManager implements IVirtualDeviceManager {

    private final VirtualDeviceRepository virtualDeviceRepository;

    @Override
    public VirtualPhone addVirtualPhone(int cpuCores,int ram, int storageSize, int number) {
        VirtualPhone virtualPhone = new VirtualPhone();
        virtualPhone.setPhoneNumber(number);
        addVirtualDevice(virtualPhone,cpuCores,ram,storageSize);
        return virtualPhone;
    }
    @Override
    public VirtualDatabaseServer addVirtualDatabaseServer(int cpuCores,int ram, int storageSize,DatabaseType databaseType) {
        VirtualDatabaseServer virtualDatabaseServer = new VirtualDatabaseServer();
        virtualDatabaseServer.setDatabase(databaseType);
        addVirtualDevice(virtualDatabaseServer,cpuCores,ram,storageSize);
        return virtualDatabaseServer;
    }
    @Override
    public VirtualMachine addVirtualMachine(int cpuCores,int ram, int storageSize, OperatingSystemType operatingSystemType) {
        VirtualMachine virtualMachine = new VirtualMachine();
        virtualMachine.setOperatingSystemType(operatingSystemType);
        addVirtualDevice(virtualMachine,cpuCores, ram, storageSize);
        return virtualMachine;
    }

    private void addVirtualDevice(VirtualDevice virtualDevice,int cpuCores,int ram, int storageSize){
        virtualDevice.setCpuCores(cpuCores);
        virtualDevice.setRam(ram);
        virtualDevice.setStorageSize(storageSize);
        virtualDeviceRepository.save(virtualDevice);
    }
    @Override
    public void deleteVirtualDevice(String id) {
        virtualDeviceRepository.deleteById(id);
    }

    @Override
    public VirtualDevice updateVirtualDeviceRam(String id,int ram) {
        virtualDeviceRepository.findById(id).ifPresent(virtualDevice -> {
            virtualDevice.setRam(ram);
            virtualDeviceRepository.save(virtualDevice);
        });
        return virtualDeviceRepository.findById(id).orElse(null);
    }
    @Override
    public VirtualDevice updateVirtualDeviceStorageSize(String id,int storageSize) {
        virtualDeviceRepository.findById(id).ifPresent(virtualDevice -> {
            virtualDevice.setStorageSize(storageSize);
            virtualDeviceRepository.save(virtualDevice);
        });
        return virtualDeviceRepository.findById(id).orElse(null);
    }
    @Override
    public VirtualDevice updateVirtualDeviceCpuCores(String id,int cpuCores) {
        virtualDeviceRepository.findById(id).ifPresent(virtualDevice -> {
            virtualDevice.setCpuCores(cpuCores);
            virtualDeviceRepository.save(virtualDevice);
        });
        return virtualDeviceRepository.findById(id).orElse(null);
    }
    @Override
    public VirtualMachine updateVirtualMachineOperatingSystemType(String id,OperatingSystemType operatingSystemType) throws NoMatchingDeviceFoundException {
        VirtualDevice virtualDevice = virtualDeviceRepository.findById(id).orElse(null);
        if(virtualDevice != null) {
            if(virtualDevice instanceof VirtualMachine) {
                ((VirtualMachine) virtualDevice).setOperatingSystemType(operatingSystemType);
                virtualDeviceRepository.save(virtualDevice);
                return (VirtualMachine) virtualDevice;
            }
        }
        throw new NoMatchingDeviceFoundException();

    }
    @Override
    public VirtualDatabaseServer updateVirtualDatabaseServerDatabaseType(String id,DatabaseType databaseType) throws NoMatchingDeviceFoundException {
        VirtualDevice virtualDevice = virtualDeviceRepository.findById(id).orElse(null);
        if(virtualDevice != null) {
            if(virtualDevice instanceof VirtualDatabaseServer) {
                ((VirtualDatabaseServer) virtualDevice).setDatabase(databaseType);
                virtualDeviceRepository.save(virtualDevice);
                return (VirtualDatabaseServer) virtualDevice;
            }
        }
        throw new NoMatchingDeviceFoundException();
    }
    @Override
    public VirtualPhone updateVirtualPhonePhoneNumber(String id,int phoneNumber) throws NoMatchingDeviceFoundException {
        VirtualDevice virtualDevice = virtualDeviceRepository.findById(id).orElse(null);
        if(virtualDevice != null) {
            if(virtualDevice instanceof VirtualPhone) {
                ((VirtualPhone) virtualDevice).setPhoneNumber(phoneNumber);
                virtualDeviceRepository.save(virtualDevice);
                return (VirtualPhone) virtualDevice;
            }
        }
        throw new NoMatchingDeviceFoundException();
    }
    @Override
    public List<VirtualDevice> findAllVirtualDevices() {
        return virtualDeviceRepository.findAll();
    }
    @Override
    public VirtualDevice getVirtualDeviceById(String id) {
        return virtualDeviceRepository.findById(id).orElse(null);
    }

}
