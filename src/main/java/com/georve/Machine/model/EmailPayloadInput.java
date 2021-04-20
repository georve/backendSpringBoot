package com.georve.Machine.model;

import javax.validation.constraints.NotBlank;

public class EmailPayloadInput {

    @NotBlank
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
