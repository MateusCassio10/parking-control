package com.api.parkingcontrol.services;

import com.api.parkingcontrol.domain.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import com.api.parkingcontrol.services.exceptions.DataIntegrityException;
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

        if (parkingSpotRepository.existsByLicensePlateCar(parkingSpotModel.getLicensePlateCar())) {
            throw new DataIntegrityException("Conflict: License Plate Car is already in use!");
        }
        if (parkingSpotRepository.existsByParkingSpotNumber(parkingSpotModel.getParkingSpotNumber())) {
            throw new DataIntegrityException("Conflict: Parking Spot is already in use!");
        }
        if (parkingSpotRepository.existsByApartmentAndBlock(parkingSpotModel.getApartment(), parkingSpotModel.getBlock())) {
            throw new DataIntegrityException("Conflict: Parking Spot already registered for this apartment/block!");
        }
        return parkingSpotRepository.save(parkingSpotModel);
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
