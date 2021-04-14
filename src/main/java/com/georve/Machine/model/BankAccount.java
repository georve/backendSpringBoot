package com.georve.Machine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BankAccount")
public class BankAccount {
    @Id
    private String id;
    @Indexed(unique=true)
    private String account;
    @Indexed(unique=true)
    private String email;
    private Double value;

    public BankAccount(){

    }

    public BankAccount(String account, String email,Double value){
        this.account=account;
        this.email=email;
        this.value=value;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
