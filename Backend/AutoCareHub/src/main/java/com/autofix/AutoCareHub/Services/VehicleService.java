package com.autofix.AutoCareHub.Services;

import com.autofix.AutoCareHub.Controllers.Request.RegisterVehicleDTO;
import com.autofix.AutoCareHub.Entities.VehicleEntity;
import com.autofix.AutoCareHub.Repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    // get

    // Groupal
        // Find all
    public ArrayList<VehicleEntity> getAllVehicles(){
        return (ArrayList<VehicleEntity>) vehicleRepository.findAll();
    }
        // Find By Marca
    public ArrayList<VehicleEntity> getVehiclesByMarca(String marca){
        return (ArrayList<VehicleEntity>) vehicleRepository.findByMarca(marca);
    }
    // Individual
        // Find by id
    public Optional<VehicleEntity> getVehicleById(Long id){
        return vehicleRepository.findById(id);
    }
        // Find by patente
    public Optional<VehicleEntity> getVehicleByPatente(String patente){
        return vehicleRepository.findByPatente(patente);
    }

    // save

    public VehicleEntity registerVehicle(RegisterVehicleDTO vehicleDTO){
        if (vehicleRepository.existsByPatente(vehicleDTO.getPatente())){
            return null;
        }
        VehicleEntity vehicle = VehicleEntity.builder()
                .patente(vehicleDTO.getPatente())
                .marca(vehicleDTO.getMarca())
                .modelo(vehicleDTO.getModelo())
                .carType(vehicleDTO.getCarType())
                .motorType(vehicleDTO.getMotorType())
                .nAsientos(vehicleDTO.getNAsientos())
                .kmRecorridos(vehicleDTO.getKmRecorridos())
                .fabricationYear(Year.of(vehicleDTO.getFabricationYear()))
                .build();
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    // Update


    // delete ?
    public boolean deleteVehicle(Long id_vehicle){
        try{
            vehicleRepository.deleteById(id_vehicle);
            return true;
        }catch (Exception e) {
            return false;
        }

    }

}
