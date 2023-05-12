package com.api.parkingcontrol.services;

import com.api.parkingcontrol.domain.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import com.api.parkingcontrol.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ParkingSpotService {

    final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        if (parkingSpotModel == null) {
            throw new ObjectNotFoundException("Vaga não encontrada! Id: " + parkingSpotModel.getId() + ", Tipo: " + ParkingSpotModel.class.getName());
        }
        return parkingSpotRepository.save(parkingSpotModel);
    }

    public boolean existsByPlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartment(String apartment, String block) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    public Page<ParkingSpotModel> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    public Optional<ParkingSpotModel> findById(Long id) {
        Optional<ParkingSpotModel> parkingSpotModelId = parkingSpotRepository.findById(id);
        if (parkingSpotModelId.isEmpty()) {
            throw new ObjectNotFoundException("Parking spot not found! Id: " + id + " does not exist. ");
        }
        return parkingSpotRepository.findById(id);
    }

    @Transactional
    public void delete(ParkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);
    }
}
