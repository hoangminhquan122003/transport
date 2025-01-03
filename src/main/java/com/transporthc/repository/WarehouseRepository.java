package com.transporthc.repository;

import com.transporthc.entity.Cargo;
import com.transporthc.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {
    boolean existsByWarehouseName(String warehouseName);

    Optional<Warehouse> findByWarehouseId(Integer warehouseId);

    void deleteByWarehouseId(Integer warehouseId);

    Optional<Warehouse> findByWarehouseName(String warehouseName);

}
