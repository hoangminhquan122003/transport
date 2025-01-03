package com.transporthc.controller;

import com.transporthc.dto.request.CargoRequest;
import com.transporthc.dto.response.ApiResponse;
import com.transporthc.dto.response.CargoResponse;
import com.transporthc.dto.response.CargoResponse;
import com.transporthc.service.CargoService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/cargo")
public class CargoController {
    CargoService cargoService;

    @PostMapping
    public ApiResponse<CargoResponse> createCargo (@RequestBody @Valid CargoRequest cargoRequest){

        return ApiResponse.<CargoResponse>builder()
                .message("create Cargo")
                .result(cargoService.createCargo(cargoRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CargoResponse>> getAllCargo(){
        return ApiResponse.<List<CargoResponse>>builder()
                .message("get all Cargo")
                .result(cargoService.getAllCargo())
                .build();
    }

    @PutMapping("/{cargoId}")
    public ApiResponse<CargoResponse> updateCargoById(@PathVariable Integer cargoId, @RequestBody @Valid
    CargoRequest cargoRequest){
        return ApiResponse.<CargoResponse>builder()
                .message("update Cargo successful")
                .result(cargoService.updateCargo(cargoId,cargoRequest))
                .build();
    }

    @DeleteMapping("/{cargoId}")
    public ApiResponse<Void> deleteCargoById( @PathVariable Integer cargoId){
        cargoService.deleteCargo(cargoId);
        return ApiResponse.<Void>builder()
                .message("delete Cargo successful")
                .build();
    }

    @GetMapping("/inventory")
    public ApiResponse<String> inventory(){
        return  ApiResponse.<String>builder()
                .message("inventory")
                .result(cargoService.inventory())
                .build();
    }
}
