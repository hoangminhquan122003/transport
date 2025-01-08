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

public interface CargoService {

    CargoResponse createCargo(CargoRequest cargoRequest);

    List<CargoResponse> getAllCargo();

    CargoResponse updateCargo(Integer cargoId, CargoRequest cargoRequest);

    void deleteCargo(Integer cargoId);

    String inventory();
}
