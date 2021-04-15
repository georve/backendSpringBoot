package com.georve.Machine.model;

import javax.validation.constraints.NotBlank;

public class DepositWidrawalObjet {
    @NotBlank
    private String email;
    @NotBlank
    private Double value;
    private String accountNumber;

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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public DepositWidrawalObjet(){

    }

}
