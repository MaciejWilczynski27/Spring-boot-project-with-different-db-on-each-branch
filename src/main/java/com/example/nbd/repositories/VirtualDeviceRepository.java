package com.example.nbd.repositories;



import com.example.nbd.model.virtualdevices.VirtualDatabaseServer;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.model.virtualdevices.VirtualMachine;
import com.example.nbd.model.virtualdevices.VirtualPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VirtualDeviceRepository {
    private final VirtualMachineRepository virtualMachineRepository;
    private final VirtualDatabaseServerRepository virtualDatabaseServerRepository;
    private final VirtualPhoneRepository virtualPhoneRepository;

    public VirtualDevice save(VirtualDevice virtualDevice){
        if(virtualDevice instanceof VirtualMachine){
            return virtualMachineRepository.save((VirtualMachine) virtualDevice);
        }
        else if(virtualDevice instanceof VirtualDatabaseServer){
            return virtualDatabaseServerRepository.save((VirtualDatabaseServer) virtualDevice);
        }
        else if(virtualDevice instanceof VirtualPhone){
            return virtualPhoneRepository.save((VirtualPhone) virtualDevice);
        }
        else{
            throw new IllegalArgumentException("Unknown virtual device type");
        }
    }
    public void deleteById(String id){
        if(virtualMachineRepository.existsById(id)){
            virtualMachineRepository.deleteById(id);
        }
        else if(virtualDatabaseServerRepository.existsById(id)){
            virtualDatabaseServerRepository.deleteById(id);
        }
        else if(virtualPhoneRepository.existsById(id)){
            virtualPhoneRepository.deleteById(id);
        }
    }
    public void deleteAll(){
        virtualMachineRepository.deleteAll();
        virtualDatabaseServerRepository.deleteAll();
        virtualPhoneRepository.deleteAll();
    }
    public List<VirtualDevice> findAll(){
        List<VirtualDevice> virtualDevices = virtualMachineRepository.findAll()
                .stream()
                .map(virtualDevice -> (VirtualDevice) virtualDevice)
                .collect(Collectors.toList());
        virtualDevices.addAll(virtualDatabaseServerRepository.findAll()
                .stream()
                .map(virtualDevice -> (VirtualDevice) virtualDevice)
                .collect(Collectors.toList()));
        virtualDevices.addAll(virtualPhoneRepository.findAll()
                .stream()
                .map(virtualDevice -> (VirtualDevice) virtualDevice)
                .collect(Collectors.toList()));
        return virtualDevices;
    }
    public Optional<VirtualDevice> findById(String id){
        return virtualMachineRepository.findById(id)
                .map(virtualDevice -> (VirtualDevice) virtualDevice)
                .or(() -> virtualDatabaseServerRepository.findById(id)
                        .map(virtualDevice -> (VirtualDevice) virtualDevice))
                .or(() -> virtualPhoneRepository.findById(id)
                        .map(virtualDevice -> (VirtualDevice) virtualDevice));
    }

}
