package com.example.nbd.managers.virtualdevicemanagers;

import com.example.nbd.exceptions.*;
import com.example.nbd.model.Client;
import com.example.nbd.model.Rent;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;
import com.example.nbd.model.virtualdevices.VirtualDatabaseServer;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.model.virtualdevices.VirtualMachine;
import com.example.nbd.model.virtualdevices.VirtualPhone;

import java.time.LocalDateTime;
import java.util.List;

public interface IVirtualDeviceManager {
    VirtualPhone addVirtualPhone(int cpuCores,int ram, int storageSize, int number);
    VirtualDatabaseServer addVirtualDatabaseServer(int cpuCores, int ram, int storageSize, DatabaseType databaseType);
    VirtualMachine addVirtualMachine(int cpuCores,int ram, int storageSize, OperatingSystemType operatingSystemType);
    void deleteVirtualDevice(String id);
    VirtualDevice updateVirtualDeviceRam(String id,int ram);
    VirtualDevice updateVirtualDeviceStorageSize(String id,int storageSize);
    VirtualDevice updateVirtualDeviceCpuCores(String id,int cpuCores);
    VirtualMachine updateVirtualMachineOperatingSystemType(String id,OperatingSystemType operatingSystemType) throws NoMatchingDeviceFoundException;
    VirtualDatabaseServer updateVirtualDatabaseServerDatabaseType(String id,DatabaseType databaseType) throws NoMatchingDeviceFoundException;
    VirtualPhone updateVirtualPhonePhoneNumber(String id,int phoneNumber) throws NoMatchingDeviceFoundException;

    List<VirtualDevice> findAllVirtualDevices();
    VirtualDevice getVirtualDeviceById(String id);
}