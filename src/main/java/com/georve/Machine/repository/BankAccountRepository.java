package com.georve.Machine.repository;

import com.georve.Machine.model.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface BankAccountRepository extends MongoRepository<BankAccount, String> {

    @Query("{ 'email' : ?0 }")
    BankAccount findByEmail(String email);

    @Query("{ 'account' : ?0 }")
    BankAccount findByAccount(String account);

}
