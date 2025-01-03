package com.transporthc.service;

import com.transporthc.dto.request.CargoRequest;
import com.transporthc.dto.request.WarehouseRequest;
import com.transporthc.dto.response.CargoResponse;
import com.transporthc.dto.response.WarehouseResponse;
import com.transporthc.entity.Cargo;
import com.transporthc.entity.Warehouse;
import com.transporthc.exception.AppException;
import com.transporthc.repository.CargoRepository;
import com.transporthc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CargoService {
    CargoRepository cargoRepository;
    ConcurrentHashMap<Integer, Object> cargoLock= new ConcurrentHashMap<>();

    public CargoResponse createCargo(CargoRequest cargoRequest){
        if (cargoRepository.existsByCargoName(cargoRequest.getCargoName())) {
            log.error("cargo name existed:{}",cargoRequest.getCargoName());
            throw new AppException(ErrorCode.CARGO_EXISTED);
        }
        Cargo cargo=Cargo.builder()
                .cargoName(cargoRequest.getCargoName())
                .cargoType(cargoRequest.getCargoType())
                .availableMass(cargoRequest.getAvailableMass())
                .build();
        cargoRepository.save(cargo);
        log.info("create cargo {} successful",cargoRequest.getCargoName());
        return CargoResponse.builder()
                .cargoId(cargo.getCargoId())
                .cargoName(cargo.getCargoName())
                .cargoType(cargo.getCargoType())
                .availableMass(cargo.getAvailableMass())
                .build();
    }

    public List<CargoResponse> getAllCargo(){
        List<Cargo> listCargo=cargoRepository.findAll();
        log.info("get all cargo");
        return listCargo.stream().map(cargo->CargoResponse.builder()
                .cargoId(cargo.getCargoId())
                .cargoName(cargo.getCargoName())
                .cargoType(cargo.getCargoType())
                .availableMass(cargo.getAvailableMass())
                .build()
        ).toList();
    }

    public CargoResponse updateCargo(Integer cargoId, CargoRequest cargoRequest){
        Cargo cargo= cargoRepository.findByCargoId(cargoId).orElseThrow(
                ()-> new AppException(ErrorCode.CARGO_NOT_EXISTED)
        );
        var lock= cargoLock.computeIfAbsent(cargo.getCargoId(),key-> new Object());
        synchronized (lock) {
            cargo.setCargoName(cargoRequest.getCargoName());
            cargo.setCargoType(cargoRequest.getCargoType());
            cargo.setAvailableMass(cargoRequest.getAvailableMass());
            cargoRepository.save(cargo);
            log.info("update cargo:{} successful", cargoRequest.getCargoName());
            return CargoResponse.builder()
                    .cargoId(cargo.getCargoId())
                    .cargoName(cargo.getCargoName())
                    .cargoType(cargo.getCargoType())
                    .availableMass(cargo.getAvailableMass())
                    .build();
        }
    }

    @Transactional
    public void deleteCargo(Integer cargoId){
        cargoRepository.deleteByCargoId(cargoId);
        log.info("delete cargo id:{}",cargoId);
    }

    public String inventory(){
        List<Cargo> list=cargoRepository.findAll();
        StringBuilder kq=new StringBuilder();
        for(Cargo cargo: list){
        kq.append(cargo.getAvailableMass())
                .append(" kg ")
                .append(cargo.getCargoName())
                .append(", ");
        }
        if(kq.length()>0){
            kq.setLength(kq.length()-2);
        }
        return kq.toString();
    }
}
