package com.example.nbd.managers;

import com.example.nbd.exceptions.ClientCantRentException;
import com.example.nbd.exceptions.DeviceAlreadyRentedException;
import com.example.nbd.exceptions.InvalidDatesException;
import com.example.nbd.model.Client;
import com.example.nbd.model.Rent;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.repositories.RentRepository;
import com.example.nbd.repositories.VirtualDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class RentManager {
    @Autowired
    RentRepository rentRepository;
    @Autowired
    VirtualDeviceRepository virtualDeviceRepository;
    @Autowired
    ClientManager clientManager;

    public void startRent(Client client, VirtualDevice virtualDevice,LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) throws DeviceAlreadyRentedException, ClientCantRentException, InvalidDatesException {
        if(startLocalDateTime.isAfter(endLocalDateTime)) {
            throw new InvalidDatesException();
        }
        if(!(client.isActive()
                && client.getActiveRents().size() < client.getClientType().getValue())) {
            throw new ClientCantRentException();
        }
        if(!willVirtualDeviceBeRented(virtualDevice,startLocalDateTime,endLocalDateTime)) {
            Rent rent = new Rent();
            rent.setStartLocalDateTime(startLocalDateTime);
            rent.setEndLocalDateTime(endLocalDateTime);
            rent.setClientId(client.getId());
            rent.setVirtualDeviceId(virtualDevice.getId());
            rentRepository.save(rent);
            clientManager.addRent(client,rent);
        } else {
            throw new DeviceAlreadyRentedException();
        }
    }
    public void endRent(Rent rent) {
        clientManager.findClientById(rent.getClientId()).getActiveRents().remove(rent);
        rent.setEndLocalDateTime(LocalDateTime.now());
    }
    public List<Rent> findAllRents() {
        return rentRepository.findAll();
    }
    public Rent findRentById(String id) {
        return rentRepository.findById(id).orElse(null);
    }
    public void updateEndLocalDateTime(String id, LocalDateTime endLocalDateTime) throws DeviceAlreadyRentedException {
        Rent rent = rentRepository.findById(id).orElse(null);
        if(rent != null
                && !willVirtualDeviceBeRented(virtualDeviceRepository.findById(rentRepository.findById(id).get().getVirtualDeviceId()).get()
                ,rent.getStartLocalDateTime(),endLocalDateTime)){
            rent.setEndLocalDateTime(endLocalDateTime);
        } else {
            throw new DeviceAlreadyRentedException();
        }
    }
    private boolean willVirtualDeviceBeRented(VirtualDevice virtualDevice, LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
        return rentRepository.findAllByVirtualDeviceId(virtualDevice.getId()) .stream().anyMatch(rent -> {
                if(rent.getStartLocalDateTime().isBefore(startLocalDateTime) && rent.getEndLocalDateTime().isAfter(startLocalDateTime)) {
                    if(!rent.getStartLocalDateTime().isEqual(startLocalDateTime)) {
                        return true;
                    }
                }
                if(rent.getStartLocalDateTime().isBefore(endLocalDateTime) && rent.getEndLocalDateTime().isAfter(endLocalDateTime)) {
                    if(!rent.getStartLocalDateTime().isEqual(startLocalDateTime)) {
                        return true;
                    }
                }
                if(rent.getStartLocalDateTime().isAfter(startLocalDateTime) && rent.getEndLocalDateTime().isBefore(endLocalDateTime)) {
                    if(!rent.getStartLocalDateTime().isEqual(startLocalDateTime)) {
                        return true;
                    }
                }
                return false;
        });

    }
    
}
