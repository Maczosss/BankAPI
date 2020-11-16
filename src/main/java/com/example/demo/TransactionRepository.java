package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends MongoRepository<Transactions, String> {

    Optional<Transactions> findById(String id);

    List<Transactions> findAllByAccountType(String id);
    List<Transactions> findAllByCustomerId(String accountType);
}
