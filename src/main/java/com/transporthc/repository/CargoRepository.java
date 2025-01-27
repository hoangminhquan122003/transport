package com.transporthc.repository;

import com.transporthc.entity.Cargo;
import com.transporthc.entity.Warehouse;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends BaseRepository<Cargo, Integer> {
    boolean existsByCargoName(String cargoName);

    Optional<Cargo> findByCargoId(Integer cargoId);

    void deleteByCargoId(Integer cargoId);

    Optional<Cargo> findByCargoName(String cargoName);
}
