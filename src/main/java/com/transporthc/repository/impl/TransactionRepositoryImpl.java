package com.transporthc.repository.impl;

import com.transporthc.entity.QTransaction;
import com.transporthc.entity.Transaction;
import com.transporthc.repository.TransactionRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Primary
public class TransactionRepositoryImpl extends BaseRepositoryImpl<Transaction,Integer> implements TransactionRepository {
    final QTransaction transaction=QTransaction.transaction;
    public TransactionRepositoryImpl( EntityManager em) {
        super(Transaction.class, em);
    }

    @Override
    public void deleteByTransactionId(Integer transactionId) {
        query.delete(transaction)
                .where(transaction.transactionId.eq(transactionId))
                .execute();
    }

    @Override
    public List<Transaction> searchTransactionDate(LocalDate startDate, LocalDate endDate) {
        return query.selectFrom(transaction)
                .where(transaction.transactionDate.goe(startDate).and(transaction.transactionDate.loe(endDate)))
                .fetch();
    }

    @Override
    public Optional<Transaction> findByTransactionId(Integer transactionId) {
        return Optional.ofNullable(query.selectFrom(transaction)
                .where(transaction.transactionId.eq(transactionId))
                .fetchOne());
    }
}
