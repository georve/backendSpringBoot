package com.georve.Machine.repository;

import com.georve.Machine.model.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface BankAccountRepository extends MongoRepository<BankAccount, String> {

    @Query("{ 'email' : ?0 }")
    Optional<BankAccount> findByEmail(String email);

    @Query("{ 'account' : ?0 }")
    Optional<BankAccount> findByAccount(String account);

}
