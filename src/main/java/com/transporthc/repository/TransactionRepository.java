package com.transporthc.repository;

import com.transporthc.entity.Cargo;
import com.transporthc.entity.Transaction;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    void deleteByTransactionId(Integer transactionId);
    @Query( "SELECT t from Transaction t "+
            "WHERE t.transactionDate >= :startDate "+
            "AND t.transactionDate <= :endDate ")
    public List<Transaction> searchTransactionDate(@Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);

    Optional<Transaction> findByTransactionId(Integer transactionId);
}
