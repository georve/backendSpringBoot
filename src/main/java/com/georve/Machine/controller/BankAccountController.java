package com.georve.Machine.controller;

import com.georve.Machine.exception.NotEnoughMoneyException;
import com.georve.Machine.model.BankAccount;
import com.georve.Machine.model.DepositWidrawalObjet;
import com.georve.Machine.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@RestController
@RequestMapping("/api/bankAccount/")
public class BankAccountController {

    @Autowired
    BankAccountRepository repository;

    @PostMapping("/create")
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount account) {

        try {
            BankAccount _account = repository.save(new BankAccount(account.getAccount(), account.getEmail(), account.getValue()));
            return new ResponseEntity<>(_account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get/{account}")
    public ResponseEntity<BankAccount> getAccountById(@PathVariable("account") String id) {


        BankAccount accountData = repository.findByAccount(id);

        if (accountData !=null) {
            return new ResponseEntity<>(accountData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<BankAccount> depositBankAccount(@RequestBody DepositWidrawalObjet account) {

        try {
            BankAccount _account = repository.findByEmail(account.getEmail());
            if (_account !=null) {
                Double newValue=_account.getValue().doubleValue()+account.getValue().doubleValue();
                _account.setValue(newValue);
              BankAccount updated=  repository.save(_account);
              return new ResponseEntity<>(updated,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/withdrawal")
    public ResponseEntity<BankAccount> withdrawalBankAccount(@RequestBody DepositWidrawalObjet account) {

        try {
            BankAccount _account = repository.findByEmail(account.getEmail());
            if (_account !=null) {
                if(_account.getValue().doubleValue()>=account.getValue().doubleValue()){
                    Double newValue=_account.getValue().doubleValue()-account.getValue().doubleValue();
                    _account.setValue(newValue);
                    BankAccount updated=  repository.save(_account);
                    return new ResponseEntity<>(updated,HttpStatus.OK);
                }else{
                    throw new NotEnoughMoneyException("There is not enough money to complete the transaction");
                }

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

