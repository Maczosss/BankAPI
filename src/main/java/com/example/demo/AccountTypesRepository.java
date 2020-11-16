package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypesRepository extends MongoRepository<AccountTypes, String> {

    AccountTypes getAccountTypesByAccountType(String type);
}
