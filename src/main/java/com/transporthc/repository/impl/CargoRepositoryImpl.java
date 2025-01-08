package com.transporthc.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.transporthc.entity.Cargo;
import com.transporthc.entity.QCargo;
import com.transporthc.repository.BaseRepository;
import com.transporthc.repository.CargoRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;

import java.util.Optional;
@Primary
public class CargoRepositoryImpl extends BaseRepositoryImpl<Cargo,Integer> implements CargoRepository {
    final QCargo cargo= QCargo.cargo;

    public CargoRepositoryImpl( EntityManager em) {
        super(Cargo.class, em);
    }

    @Override
    public boolean existsByCargoName(String cargoName) {
        return query.selectOne()
                .from(cargo)
                .where(cargo.cargoName.eq(cargoName))
                .fetchFirst() !=null;
    }

    @Override
    public Optional<Cargo> findByCargoId(Integer cargoId) {

        return Optional.ofNullable(query.selectFrom(cargo)
                .where(cargo.cargoId.eq(cargoId))
                .fetchOne());
    }

    @Override
    public void deleteByCargoId(Integer cargoId) {
        query.delete(cargo)
                .where(cargo.cargoId.eq(cargoId))
                .execute();
    }

    @Override
    public Optional<Cargo> findByCargoName(String cargoName) {
        return Optional.ofNullable(query.select(cargo)
                .from(cargo)
                .where(cargo.cargoName.eq(cargoName))
                .fetchOne());
    }
}

