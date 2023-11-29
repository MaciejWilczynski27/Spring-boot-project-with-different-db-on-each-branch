package com.example.nbd.managers;

import com.example.nbd.exceptions.ClientHasTooManyRentsException;
import com.example.nbd.exceptions.ClientIsNotActiveException;
import com.example.nbd.exceptions.DeviceAlreadyRentedException;
import com.example.nbd.exceptions.InvalidDatesException;
import com.example.nbd.model.Client;
import com.example.nbd.model.Rent;
import com.example.nbd.model.virtualdevices.VirtualDevice;
import com.example.nbd.repositories.ClientRepository;
import com.example.nbd.repositories.RentRepository;
import com.example.nbd.repositories.VirtualDeviceRepository;
import com.mongodb.MongoTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional(isolation = Isolation.REPEATABLE_READ,rollbackFor = MongoTimeoutException.class)
@RequiredArgsConstructor
public class RentManager {

    private final RentRepository rentRepository;

    private final VirtualDeviceRepository virtualDeviceRepository;

    private final ClientManager clientManager;
    private final ClientRepository clientRepository;


    public Rent startRent(Client client, VirtualDevice virtualDevice,LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) throws DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, ClientIsNotActiveException {
            return startRentBody(client,virtualDevice,startLocalDateTime,endLocalDateTime);
    }

    private Rent startRentBody(Client client, VirtualDevice virtualDevice,LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) throws DeviceAlreadyRentedException, ClientHasTooManyRentsException, InvalidDatesException, ClientIsNotActiveException {
        if(startLocalDateTime.isAfter(endLocalDateTime)) {
            throw new InvalidDatesException();
        }
        if(!client.isActive()) {
            throw new ClientIsNotActiveException();
        }
        if(client.getActiveRents().size() >= client.getClientType().getValue()){
            throw new ClientHasTooManyRentsException();
        }
        if(!willVirtualDeviceBeRented(virtualDevice,startLocalDateTime,endLocalDateTime)) {
            Rent rent = new Rent();
            rent.setStartLocalDateTime(startLocalDateTime);
            rent.setEndLocalDateTime(endLocalDateTime);
            rent.setClientId(client.getId());
            rent.setVirtualDeviceId(virtualDevice.getId());
            rentRepository.save(rent);
            clientManager.addRent(client,rent);
            return rent;
        } else {
            throw new DeviceAlreadyRentedException();
        }
    }
    public void endRent(Rent rent) {
        clientManager.findClientById(rent.getClientId()).getActiveRents().remove(rent);
        rent.setEndLocalDateTime(LocalDateTime.now());
        clientRepository.findById(rent.getClientId()).ifPresent(client -> {
            client.getActiveRents().remove(rent.getRentId());
            clientRepository.save(client);
        });
    }
    public void deleteRent(String id) {
            rentRepository.deleteById(id);
    }



    public List<Rent> findAllRents() {
            return rentRepository.findAll();
    }

    public List<Rent> findAllRentsWithRedis() {
        return rentRepository.findAll();
    }

    public Rent findRentById(String id) {
            return rentRepository.findById(id).orElse(null);
    }


    public void updateEndLocalDateTime(String id, LocalDateTime endLocalDateTime) throws DeviceAlreadyRentedException {
            updateEndLocalDateTimeBody(id,endLocalDateTime);
    }

    private void updateEndLocalDateTimeBody(String id, LocalDateTime endLocalDateTime) throws DeviceAlreadyRentedException {
        Rent rent = rentRepository.findById(id).orElse(null);
        if(rent != null
                && !willVirtualDeviceBeRented(virtualDeviceRepository.findById(rentRepository.findById(id).get().getVirtualDeviceId()).get()
                ,rent.getStartLocalDateTime(),endLocalDateTime)){
            rent.setEndLocalDateTime(endLocalDateTime);
            rentRepository.save(rent);
        } else {
            throw new DeviceAlreadyRentedException();
        }
    }
    private boolean willVirtualDeviceBeRented(VirtualDevice virtualDevice, LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
        return rentRepository.findAllByVirtualDeviceId(virtualDevice.getId()) .stream().anyMatch(rent ->
                !rent.getStartLocalDateTime().equals(startLocalDateTime)
                        &&(rent.getStartLocalDateTime().isBefore(startLocalDateTime) && rent.getEndLocalDateTime().isAfter(startLocalDateTime)
                        || rent.getStartLocalDateTime().isBefore(endLocalDateTime) && rent.getEndLocalDateTime().isAfter(endLocalDateTime)
                        || rent.getStartLocalDateTime().isAfter(startLocalDateTime) && rent.getEndLocalDateTime().isBefore(endLocalDateTime)));
    }
}
