package com.georve.Machine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughMoneyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotEnoughMoneyException(String message){
        super(message);
    }
}
