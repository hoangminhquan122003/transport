package com.transporthc.controller;

import com.transporthc.dto.request.CustomerRequest;
import com.transporthc.dto.request.UpdateCustomerRequest;
import com.transporthc.dto.request.WarehouseRequest;
import com.transporthc.dto.response.ApiResponse;
import com.transporthc.dto.response.CustomerResponse;
import com.transporthc.dto.response.WarehouseResponse;
import com.transporthc.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/warehouse")
public class WarehouseController {
    WarehouseService warehouseService;

    @PostMapping
    public ApiResponse<WarehouseResponse> createWarehouse (@RequestBody @Valid WarehouseRequest warehouseRequest){

        return ApiResponse.<WarehouseResponse>builder()
                .message("create warehouse")
                .result(warehouseService.createWarehouse(warehouseRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<WarehouseResponse>> getAllWarehouse(){
        return ApiResponse.<List<WarehouseResponse>>builder()
                .message("get all warehouse")
                .result(warehouseService.getAllWarehouse())
                .build();
    }

    @PutMapping("/{warehouseId}")
    public ApiResponse<WarehouseResponse> updateWarehouseById(@PathVariable Integer warehouseId, @RequestBody @Valid
    WarehouseRequest warehouseRequest){
        return ApiResponse.<WarehouseResponse>builder()
                .message("update warehouse successful")
                .result(warehouseService.updateWarehouse(warehouseId,warehouseRequest))
                .build();
    }
    @DeleteMapping("/{warehouseId}")
    public ApiResponse<Void> deleteWarehouseById( @PathVariable Integer warehouseId){
        warehouseService.deleteWarehouse(warehouseId);
        return ApiResponse.<Void>builder()
                .message("delete warehouse successful")
                .build();
    }
}
