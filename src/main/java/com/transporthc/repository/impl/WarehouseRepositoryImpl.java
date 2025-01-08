package com.transporthc.repository.impl;

import com.transporthc.entity.QWarehouse;
import com.transporthc.entity.Warehouse;
import com.transporthc.repository.WarehouseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;

import java.util.Optional;
@Primary
public class WarehouseRepositoryImpl extends BaseRepositoryImpl<Warehouse,Integer> implements WarehouseRepository {
    final QWarehouse warehouse=QWarehouse.warehouse;
    public WarehouseRepositoryImpl( EntityManager em) {
        super(Warehouse.class, em);
    }

    @Override
    public boolean existsByWarehouseName(String warehouseName) {
        return query.selectOne()
                .from(warehouse)
                .where(warehouse.warehouseName.eq(warehouseName))
                .fetchOne() !=null;
    }

    @Override
    public Optional<Warehouse> findByWarehouseId(Integer warehouseId) {
        return Optional.ofNullable(query.selectFrom(warehouse)
                .where(warehouse.warehouseId.eq(warehouseId))
                .fetchOne());
    }

    @Override
    public void deleteByWarehouseId(Integer warehouseId) {
        query.delete(warehouse)
                .where(warehouse.warehouseId.eq(warehouseId))
                .execute();
    }

    @Override
    public Optional<Warehouse> findByWarehouseName(String warehouseName) {
        return Optional.ofNullable(query.selectFrom(warehouse)
                .where(warehouse.warehouseName.eq(warehouseName))
                .fetchOne());
    }
}
