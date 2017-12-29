package com.abc.tpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.tpp.Transaction;
@Repository("transactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
