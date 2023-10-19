package com.example.nbd.managers;

import com.example.nbd.exceptions.ClientCantRentException;
import com.example.nbd.exceptions.DeviceAlreadyRentedException;
import com.example.nbd.model.Client;
import com.example.nbd.model.Rent;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@Transactional
public class RentManager {
    @Autowired
    RentRepository rentRepository;
    @Autowired
    ClientManager clientManager;

    public void startRent(Client client, VirtualDevice virtualDevice) throws DeviceAlreadyRentedException, ClientCantRentException {
        if(virtualDevice.isRented()) {
            throw new DeviceAlreadyRentedException();
        }
        if(client.isActive()
                && client.getActiveRents().size() < client.getClientType().getValue()) {
            Rent rent = new Rent();
            rent.setBeginDate(LocalDate.now());
            rent.setEndDate(null);
            rent.setClient(client);
            rent.setVirtualDevice(virtualDevice);
            virtualDevice.setRented(true);
            rentRepository.save(rent);
            clientManager.addRent(client,rent);
        } else {
            throw new ClientCantRentException();
        }
    }
    public void endRent(Rent rent) {
        rent.getClient().getActiveRents().remove(rent);
        rent.getVirtualDevice().setRented(false);
        rent.setEndDate(LocalDate.now());
    }
    public List<Rent> findAllRents() {
        return rentRepository.findAll();
    }
    public Rent findRentById(Long id) {
        return rentRepository.findById(id).orElse(null);
    }
}
