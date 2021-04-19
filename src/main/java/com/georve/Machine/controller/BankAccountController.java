package com.georve.Machine.controller;

import com.georve.Machine.exception.NotEnoughMoneyException;
import com.georve.Machine.exception.ResourceNotFoundException;
import com.georve.Machine.model.BankAccount;
import com.georve.Machine.model.DepositWidrawalObjet;
import com.georve.Machine.repository.BankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/bankAccount/")
public class BankAccountController {

    @Autowired
    BankAccountRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount account) {

        try {
            BankAccount _account = repository.save(new BankAccount(account.getAccount(), account.getEmail(), account.getValue()));
            return new ResponseEntity<>(_account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get/{account}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<BankAccount> getAccountById(@PathVariable("account") String id) {


        BankAccount accountData = repository.findByAccount(id)
                .orElseThrow(()->new ResourceNotFoundException("Account no found"));

        return new ResponseEntity<>(accountData, HttpStatus.OK);

    }

    @GetMapping("/getByEmail/{email}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<BankAccount> getAccountByEmail(@PathVariable("email") String email) {


        BankAccount accountData = repository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Account no found for email:"+email));

        return new ResponseEntity<>(accountData, HttpStatus.OK);


    }

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<BankAccount> depositBankAccount(@Valid @RequestBody DepositWidrawalObjet account) {

        logger.info("deposit "+account.getAccountNumber()+" "+account.getEmail());

        BankAccount _account = repository.findByEmail(account.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("Account no found for email:"+account.getEmail()));

        Double newValue=_account.getValue().doubleValue()+account.getValue().doubleValue();
        _account.setValue(newValue);
        BankAccount updated=  repository.save(_account);
        return new ResponseEntity<>(updated,HttpStatus.OK);

    }

    @PostMapping("withdrawal")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<BankAccount> withdrawalBankAccount(@Valid @RequestBody DepositWidrawalObjet account) {

        logger.info("withdrawal "+account.getAccountNumber()+" "+account.getEmail());

        BankAccount _account = repository.findByEmail(account.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("Account no found for email:"+account.getEmail()));
        logger.info(_account.getAccount());

        if(_account.getValue()>=account.getValue()){
            Double newValue=_account.getValue().doubleValue()-account.getValue().doubleValue();
            _account.setValue(newValue);
            BankAccount updated=  repository.save(_account);
            return new ResponseEntity<>(updated,HttpStatus.OK);
        }else{
            throw new NotEnoughMoneyException("There is not enough money to complete the transaction");
        }

    }
}

