package com.transporthc.service;

import com.transporthc.dto.request.WarehouseRequest;
import com.transporthc.dto.response.WarehouseResponse;
import com.transporthc.entity.Warehouse;
import com.transporthc.exception.AppException;
import com.transporthc.repository.WarehouseRepository;
import com.transporthc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class WarehouseService {
    WarehouseRepository warehouseRepository;

    public WarehouseResponse createWarehouse(WarehouseRequest warehouseRequest) {
        if (warehouseRepository.existsByWarehouseName(warehouseRequest.getWarehouseName())) {
            log.error("warehouse name existed:{}", warehouseRequest.getWarehouseName());
            throw new AppException(ErrorCode.WAREHOUSE_EXISTED);
        }
        Warehouse warehouse = Warehouse.builder()
                .warehouseAddress(warehouseRequest.getWarehouseAddress())
                .warehouseName(warehouseRequest.getWarehouseName())
                .build();
        warehouseRepository.save(warehouse);
        log.info("create warehouse {} successful", warehouseRequest.getWarehouseName());
        return WarehouseResponse.builder()
                .warehouseId(warehouse.getWarehouseId())
                .warehouseName(warehouse.getWarehouseName())
                .warehouseAddress(warehouse.getWarehouseAddress())
                .build();
    }

    public List<WarehouseResponse> getAllWarehouse() {
        List<Warehouse> listWarehouse = warehouseRepository.findAll();
        log.info("get all warehouse");
        return listWarehouse.stream().map(warehouse -> WarehouseResponse.builder()
                .warehouseId(warehouse.getWarehouseId())
                .warehouseName(warehouse.getWarehouseName())
                .warehouseAddress(warehouse.getWarehouseAddress())
                .build()
        ).toList();
    }

    public WarehouseResponse updateWarehouse(Integer warehouseId, WarehouseRequest warehouseRequest) {
        Warehouse warehouse = warehouseRepository.findByWarehouseId(warehouseId).orElseThrow(
                () -> new AppException(ErrorCode.WAREHOUSE_NOT_EXISTED)
        );
        warehouse.setWarehouseAddress(warehouseRequest.getWarehouseAddress());
        warehouse.setWarehouseName(warehouseRequest.getWarehouseName());
        warehouseRepository.save(warehouse);
        log.info("update warehouse:{} successful", warehouseRequest.getWarehouseName());
        return WarehouseResponse.builder()
                .warehouseId(warehouse.getWarehouseId())
                .warehouseName(warehouse.getWarehouseName())
                .warehouseAddress(warehouse.getWarehouseAddress())
                .build();
    }

    @Transactional
    public void deleteWarehouse(Integer warehouseId) {
        warehouseRepository.deleteByWarehouseId(warehouseId);
        log.info("delete warehouse id:{}", warehouseId);
    }
}
