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

public interface WarehouseService {

    WarehouseResponse createWarehouse(WarehouseRequest warehouseRequest);

    List<WarehouseResponse> getAllWarehouse();

    WarehouseResponse updateWarehouse(Integer warehouseId, WarehouseRequest warehouseRequest);

    void deleteWarehouse(Integer warehouseId) ;
}
