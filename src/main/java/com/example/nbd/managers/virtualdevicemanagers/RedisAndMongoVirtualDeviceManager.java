package com.example.nbd.managers.virtualdevicemanagers;

import com.example.nbd.exceptions.NoMatchingDeviceFoundException;
import com.example.nbd.model.enums.DatabaseType;
import com.example.nbd.model.enums.OperatingSystemType;
import com.example.nbd.model.virtualdevices.VirtualDatabaseServer;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.model.virtualdevices.VirtualMachine;
import com.example.nbd.model.virtualdevices.VirtualPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class RedisAndMongoVirtualDeviceManager extends VMDecorator {
    private final RedisVirtualDeviceManager redisVirtualDeviceManager;
    private final VirtualDeviceManager virtualDeviceManager;
    private final CacheManager cacheManager;
    @Override
    public VirtualPhone addVirtualPhone(int cpuCores, int ram, int storageSize, int number) {
        return (VirtualPhone) redisMethodDefaultMongoIfFails(() -> redisVirtualDeviceManager.addVirtualPhone(cpuCores,ram,storageSize,number),
                                                             () -> virtualDeviceManager.addVirtualPhone(cpuCores,ram,storageSize,number));
    }

    @Override
    public VirtualDatabaseServer addVirtualDatabaseServer(int cpuCores, int ram, int storageSize, DatabaseType databaseType) {
        return (VirtualDatabaseServer) redisMethodDefaultMongoIfFails(() -> redisVirtualDeviceManager.addVirtualDatabaseServer(cpuCores,ram,storageSize,databaseType),
                                                                      () -> virtualDeviceManager.addVirtualDatabaseServer(cpuCores,ram,storageSize,databaseType));
    }

    @Override
    public VirtualMachine addVirtualMachine(int cpuCores, int ram, int storageSize, OperatingSystemType operatingSystemType) {
        return (VirtualMachine) redisMethodDefaultMongoIfFails(() -> redisVirtualDeviceManager.addVirtualMachine(cpuCores,ram,storageSize,operatingSystemType),
                                                               () -> virtualDeviceManager.addVirtualMachine(cpuCores,ram,storageSize,operatingSystemType));
    }

    @Override
    public void deleteVirtualDevice(String id) {
       redisMethodDefaultMongoIfFails(redisVirtualDeviceManager::deleteVirtualDevice,
                                      virtualDeviceManager::deleteVirtualDevice,
                                      id);
    }

    @Override
    public VirtualDevice updateVirtualDeviceRam(String id, int ram) {
        return (VirtualDevice) redisMethodDefaultMongoIfFails(() -> redisVirtualDeviceManager.updateVirtualDeviceRam(id,ram),
                                                              () -> virtualDeviceManager.updateVirtualDeviceRam(id,ram));
    }

    @Override
    public VirtualDevice updateVirtualDeviceStorageSize(String id, int storageSize) {
        return (VirtualDevice) redisMethodDefaultMongoIfFails(() -> redisVirtualDeviceManager.updateVirtualDeviceStorageSize(id,storageSize),
                                                              () -> virtualDeviceManager.updateVirtualDeviceStorageSize(id,storageSize));
    }

    @Override
    public VirtualDevice updateVirtualDeviceCpuCores(String id, int cpuCores) {
        return (VirtualDevice) redisMethodDefaultMongoIfFails(() -> redisVirtualDeviceManager.updateVirtualDeviceCpuCores(id,cpuCores),
                                                              () -> virtualDeviceManager.updateVirtualDeviceCpuCores(id,cpuCores));
    }

    @Override
    public VirtualMachine updateVirtualMachineOperatingSystemType(String id, OperatingSystemType operatingSystemType) throws NoMatchingDeviceFoundException {
        return (VirtualMachine) redisMethodDefaultMongoIfFails(() -> redisVirtualDeviceManager.updateVirtualMachineOperatingSystemType(id,operatingSystemType),
                                                               () -> virtualDeviceManager.updateVirtualMachineOperatingSystemType(id,operatingSystemType));
    }

    @Override
    public VirtualDatabaseServer updateVirtualDatabaseServerDatabaseType(String id, DatabaseType databaseType) throws NoMatchingDeviceFoundException {
        return (VirtualDatabaseServer) redisMethodDefaultMongoIfFails(() -> redisVirtualDeviceManager.updateVirtualDatabaseServerDatabaseType(id,databaseType),
                                                                      () -> virtualDeviceManager.updateVirtualDatabaseServerDatabaseType(id,databaseType));
    }

    @Override
    public VirtualPhone updateVirtualPhonePhoneNumber(String id, int phoneNumber) throws NoMatchingDeviceFoundException {
        return (VirtualPhone) redisMethodDefaultMongoIfFails(() -> redisVirtualDeviceManager.updateVirtualPhonePhoneNumber(id,phoneNumber),
                                                             () -> virtualDeviceManager.updateVirtualPhonePhoneNumber(id,phoneNumber));
    }

    @Override
    public List<VirtualDevice> findAllVirtualDevices() {
        return (List<VirtualDevice>) redisMethodDefaultMongoIfFails(redisVirtualDeviceManager::findAllVirtualDevices,
                                                                    virtualDeviceManager::findAllVirtualDevices);
    }

    @Override
    public VirtualDevice getVirtualDeviceById(String id) {
        return redisMethodDefaultMongoIfFails(redisVirtualDeviceManager::getVirtualDeviceById,
                                              virtualDeviceManager::getVirtualDeviceById,
                                              id);
    }
    private Object redisMethodDefaultMongoIfFails(Supplier<Object> redisMethod, Supplier<Object> mongoMethod){
        try{
            testConnection();
            return redisMethod.get();
        }catch (RedisConnectionFailureException e){
            return mongoMethod.get();
        }
    }
    private VirtualDevice redisMethodDefaultMongoIfFails(Function<String,VirtualDevice> redisMethod, Function<String,VirtualDevice> mongoMethod,String id){
        try{
            testConnection();
            return redisMethod.apply(id);
        }catch (RedisConnectionFailureException e){
            return mongoMethod.apply(id);
        }
    }
    private void redisMethodDefaultMongoIfFails(Consumer<String> redisMethod, Consumer<String> mongoMethod,String id){
        try{
            testConnection();
            redisMethod.accept(id);
        }catch (RedisConnectionFailureException e){
            mongoMethod.accept(id);
        }
    }
    private void testConnection() {
        redisVirtualDeviceManager.testRedisConnection();
        redisVirtualDeviceManager.testRedisConnection();
    }
}
