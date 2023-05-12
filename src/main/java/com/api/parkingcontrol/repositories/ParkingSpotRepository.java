package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.domain.ParkingSpotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, Long> {

    boolean existsByParkingSpotNumber(String parkingSpotNumber);

    boolean existsByLicensePlateCar(String licensePlateCar);

    boolean existsByApartmentAndBlock(String apartment, String block);

}
