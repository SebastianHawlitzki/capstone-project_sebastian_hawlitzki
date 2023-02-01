package de.neuefische.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotEnoughBalanceException extends RuntimeException{

    public NotEnoughBalanceException(double accountBalance){
        super("Not enough balance" + accountBalance);
    }
}
